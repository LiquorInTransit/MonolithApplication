package com.gazorpazorp.model.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.DriverDto;

public abstract class AccountMapperDecorator implements AccountMapper {
	
	@Autowired
	@Qualifier("delegate")
	private AccountMapper delegate;
	
	@Override
	public DriverDto driverAndUserToDriverDto(Driver driver, User user) {
		DriverDto dto = delegate.driverAndUserToDriverDto(driver, user);
		dto.setProfileImageUrl(IMGUR_URL+(dto.getProfileImageId()!=null?dto.getProfileImageId():IMGUR_DEFAULT_IMAGE)+".jpg");
		return dto;
	}
}
