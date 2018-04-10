package com.gazorpazorp.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gazorpazorp.model.OrderEvent;
import com.gazorpazorp.model.OrderEventType;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeOutcome;
import com.stripe.model.EphemeralKey;
import com.stripe.net.RequestOptions;


@Service
public class PaymentService {
	@Value("${stripe.secret-key}")
	String secretKey;
	@Value("${stripe.public-key}")
	String publicKey;
	
	@Autowired
	CustomerService customerService;
	@Autowired
	@Lazy(value=true)
	OrderService orderService;
	
	Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@SuppressWarnings("unused")
	public String getKey(String version) {
		RequestOptions reqopt = (new RequestOptions.RequestOptionsBuilder())
								.setApiKey(secretKey)
								.setStripeVersion(version)
								.build();
		logger.warn("Stripe API Version: " + version);
		Map<String, Object> options = new HashMap<>();
		options.put("customer", customerService.getCurrentCustomer().getStripeId());
		
		try {
			EphemeralKey key = EphemeralKey.create(options, reqopt);
			return key.getRawJson();
		} catch (StripeException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * This is the old method of processing payments. Requires the driver.
	 * New version works in three steps, at different times. #EventSourcing
	 * @param customerId
	 * @param driverId
	 * @param orderId
	 * @param amount
	 * @return
	 */
	@Deprecated
	public HttpStatus processPayment (String customerId, String driverId, Long orderId, Integer amount) {
		RequestOptions reqopt = (new RequestOptions.RequestOptionsBuilder())
				.setApiKey(secretKey)
				.build();
		
		Stripe.apiKey=secretKey;

		com.stripe.model.Customer cust;
		try {
			cust = com.stripe.model.Customer.retrieve(customerId, reqopt);
			Map<String, Object> params = new HashMap<>();
			params.put("amount", amount);
			params.put("currency", "cad");
			params.put("description", "Example charge");
			params.put("customer", cust.getId());
			String source = cust.getDefaultSource();
			if (source == null)
				return HttpStatus.BAD_REQUEST;
			params.put("source", source);
			params.put("destination", driverId);
			params.put("application_fee", 400+(int)(amount*.029));
			params.put("capture", "false");
			Map<String, String> initialMetadata = new HashMap<String, String>();
			initialMetadata.put("order_id", "6735");
			params.put("metadata", initialMetadata);
			Charge charge = Charge.create(params, reqopt);
			if (determineHttpStatus(charge.getOutcome())==HttpStatus.OK) {
				Charge capped = Charge.retrieve(charge.getId()).capture();
				return determineHttpStatus(capped.getOutcome());
			} else {
				return determineHttpStatus(charge.getOutcome());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	/**
	 * TODO: Figure out some good error handling. Add error to the event maybe?
	 * @param customerStripeId
	 * @param orderId
	 * @param amount (in cents)
	 */
	@Async
	public void chargeCustomerNoCap(String customerStripeId, Long orderId, Integer amount) {
		RequestOptions reqopt = (new RequestOptions.RequestOptionsBuilder())
				.setApiKey(secretKey)
				.build();
		
		Stripe.apiKey=secretKey;

		com.stripe.model.Customer cust;
		try {
			cust = com.stripe.model.Customer.retrieve(customerStripeId, reqopt);
			String source = cust.getDefaultSource();
			if (source == null)
				return; //HttpStatus.BAD_REQUEST;
			Map<String, Object> params = new HashMap<>();
			params.put("amount", amount);
			params.put("currency", "cad");
			params.put("description", "Liquor In Transit order " + orderId+ ", placed at" + Instant.now());
			params.put("customer", cust.getId());
			params.put("statement_descriptor", "LIT Order "+orderId);
			params.put("source", source);
//			params.put("destination", driverId);
//			params.put("application_fee", 400+(int)(amount*.029));
			params.put("capture", "false");
			Map<String, String> initialMetadata = new HashMap<String, String>();
			initialMetadata.put("order_id", "6735");
			params.put("metadata", initialMetadata);
			Charge charge = Charge.create(params, reqopt);
			//No longer capturing the charge here.
//			if (determineHttpStatus(charge.getOutcome())==HttpStatus.OK) {
//				Charge capped = Charge.retrieve(charge.getId()).capture();
//				return; //determineHttpStatus(capped.getOutcome());
//			} else {
//				return; //determineHttpStatus(charge.getOutcome());
//			}
		} catch (Exception e) { //THIS IS ONLY IF SOMETHING SERIOUSLY BREAKS. NOT IF SAY, THERE WEREN'T ENOUGH FUNDS. TODO:
			e.printStackTrace();
			//The charge was not successfully created. add the declined event to the order
			orderService.addOrderEvent(new OrderEvent(OrderEventType.PAYMENT_DECLINED, orderId, e.getMessage()), false);
			return; //HttpStatus.INTERNAL_SERVER_ERROR;
		}
		//The charge was successfully created. Add the verified event to the order
		orderService.addOrderEvent(new OrderEvent(OrderEventType.PAYMENT_VERIFIED, orderId), false);
	}
	
	
	
//	public HttpStatus processPayment (String customerId, Long orderId, Integer amount) {
//		RequestOptions reqopt = (new RequestOptions.RequestOptionsBuilder())
//				.setApiKey(secretKey)
//				.build();
//		com.stripe.model.Customer cust;
//		try {
//			cust = com.stripe.model.Customer.retrieve(customerId, reqopt);
//			Map<String, Object> params = new HashMap<>();
//			params.put("amount", amount);
//			params.put("currency", "cad");
//			params.put("description", "Example charge");
//			params.put("customer", cust.getId());
//			params.put("source", cust.getDefaultSource());
//			Map<String, String> initialMetadata = new HashMap<String, String>();
//			initialMetadata.put("order_id", String.valueOf(orderId));
//			params.put("metadata", initialMetadata);
//			Charge charge = Charge.create(params, reqopt);
//			return determineHttpStatus(charge.getOutcome());
//		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
//				| APIException e) {
//			e.printStackTrace();
//			return null;
//		}	
//	}
	
	private HttpStatus determineHttpStatus(ChargeOutcome outcome) {
		System.out.println(outcome.getReason());
		if ("".equals(outcome.getReason()) || outcome.getReason()==null)
			return HttpStatus.OK;
		switch (outcome.getReason()) {
		case "expired_card":
			return HttpStatus.REQUEST_TIMEOUT;
		case "card_not_supported":
			return HttpStatus.FAILED_DEPENDENCY;
		case "invalid_cvc":
			return HttpStatus.FORBIDDEN;	
		default:
			return HttpStatus.GONE;
		}
	}
	
}
