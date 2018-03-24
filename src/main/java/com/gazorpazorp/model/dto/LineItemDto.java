package com.gazorpazorp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gazorpazorp.model.Product;

public class LineItemDto {
	@JsonIgnore
	private Long productId;
	private Product product;
	private int qty;
	
	public LineItemDto () {}
	public LineItemDto (Long productId, Product product, int qty) {
		this.productId = productId;
		this.product = product;
		this.qty = qty;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
}
