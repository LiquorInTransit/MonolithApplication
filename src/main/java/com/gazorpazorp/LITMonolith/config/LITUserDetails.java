package com.gazorpazorp.LITMonolith.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gazorpazorp.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LITUserDetails implements UserDetails{

//	private Long userId;
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	
	public static LITUserDetails create (User user) {
		 List<GrantedAuthority> authorities = Arrays.asList(user.getRoles().split(",")).stream().map(role ->
		 new SimpleGrantedAuthority("ROLE_"+role)
				 ).collect(Collectors.toList());
		 
		 return new LITUserDetails (user.getEmail(), user.getPassword(), authorities, user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked());
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getUsername() {
		return email;
	}

	
}
