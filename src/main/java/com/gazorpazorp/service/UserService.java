package com.gazorpazorp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.UserInfoUpdateDto;
import com.gazorpazorp.repository.UserRepository;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepo;
	
	public User getUserById(Long id) {
		return userRepo.findById(id).get();
	}
	public User updateUser(Long id, UserInfoUpdateDto userInfo) {
			User user = getUserById(id);
			userInfo.incorporate(user);
			return userRepo.save(user);
	}
	
	public User create(User user) {
		User existing = userRepo.findByEmail(user.getEmail());
		Assert.isNull(existing, "User already exists: " + user.getEmail());
		String oldPassword = user.getPassword();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setRoles("CUSTOMER,DRIVER");
		
		user =  userRepo.save(user);
		user.setPassword(oldPassword);
		return user;
	}	
}
