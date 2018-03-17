package com.gazorpazorp.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.dto.UserInfoUpdateDto;
import com.gazorpazorp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("#oauth2.hasAnyScope('customer', 'driver')")
	@PatchMapping("/me")
	public ResponseEntity updateUserById(Principal principal, @RequestBody UserInfoUpdateDto newInfo) throws Exception {
		return Optional.ofNullable(userService.updateUser(Long.parseLong(principal.getName()), newInfo))
				.map(u -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("User not updated"));
	}
}
