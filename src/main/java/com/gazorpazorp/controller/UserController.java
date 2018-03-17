package com.gazorpazorp.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.UserInfoUpdateDto;
import com.gazorpazorp.service.UserService;

@RestController
@RequestMapping("/api/users")
@EnableResourceServer
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	@GetMapping("/user")
//	public Principal user(Principal user) {
//		return user;
//	}
	
	//Current user mapping (for fetches and business logic)
	
	@PreAuthorize("#oauth2.hasAnyScope('customer', 'driver')")
	@PatchMapping("/me")
	public ResponseEntity updateUserById(Principal principal, @RequestBody UserInfoUpdateDto newInfo/*@RequestParam(name="email", required=false)String email, @RequestParam(name="phone", required=false)String phone, @RequestParam(name="password", required=false)String password*/) throws Exception {
		return Optional.ofNullable(userService.updateUser(Long.parseLong(principal.getName()), newInfo))
				.map(u -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("User not updated"));
	}
	
	@PreAuthorize("#oauth2.hasScope('signup')")
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) throws Exception{
		return Optional.ofNullable(userService.create(user))
				.map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
				.orElse/*Throw*/(null/*() -> new Exception("An error occured creating user")*/);
	}
	
	@PreAuthorize("#oauth2.hasScope('system')")
	@GetMapping("/{id}")
	public ResponseEntity getUserById(@PathVariable Long id) throws Exception {
		return Optional.ofNullable(userService.getUserById(id))
				.map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
				.orElseThrow(() -> new Exception("User not found"));
	}
}
