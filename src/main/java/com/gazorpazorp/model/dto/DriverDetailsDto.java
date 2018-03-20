package com.gazorpazorp.model.dto;

import com.gazorpazorp.model.Driver.Car;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DriverDetailsDto {
	private Long id;
	private String stripeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Car car;
	private String profileImageUrl;
}
