package com.gazorpazorp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.OrderEvent;
import com.gazorpazorp.model.OrderEventType;
import com.gazorpazorp.repository.OrderRepository;

@Service
public class OrderEventDispatcherService {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	OrderService orderService;
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	OrderRepository orderRepo;

	
	//Do something based on the event type here. This is simulating ACTUAL event sourcing with producers and consumers
			//What happens here will depend on the status of the order (retrieve the order via the getOrderById method)
			//EG. If someone somehow added another purchased event, we DO NOT want to charge their card again...
			//Block out specific event types once certain stages have been reached?
			//Maybe limit customer interactions to ONLY the updated type (if we even keep it).
//	@Async
	public void dispatch (OrderEvent event, Long orderId) {
		Order order = orderRepo.getOne(orderId);
		switch (event.getOrderEventType()) {
			case CREATED:
				
				break;
			case PURCHASED:
				//Get orderId, stripe customerId, amount, and NoCapChargeCustomer(payment-service)
				Customer customer = customerService.getCustomerById(order.getCustomerId());
	//			paymentService.chargeCustomerNoCap(customer.getStripeId(), order.getId(), order.getTotal());
				doCharge(customer.getStripeId(), order.getId(), order.getTotal());
				break;
			case PAYMENT_VERIFIED:
				//Prepare the delivery
				System.out.println(order.getId());
				deliveryService.createDelivery(order.getQuoteId(), order.getId());
				break;
			case PAYMENT_DECLINED:
				orderService.addOrderEvent(new OrderEvent(OrderEventType.CANCELLED, order.getId()), false);
				break;
			case UPDATED:
				//DO WE EVEN WANT TO ALLOW THIS?
				//If yes, it will ONLY be for REMOVING items. No adding. Only to be used if item not in stock.
				break;
			case COMPLETED:
				//Capture the customer charge
				break;
			case CANCELLED:
				//Send cancel event to delivery
				//TODO: Release the capture (do a refund)
				break;
			default:
				break;
		}
	}
	@Transactional
	private void doCharge (String str, Long id, int total) {
		paymentService.chargeCustomerNoCap(str, id, total);
	}
}
