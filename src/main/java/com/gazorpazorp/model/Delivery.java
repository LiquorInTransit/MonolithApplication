package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Delivery {

	private Long id;
	private Long quoteId;
	private Long orderId;
	private Long driverId;
	
	private Long driverHold;
	private List<Long> driverBlacklist;
	
	private Timestamp createdAt;
	
	@Embedded
	//@Column(name="pickup")
	private Pickup pickup;
	@Embedded
	//@Column(name="dropoff")
	private Dropoff dropoff;
	
//	private List<LineItem> items;
	
	private Double fee;
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	
	//private String trackingURL;
	private String trackingId;
	
	
	public Delivery () {}
	
	@PrePersist
	public void onCreate() {
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

	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	
	@JsonIgnore
	public Long getDriverHold() {
		return driverHold;
	}
	public void setDriverHold(Long driverHold) {
		this.driverHold = driverHold;
	}

	@ElementCollection
	@JsonIgnore
	public List<Long> getDriverBlacklist() {
		return driverBlacklist;
	}
	public void setDriverBlacklist(List<Long> driverBlacklist) {
		this.driverBlacklist = driverBlacklist;
	}

	public Pickup getPickup() {
		return pickup;
	}
	public void setPickup(Pickup pickup) {
		this.pickup = pickup;
	}

	public Dropoff getDropoff() {
		return dropoff;
	}
	public void setDropoff(Dropoff dropoff) {
		this.dropoff = dropoff;
	}
	
//	@Transient
//	public List<LineItem> getItems() {
//		return items;
//	}
//	public void setItems(List<LineItem> items) {
//		this.items = items;
//	}

	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}

	public DeliveryStatus getStatus() {
		return status;
	}
	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	@Transient
	@JsonProperty(access=JsonProperty.Access.READ_ONLY)
	public String getTrackingURL() {
		return "http://www.liquorintransit.com/api/tracking/"+this.trackingId;//trackingURL;
	}
//	public void setTrackingURL(String trackingURL) {
//		this.trackingURL = trackingURL;
//	}

	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	
	
	
	
	
}
