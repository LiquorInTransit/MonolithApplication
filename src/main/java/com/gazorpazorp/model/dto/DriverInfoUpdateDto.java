package com.gazorpazorp.model.dto;

import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.Driver.Car;

public class DriverInfoUpdateDto {
	private Car car;
	private String file;
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public void Incorporate (Driver driver) {
		if (car != null) 
			driver.setCar(car);		
	}
}
