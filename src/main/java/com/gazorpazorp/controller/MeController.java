package com.gazorpazorp.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.User;
import com.gazorpazorp.service.UserService;

@RestController
@RequestMapping("/me")
@EnableResourceServer
public class MeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@PreAuthorize("#oauth2.hasAnyScope('customer', 'driver')")
	public ResponseEntity me (Principal principal){
		User user = null;
		if (principal != null) {
			user = userService.getUserById(Long.parseLong(principal.getName()));
		}
		return Optional.ofNullable(user)
				.map(a -> new ResponseEntity<User>(a, HttpStatus.OK))
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}
}
