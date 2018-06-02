package com.gazorpazorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gazorpazorp.model.OrderEvent;
import com.gazorpazorp.model.OrderEventType;
import com.gazorpazorp.model.dto.CustomerDetailsDto;
import com.gazorpazorp.model.dto.DriverDetailsDto;
import com.gazorpazorp.service.MeService;
import com.gazorpazorp.service.OrderService;

@Controller
public class HomeController {

	@Autowired
	MeService meService;
	
	@Autowired
	OrderService orderService;
	
	
	@GetMapping("/")
	public String home () {
		return "home";
	}	

	@GetMapping("/api/me")
	@PreAuthorize("#oauth2.hasScope('customer')")
	public ResponseEntity<CustomerDetailsDto> me () throws Exception {
		return Optional.ofNullable(meService.getCustomer())
				.map(c -> new ResponseEntity<CustomerDetailsDto>(c, HttpStatus.OK))
				.orElseThrow(() -> new Exception("An error occured"));
	}	
	
	@GetMapping("/api/drivers/me")
	@PreAuthorize("#oauth2.hasScope('driver')")
	public ResponseEntity<DriverDetailsDto> driverMe () throws Exception {
		return Optional.ofNullable(meService.getDriver())
				.map(d -> new ResponseEntity<DriverDetailsDto>(d, HttpStatus.OK))
				.orElseThrow(() -> new Exception("An error occured"));
	}
	@GetMapping("/api/test/{id}")
	@PreAuthorize("#oauth2.hasScope('customer')")
	public void test (@PathVariable Long id) {
		orderService.addOrderEvent(new OrderEvent(OrderEventType.COMPLETED, id), false);
	}
}
