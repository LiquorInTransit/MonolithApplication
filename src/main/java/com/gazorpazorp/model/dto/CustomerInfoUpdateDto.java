package com.gazorpazorp.model.dto;

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInfoUpdateDto {
	private Location location;
	private String file;
	
	public void Incorporate (Customer customer) {
		if (location != null) 
			customer.setLocation(location);		
	}
}
