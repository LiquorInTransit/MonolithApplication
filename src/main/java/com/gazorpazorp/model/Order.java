package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="LCBO_ORDER")
public class Order {

	private Long id;
	private Long customerId;		
	
//	private String deliveryLocation;
//	private String storeLocation;
	private double total;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private Set<LineItem> items;
	
	
	private Timestamp createdAt;
	
	private String trackingURL;
	
	public Order() {}
	
	@PrePersist
	void onCreate() {
		this.setCreatedAt(new Timestamp(new Date().getTime()));
	}


	
	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="customer_id")
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name="total")
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

//	@Column(name="delivery_location")
//	public String getDeliveryLocation() {
//		return deliveryLocation;
//	}
//	public void setDeliveryLocation(String deliveryLocation) {
//		this.deliveryLocation = deliveryLocation;
//	}
//
//	@Column(name="store_location")
//	public String getStoreLocation() {
//		return storeLocation;
//	}
//	public void setStoreLocation(String storeLocation) {
//		this.storeLocation = storeLocation;
//	}

	@Column(name="status")
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	public Set<LineItem> getItems() {
		return items;
	}
	public void setItems(Set<LineItem> items) {
		this.items = items;
	}

	@Column(name = "created_at")
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Transient
	public String getTrackingURL() {
		return trackingURL;
	}
	public void setTrackingURL(String trackingURL) {
		this.trackingURL = trackingURL;
	}
	
	
	
}
