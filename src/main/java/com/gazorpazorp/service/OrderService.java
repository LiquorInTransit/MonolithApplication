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

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.OrderStatus;
import com.gazorpazorp.model.Product;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.DriverDto;
import com.gazorpazorp.model.dto.OrderCurrentDto;
import com.gazorpazorp.model.dtoMapper.AccountMapper;
import com.gazorpazorp.model.dtoMapper.OrderMapper;
import com.gazorpazorp.repository.LineItemRepository;
import com.gazorpazorp.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;
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
	PaymentService paymentClient;
	@Autowired
	QuoteService quoteService;
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	AccountMapper accountMapper;
	
	private final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	private static final OrderStatus[] TERMINATING_ORDER_STATUSES = {OrderStatus.COMPLETED, OrderStatus.CANCELLED};
	
	public List<Order> getAllOrdersForCustomer() {
		Long customerId = customerService.getCurrentCustomer().getId();								//add the CANCELLED status to the valid types
		return orderRepo.findByCustomerIdAndStatusInOrderByCreatedAtDesc(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES));
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
		aggregateOrderItems(order);
		
		return order;
	}
	
	public OrderCurrentDto getOrderCurrentDtoById(Long orderId, boolean verify) throws Exception {
		Order order = getOrderById(orderId, verify);
		Delivery delivery = deliveryService.getDeliveryByOrderId(order.getId(), false);
		if (delivery == null)
			return null;
		return aggregateOrderCurrent(order, delivery);
	}
	
	public OrderCurrentDto getCurrentOrder() {
		Long customerId = customerService.getCurrentCustomer().getId();
		Order order = orderRepo.findByCustomerIdAndStatusNotIn(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES));
		if (order==null)
			return null;
		aggregateOrderItems(order);
		Delivery delivery = deliveryService.getDeliveryByOrderId(order.getId(), false);
		if (delivery == null)
			return null;
		return aggregateOrderCurrent(order, delivery);
		//return order;
	}
	
	private OrderCurrentDto aggregateOrderCurrent(Order order, Delivery delivery) {
		DriverDto driverDto = null;
		if (delivery.getDriverId() != null) {
			Driver driver = driverService.getDriverById(delivery.getDriverId());
			User user = userService.getUserById(driver.getUserId());
			driverDto = accountMapper.driverAndUserToDriverDto(driver, user);
		}
		OrderCurrentDto dto = OrderMapper.INSTANCE.orderAndDriverDtoToOrderCurrentDto(order, driverDto);	
		return dto;
	}
	
	@Transactional(rollbackOn= {Exception.class} )
	public boolean cancelCurrentOrder() throws Exception {
		Long accountId = customerService.getCurrentCustomer().getId();
		Order order = orderRepo.findByCustomerIdAndStatusNotIn(accountId, Arrays.asList(TERMINATING_ORDER_STATUSES));
		if (order == null)
			return false;
		order.setStatus(OrderStatus.CANCELLED);
		orderRepo.save(order);
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
		if (orderRepo.findByCustomerIdAndStatusNotIn(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES)) != null) {
			throw new Exception ("Customer already has an active order");
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
		order.setTotal(items.stream().mapToDouble(li -> li.getProduct().getPrice()*(li.getQty()*1.0)).sum());
		order.setStatus(OrderStatus.ACTIVE);
//		order.setCreatedAt(new Date());
		order = orderRepo.saveAndFlush(order);
		
	//Create delivery from quote
	//If something failed, then delete the order we just created, and return /*null;*/ an empty responsebody with a 500 status
		try {
			deliveryService.createDelivery(quoteId, order.getId());	
		} catch (Exception e) {
			e.printStackTrace();
			//The delivery creation failed
			orderRepo.deleteById(order.getId());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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
		Customer customer = customerService.getCurrentCustomer();
		
		if (customer != null && customer.getId() != customerId) {
			throw new Exception ("Account number not valid");
		}
		return true;
	}
	protected void aggregateOrderItems(Order order) {
		Set<Product> products = new HashSet<>(productService.getProductsById(order.getItems().stream().map(item -> item.getProductId().toString()).collect(Collectors.joining(","))));
		order.getItems().forEach(item -> item.setProduct(products.stream().filter(prd -> item.getProductId().equals(prd.getId())).findFirst().orElse(null)));
		order.getItems().forEach(item -> {if (item!=null && item.getProduct() != null)item.getProduct().Incorporate();});
	}
}
