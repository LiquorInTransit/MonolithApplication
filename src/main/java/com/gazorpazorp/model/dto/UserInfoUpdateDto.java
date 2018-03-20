package com.gazorpazorp.model.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gazorpazorp.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoUpdateDto {
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String password;	
	
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
