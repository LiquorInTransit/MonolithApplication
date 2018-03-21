package com.gazorpazorp.model.dtoMapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.DriverDto;

@Mapper(componentModel = "spring")
@Qualifier("delegate")
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {

	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
	
	final String IMGUR_URL = "https://i.imgur.com/";
	final String IMGUR_DEFAULT_IMAGE = "qrgVCAy";
	
	@Mapping(target="firstName")
	@Mapping(target="phone")
	@Mapping(target="car")
	@Mapping(target="profileImageId")
	public DriverDto driverAndUserToDriverDto(Driver driver, User user);
}
