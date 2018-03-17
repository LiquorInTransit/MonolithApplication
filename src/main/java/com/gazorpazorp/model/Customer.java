package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

	private Long id;
	private Long userId;
	private String paymentMethod;	
	@Embedded
	private Location location;
	private String profileImageId;
	private String stripeId;
	
	public Customer() {}
	

	public Customer(Long userId) {
		super();
		this.userId = userId;
	}


	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	@Column(name = "id", length = 20)
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "user_id", length = 20)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
//	public Double getLatitude() {
//		return latitude;
//	}
//	public void setLatitude(Double latitude) {
//		this.latitude = latitude;
//	}
//
//	public Double getLongitude() {
//		return longitude;
//	}
//	public void setLongitude(Double longitude) {
//		this.longitude = longitude;
//	}
//
//
//	@Column(name = "address", length = 255)
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}

	@Column(name = "payment_method", length = 255)
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	@Column(name="profile_image_id")
	public String getProfileImageId() {
		return profileImageId;
	}
	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}
	@Column(name="stripe_id")
	public String getStripeId() {
		return stripeId;
	}
	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}
	
	
	
	
}
