package com.gazorpazorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Location;
import com.gazorpazorp.model.dto.DeliveryList;
import com.gazorpazorp.model.dto.DeliveryWithItemsDto;
import com.gazorpazorp.model.dto.QuoteDto;
import com.gazorpazorp.model.dtoMapper.QuoteMapper;
import com.gazorpazorp.service.DeliveryService;
import com.gazorpazorp.service.QuoteService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
	
	@Autowired
	QuoteService quoteService;
	@Autowired
	DeliveryService deliveryService;
	
	@PostMapping(value="/quote", produces="application/json")
	public ResponseEntity<QuoteDto> createQuote () throws Exception {
		return Optional.ofNullable(quoteService.createQuote())
				.map(o -> new ResponseEntity<QuoteDto>(QuoteMapper.INSTANCE.quoteToQuoteDto(o), HttpStatus.OK))
				.orElseThrow(() -> new Exception("Could not create quote!"));
	}	
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity getDeliverByOrderId(@PathVariable("orderId") Long orderId) throws Exception {
			return Optional.ofNullable(deliveryService.getDeliveryByOrderId(orderId, true))
					.map(d -> new ResponseEntity<Delivery>(d, HttpStatus.OK))
					.orElseThrow(() -> new Exception("Delivery with specified OrderId does not exist"));
		
	}
	
	@GetMapping("/history")
	@PreAuthorize("#oauth2.hasScope('driver') and hasRole('DRIVER')")
	public ResponseEntity<DeliveryList> getDriverDeliveryHistory () throws Exception {
		return Optional.ofNullable(deliveryService.getDriverHistory())
				.map(d -> new ResponseEntity<DeliveryList>(new DeliveryList(d), HttpStatus.OK))
				.orElseThrow(() -> new Exception("No past deliveries for driver."));
	}
	
	@GetMapping("/current")
	@PreAuthorize("#oauth2.hasScope('driver') and hasRole('DRIVER')")
	public ResponseEntity getDriverCurrentDelivery () throws Exception {
		return Optional.ofNullable(deliveryService.getDriverCurrentDelivery())
				.map(d -> new ResponseEntity<DeliveryWithItemsDto>(d, HttpStatus.OK))
				.orElseThrow(() -> new Exception("No current delivery for driver."));
	}
	
//	@DeleteMapping("/current")
//	@PreAuthorize("#oauth2.hasScope('driver') and hasRole('DRIVER')")
//	public ResponseEntity cancelCurrentDelivery () throws Exception {
//		return Optional.ofNullable(deliveryService.cancelCurrentDelivery())
//				.map(d -> new ResponseEntity(HttpStatus.OK))
//				.orElseThrow(() -> new Exception("Failed to cancel delivery"));
//	}
	
	@GetMapping
	@PreAuthorize("#oauth2.hasScope('driver')")
	public ResponseEntity getOpenDelivery () throws Exception {
		return Optional.ofNullable(deliveryService.findOpen())
				.map(d -> new ResponseEntity<Delivery>(d, HttpStatus.OK))
				.orElseThrow(() -> new Exception("No Open Deliveries."));
	}
	
	@PostMapping
	@PreAuthorize("#oauth2.hasScope('driver')")
	public ResponseEntity assignDelivery (@RequestParam("delivery") Long deliveryId, @RequestBody Location location) throws Exception{
		return Optional.ofNullable(deliveryService.assignDelivery(deliveryId, location))
				.map(d -> new ResponseEntity<DeliveryWithItemsDto>(d, HttpStatus.OK))
				.orElseThrow(() -> new Exception("Could not assign delivery to driver"));
	}
	
	@DeleteMapping
	@PreAuthorize("#oauth2.hasScope('driver')")
	public ResponseEntity reliquishDeliveryHold (@RequestParam("delivery") Long deliveryId) throws Exception{
		return Optional.ofNullable(deliveryService.removeHold(deliveryId))
				.map(d -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Failed to remove hold"));
	}

}
