package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="QUOTE")
public class Quote {

	private Long id;
	private Long customerId;
	
	private Timestamp estimatedAt;
	
	@Embedded
	private Pickup pickup;
	@Embedded
	private Dropoff dropoff;
	
//	private double pickupLat;
//	private double pickupLong;
//	
//	private double deliveryLat;
//	private double deliveryLong;
	
	private double fee;	
	private double dropoffETA;
	
	
	public Quote() {}
	
	@PrePersist
	public void onCreate() {
		this.setEstimatedAt(new Timestamp(new Date().getTime()));
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public Timestamp getEstimatedAt() {
		return estimatedAt;
	}
	public void setEstimatedAt(Timestamp estimatedAt) {
		this.estimatedAt = estimatedAt;
	}

	
//	public double getPickupLat() {
//		return pickupLat;
//	}
//	public void setPickupLat(double pickupLat) {
//		this.pickupLat = pickupLat;
//	}
//
//	public double getPickupLong() {
//		return pickupLong;
//	}
//	public void setPickupLong(double pickupLong) {
//		this.pickupLong = pickupLong;
//	}
//
//	public double getDeliveryLat() {
//		return deliveryLat;
//	}
//	public void setDeliveryLat(double deliveryLat) {
//		this.deliveryLat = deliveryLat;
//	}
//
//	public double getDeliveryLong() {
//		return deliveryLong;
//	}
//	public void setDeliveryLong(double deliveryLong) {
//		this.deliveryLong = deliveryLong;
//	}

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

	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	@Column(name="dropoff_ETA")
	public double getDropoffETA() {
		return dropoffETA;
	}
	public void setDropoffETA(double dropoffETA) {
		this.dropoffETA = dropoffETA;
	}

	

	
	
	
}
