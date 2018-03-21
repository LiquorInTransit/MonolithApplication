package com.gazorpazorp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gazorpazorp.model.Driver.Car;

public class DriverDto {

	private String firstName;
	private String phone;
	private Car car;	
	private String profileImageId;
	private String profileImageUrl;
	
	public DriverDto () {}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@JsonIgnore
	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	
}
