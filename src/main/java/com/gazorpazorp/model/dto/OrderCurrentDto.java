package com.gazorpazorp.model.dto;

import com.gazorpazorp.model.Order;

//This DTO is just an order, as well as some basic driver info, if available
public class OrderCurrentDto {
	
	private Order order;
	private DriverDto driver;
	
	public OrderCurrentDto() {}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public DriverDto getDriver() {
		return driver;
	}

	public void setDriver(DriverDto driver) {
		this.driver = driver;
	}
	
	
}
