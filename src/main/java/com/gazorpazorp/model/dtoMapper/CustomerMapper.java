package com.gazorpazorp.model.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.CustomerDetailsDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
	
	@Mapping(source="customer.id", target="id")
	@Mapping(target="firstName")
	@Mapping(target="lastName")
	@Mapping(target="email")
	@Mapping(target="phone")
	@Mapping(source="customer.profileImageId", target="profileImageUrl")
	CustomerDetailsDto customerAndUserToCustomerDetailsDto(Customer customer, User user);
	
	@Mapping(source="userId", target="id")
	@Mapping(target="firstName")
	@Mapping(target="lastName")
	@Mapping(target="email")
	@Mapping(target="phone")
	User customerDetailsDtoToUser(CustomerDetailsDto dto, Long userId);
	
	@Mapping(source="dto.id", target="id")
	@Mapping(source="userId", target="userId")
	Customer customerDetailsDtoToCustomer(CustomerDetailsDto dto, Long userId);
}
