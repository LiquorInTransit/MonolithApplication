package com.gazorpazorp.model;

import javax.persistence.Embeddable;

@Embeddable
public class Location {
	private Double latitude;
	private Double longitude;	

	private String address;
	private String city;
	private String postalCode;
	
	public Location() {}

	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Location [latitude=" + latitude + ", longitude=" + longitude + ", address=" + address + ", city=" + city
				+ ", postalCode=" + postalCode + "]";
	}
	
	
}
