package com.gazorpazorp.model.dto;

import java.util.List;

public class OrderMinimalList {
	private List<OrderMinimalDto> orders;
	
	public OrderMinimalList() {}
	public OrderMinimalList(List<OrderMinimalDto> orders) {
		this.orders = orders;
	}

	public List<OrderMinimalDto> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderMinimalDto> orders) {
		this.orders = orders;
	}
	
	
}
