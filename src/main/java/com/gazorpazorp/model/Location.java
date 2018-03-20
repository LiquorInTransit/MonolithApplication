package com.gazorpazorp.model;

import javax.persistence.Embeddable;

import com.gazorpazorp.model.Driver.Car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
public class Location {
	private Double latitude;
	private Double longitude;	
	private String address;
	private String city;
	private String postalCode;	
}
