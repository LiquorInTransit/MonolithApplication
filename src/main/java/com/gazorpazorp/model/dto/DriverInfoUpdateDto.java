package com.gazorpazorp.model.dto;

import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.Driver.Car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverInfoUpdateDto {
	private Car car;
	private String file;
	
	public void Incorporate (Driver driver) {
		if (car != null) 
			driver.setCar(car);		
	}
}
