package com.gazorpazorp.model.dto;

import com.gazorpazorp.model.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailsDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Location location;
	private String profileImageUrl;
}
