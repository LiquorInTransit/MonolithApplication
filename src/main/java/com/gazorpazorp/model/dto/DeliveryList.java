package com.gazorpazorp.model.dto;

import java.util.List;

import com.gazorpazorp.model.Delivery;

public class DeliveryList {
	private List<Delivery> deliveries;
	
	public DeliveryList() {}
	public DeliveryList(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}
	
	public List<Delivery> getDeliveries() {
		return deliveries;
	}
	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}
	
	
}
