package com.gazorpazorp.model.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gazorpazorp.model.User;

public class UserInfoUpdateDto {
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String password;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void incorporate (User user) {
		if (firstName != null)
			user.setFirstName(firstName);
		if (lastName != null)
			user.setLastName(lastName);
		if (email != null)
			user.setEmail(email);
		if (phone != null)
			user.setPhone(phone);
		if (password != null) {
			user.setPassword(new BCryptPasswordEncoder().encode(password));
		}
	}
}
