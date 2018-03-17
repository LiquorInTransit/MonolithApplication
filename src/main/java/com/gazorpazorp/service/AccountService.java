package com.gazorpazorp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.AccountCreationDto;

@Service
public class AccountService {

	@Autowired
	UserService userService;
	@Autowired
	CustomerService customerService;
	@Autowired
	DriverService driverService;
	
	/*Steps:
	 * 1. Check for USER by email (create and/or return USER)
	 * 2. Check for CUSTOMER by user_id
	 * 3. Create and/or return CUSTOMER
	 */
	public void createAccounts(AccountCreationDto dto, HttpServletRequest req) throws Exception {
		try {
			User user = new User(dto.getEmail(), dto.getPassword(), dto.getFirstName(), dto.getLastName(), dto.getPhone());
			user = userService.create(user);
			
			Assert.notNull(user, "An unexpected error occurred.");
				
			if (customerService.createCustomer(new Customer(user.getId()), dto.getEmail())==null) {
				//handle the error
			}
			if (driverService.createDriver(new Driver(user.getId()), dto.getFirstName(), dto.getLastName(), req)==null) {
				//handle the error
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("There was an error creating the accounts");
		}
	}
}
