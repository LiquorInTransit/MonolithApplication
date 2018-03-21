package com.gazorpazorp.model.dto;

import java.util.List;

import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.LineItem;

public class DeliveryWithItemsDto {
	
	private Delivery delivery;
	private List<LineItem> items;
	
	public DeliveryWithItemsDto() {}
	
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public List<LineItem> getItems() {
		return items;
	}
	public void setItems(List<LineItem> items) {
		this.items = items;
	}
	
	
}
