package com.gazorpazorp.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gazorpazorp.LITMonolith.config.LITSecurityUtil;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.OrderEvent;
import com.gazorpazorp.model.OrderEventType;
import com.gazorpazorp.model.OrderStatus;
import com.gazorpazorp.model.Product;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.DriverDto;
import com.gazorpazorp.model.dto.OrderCurrentDto;
import com.gazorpazorp.model.dtoMapper.AccountMapper;
import com.gazorpazorp.model.dtoMapper.OrderMapper;
import com.gazorpazorp.repository.LineItemRepository;
import com.gazorpazorp.repository.OrderEventRepository;
import com.gazorpazorp.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderEventRepository orderEventRepo;
	@Autowired
	LineItemRepository lineItemRepo;
	
	@Autowired
	ProductService productService;
	@Autowired
	CustomerService customerService;
	@Autowired
	UserService userService;
	@Autowired
	DriverService driverService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	QuoteService quoteService;
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	AccountMapper accountMapper;
	
	private final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	private static final OrderStatus[] TERMINATING_ORDER_STATUSES = {OrderStatus.COMPLETED, OrderStatus.CANCELLED};
	
	public List<Order> getAllOrdersForCustomer() {
		Long customerId = LITSecurityUtil.currentUser().getCustomerId();//customerService.getCurrentCustomer().getId();								//add the CANCELLED status to the valid types
//		return orderRepo.findByCustomerIdAndStatusInOrderByCreatedAtDesc(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES));
		List<Order> orders = orderRepo.findByCustomerIdOrderByCreatedAtDesc(customerId);
		
		return orders.stream().map(order->{
			try {
				return getOrderById(order.getId(), false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return order;
		}).collect(Collectors.toList());
	}

	public Order getOrderById(Long orderId, boolean verify) throws Exception{
		//Get the order
		Order order = orderRepo.findById(orderId).orElse(null);
		if (order==null)
			throw new Exception("Order with ID " + orderId + " does not exist");
		//validate that the accountId of the order belongs to the user
		if (verify) {
			try {
				validateCustomerId(order.getCustomerId());
			} catch (Exception e) {
				//TODO: Make this throw an exception so that feign can say that you're not authorized to look at these orders
				logger.error("FAILED VALIDATION");
				return null;
			}
		}
		List<OrderEvent> orderEvents = orderEventRepo.findByOrderIdOrderByCreatedAtAsc(orderId);
		orderEvents.forEach(event->order.incorporate(event));
		
		
		
		
		aggregateOrderItems(order);
		
		return order;
	}
	
	public OrderCurrentDto getOrderCurrentDtoById(Long orderId, boolean verify) throws Exception {
		Order order = getOrderById(orderId, verify);
		Delivery delivery = deliveryService.getDeliveryByOrderId(order.getId(), false);
//		if (delivery == null)
//			return null;
		return aggregateOrderCurrent(order, delivery);
	}
	
	public OrderCurrentDto getCurrentOrder() {
//		Order order = orderRepo.findByCustomerIdAndStatusNotIn(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES));
		Order order = getAllOrdersForCustomer().stream().filter(o -> !Arrays.asList(TERMINATING_ORDER_STATUSES).contains(o.getStatus())).findFirst().orElse(null);//orderRepo.findByCustomerId(customerId);
		if (order==null)
			return null;
		//aggregateOrderItems(order);
		Delivery delivery = deliveryService.getDeliveryByOrderId(order.getId(), false);
//		if (delivery == null)
//			return null;
		return aggregateOrderCurrent(order, delivery);
		//return order;
	}
	
	private OrderCurrentDto aggregateOrderCurrent(Order order, Delivery delivery) {
		DriverDto driverDto = null;
		if (delivery!=null && delivery.getDriverId() != null) {
			Driver driver = driverService.getDriverById(delivery.getDriverId());
			User user = userService.getUserById(driver.getUserId());
			driverDto = accountMapper.driverAndUserToDriverDto(driver, user);
		}
		OrderCurrentDto dto = OrderMapper.INSTANCE.orderAndDriverDtoToOrderCurrentDto(order, driverDto);	
		return dto;
	}
	
	@Transactional(rollbackOn= {Exception.class} )
	public boolean cancelCurrentOrder() throws Exception {
//		Order order = orderRepo.findByCustomerIdAndStatusNotIn(accountId, Arrays.asList(TERMINATING_ORDER_STATUSES));
		Order order = getAllOrdersForCustomer().stream().filter(o -> !Arrays.asList(TERMINATING_ORDER_STATUSES).contains(o.getStatus())).findFirst().orElse(null);//orderRepo.findByCustomerId(customerId);//orderRepo.findByCustomerId(accountId);
		if (order == null)
			return false;
		//order.setStatus(OrderStatus.CANCELLED);
	//	orderRepo.save(order);
		addOrderEvent(new OrderEvent(OrderEventType.CANCELLED, order.getId(), "Cancelled by customer"), false);
		if (deliveryService.cancelDeliveryByOrderId(order.getId())==null) {
			logger.error("The delivery failed to delete");
			throw new Exception("Failed to delete order. Try again later");
		}
		return true;
	}
	
	public boolean cancelOrderByOrderId (Long orderId) throws Exception {
		Order order = orderRepo.findById(orderId).orElse(null);
		if (order == null)
			throw new Exception("Order with ID " + orderId + " does not exist");
		order.setStatus(OrderStatus.CANCELLED);
		return true;
	}
	
	
	public ResponseEntity<Order> createOrder (List<LineItem> items, Long quoteId, Long customerId) throws Exception {
	//	Long customerId = accountClient.getCustomer().getId();
//		if (orderRepo.findByCustomerIdAndStatusNotIn(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES)) != null) {
//			throw new Exception ("Customer already has an active order"); //RETURN A REAL RESPONSE HERE, NOT AN EXCEPTION. Though, the current method DOES work...
//		}
		if (orderRepo.findByCustomerId(customerId) != null) {
			throw new Exception ("Customer already has an active order"); //RETURN A REAL RESPONSE HERE, NOT AN EXCEPTION. Though, the current method DOES work...
		}
		//Light, dirty check to be sure customer own the quote
		Quote quote = quoteService.getQuoteById(quoteId);
		if (quote.getCustomerId() != customerId)
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		
		Order order = new Order();
		for (int x = 0; x<items.size(); x++)
			items.get(x).setOrder(order);
		items.forEach(item -> item.setProductId(item.getProduct().getId()));
		order.setCustomerId(customerId);
		
		//Remove Delivery Info
		order.setItems(new HashSet<LineItem>(items));
		order.setQuoteId(quoteId);
		//Add the delivery fee to the total.
		order.setTotal(items.stream().mapToInt(li -> (int)li.getProduct().getPrice()*(li.getQty()*100)).sum());
	//	order.setStatus(OrderStatus.ACTIVE);
//		order.setCreatedAt(new Date());
		order = orderRepo.saveAndFlush(order);
		
		//Create a new order event for the order.
		addOrderEvent(new OrderEvent(OrderEventType.CREATED, order.getId()), false);
		
		//THIS NOW GETS DONE WHEN WE RECEIVE THE CREATE_ORDER COMMAND/EVENT
	//Create delivery from quote
	//If something failed, then delete the order we just created, and return /*null;*/ an empty responsebody with a 500 status
//		try {
//			deliveryService.createDelivery(quoteId, order.getId());	
//		} catch (Exception e) {
//			e.printStackTrace();
//			//The delivery creation failed
//			orderRepo.deleteById(order.getId());
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
		
		return new ResponseEntity<>(order, HttpStatus.OK);
		//return responseBody with the order
		//return new ResponseBody<Order>(order, HttpStatus.OK);
	}
	
	public Boolean completeOrder(Long orderId) throws Exception {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new Exception("Order of ID" + orderId + " does not exist"));
		order.setStatus(OrderStatus.COMPLETED);
		orderRepo.save(order);
		return true;
	}
	


	private boolean validateCustomerId(Long customerId) throws Exception {
		Long id = LITSecurityUtil.currentUser().getCustomerId();//customerService.getCurrentCustomer();
		
		if (id==null||(id != null && id != customerId)) {
			throw new Exception ("Account number not valid");
		}
		return true;
	}
	protected void aggregateOrderItems(Order order) {
		Set<Product> products = new HashSet<>(productService.getProductsById(order.getItems().stream().map(item -> item.getProductId().toString()).collect(Collectors.joining(","))));
		order.getItems().forEach(item -> item.setProduct(products.stream().filter(prd -> item.getProductId().equals(prd.getId())).findFirst().orElse(null)));
		order.getItems().forEach(item -> {if (item!=null && item.getProduct() != null)item.getProduct().Incorporate();});
	}
	
	

	//Theoretically, we should always be good with validating the customer.
	//TODO: Add MUCH better error handling here.
	//@Async
	@Autowired
	OrderEventDispatcherService dispatcherService;
	@Transactional
	public void addOrderEvent(OrderEvent orderEvent, boolean validate) {
		Order order = orderRepo.getOne(orderEvent.getOrderId());
		if (validate) {
			try {
				validateCustomerId(order.getCustomerId());
			} catch (Exception e) {
				return;
			}
		}
		orderEvent = orderEventRepo.save(orderEvent);
		dispatcherService.dispatch(orderEvent, order.getId());
//		//Do something based on the event type here. This is simulating ACTUAL event sourcing with producers and consumers
//		//What happens here will depend on the status of the order (retrieve the order via the getOrderById method)
//		//EG. If someone somehow added another purchased event, we DO NOT want to charge their card again...
//		//Block out specific event types once certain stages have been reached?
//		//Maybe limit customer interactions to ONLY the updated type (if we even keep it).
//		switch (orderEvent.getOrderEventType()) {
//			case CREATED:
//				
//				break;
//			case PURCHASED:
//				//Get orderId, stripe customerId, amount, and NoCapChargeCustomer(payment-service)
//				Customer customer = customerService.getCustomerById(order.getCustomerId());
////				paymentService.chargeCustomerNoCap(customer.getStripeId(), order.getId(), order.getTotal());
//				doCharge(customer.getStripeId(), order.getId(), order.getTotal());
//				break;
//			case PAYMENT_VERIFIED:
//				//Prepare the delivery
//				deliveryService.createDelivery(order.getQuoteId(), order.getId());
//				break;
//			case PAYMENT_DECLINED:
//				addOrderEvent(new OrderEvent(OrderEventType.CANCELLED, order.getId()), false);
//				break;
//			case UPDATED:
//				//DO WE EVEN WANT TO ALLOW THIS?
//				//If yes, it will ONLY be for REMOVING items. No adding. Only to be used if item not in stock.
//				break;
//			case COMPLETED:
//				//Capture the customer charge
//				break;
//			case CANCELLED:
//				//Send cancel event to delivery
//				//TODO: Release the capture (do a refund)
//				break;
//			default:
//				break;
//		}
	}
//	private void doCharge (String str, Long id, int total) {
//		paymentService.chargeCustomerNoCap(str, id, total);
//	}
}
