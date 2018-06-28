package com.gazorpazorp.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gazorpazorp.LITMonolith.config.LITSecurityUtil;
import com.gazorpazorp.model.dto.CustomerDetailsDto;
import com.gazorpazorp.model.dto.DriverDetailsDto;
import com.gazorpazorp.model.dtoMapper.CustomerMapper;
import com.gazorpazorp.model.dtoMapper.DriverMapper;

@Service
public class MeService {

	
	private final String IMGUR_URL = "https://i.imgur.com/";
	private final String IMGUR_DEFAULT_IMAGE = "qrgVCAy.jpg";
	
	@Autowired
	CustomerService customerService;
	@Autowired
	DriverService driverService;
	@Autowired
	UserService userService;

	public CustomerDetailsDto getCustomer() {
		Long id = LITSecurityUtil.currentUser().getUserId();//Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		CustomerDetailsDto dto = CustomerMapper.INSTANCE.customerAndUserToCustomerDetailsDto(customerService.getCurrentCustomer(), userService.getUserById(id));
		String imageUrl = dto.getProfileImageUrl();
		if (StringUtils.isBlank(imageUrl))
			dto.setProfileImageUrl(IMGUR_URL+IMGUR_DEFAULT_IMAGE);
		else
			dto.setProfileImageUrl(IMGUR_URL+dto.getProfileImageUrl()+".jpg");
		return dto;
	}
	
	public DriverDetailsDto getDriver() {
		Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		DriverDetailsDto dto = DriverMapper.INSTANCE.driverAndUserToDriverDetailsDto(driverService.getCurrentDriver(), userService.getUserById(id));
		String imageUrl = dto.getProfileImageUrl();
		if (StringUtils.isBlank(imageUrl))
			dto.setProfileImageUrl(IMGUR_URL+IMGUR_DEFAULT_IMAGE);
		else
			dto.setProfileImageUrl(IMGUR_URL+dto.getProfileImageUrl()+".jpg");
		return dto;
	}
}
