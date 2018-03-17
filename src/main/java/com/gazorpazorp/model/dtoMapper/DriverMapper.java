package com.gazorpazorp.model.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.DriverDetailsDto;

@Mapper(componentModel = "spring")
public interface DriverMapper {

	DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);
	
	@Mapping(source="driver.id", target="id")
	@Mapping(source="driver.stripeId", target="stripeId")
	@Mapping(target="firstName")
	@Mapping(target="lastName")
	@Mapping(target="email")
	@Mapping(target="phone")
	@Mapping(target="car")
	@Mapping(source="driver.profileImageId", target="profileImageUrl")
	DriverDetailsDto driverAndUserToDriverDetailsDto(Driver driver, User user);
	
	@Mapping(source="userId", target="id")
	@Mapping(target="firstName")
	@Mapping(target="lastName")
	@Mapping(target="email")
	@Mapping(target="phone")
	User driverDetailsDtoToUser(DriverDetailsDto dto, Long userId);
	
	@Mapping(source="dto.id", target="id")
	@Mapping(source="userId", target="userId")
	@Mapping(source="dto.car", target="car")
	Driver driverDetailsDtoToDriver(DriverDetailsDto dto, Long userId);
}
