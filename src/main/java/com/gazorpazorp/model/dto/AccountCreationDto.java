package com.gazorpazorp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountCreationDto {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
}
