package com.gazorpazorp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserPrincipal/* implements UserDetails*/ {

	private Long userId;
	private Long customerId;
	private Long driverId;
	private String email;
	
//	@JsonIgnore
//	private String password;
//	
//	private Collection<? extends GrantedAuthority> authorities;
//	
//	private boolean enabled;
//	private boolean accountNonExpired;
//	private boolean accountNonLocked;
	
//	public UserPrincipal (Long userId, Long customerId, String email) {
//		this.userId = userId;
//		this.customerId = customerId;
//		this.email = email;
//	}
	
	
//	public static UserPrincipal create (User user) {
//		 List<GrantedAuthority> authorities = Arrays.asList(user.getRoles().split(",")).stream().map(role ->
//		 new SimpleGrantedAuthority("ROLE_"+role)
// ).collect(Collectors.toList());
//		 
//		 return new UserPrincipal (user.getId(), user.getId(), user.getEmail(), user.getPassword(), authorities, user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked());
//	}
//	
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public String getUsername() {
//		return email;
//	}

	
}
