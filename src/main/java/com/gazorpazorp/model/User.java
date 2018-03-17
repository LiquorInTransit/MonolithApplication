package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USER")
public class User {
	//Sign in details
	private Long id;
	
	//TODO:remove username
//	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;
	private String roles;

	//Revocation details
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	
	public User() {}
	
	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	@Column(name = "id", length = 30)
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
//	@Column(name = "username", length = 30, unique=true)
//	public String getUsername() {
//		return this.username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "password", length = 60)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "roles", length = 50)
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "non_expired")
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Column(name = "non_locked")
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", enabled=" + enabled
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked + "]";
	}
	
	
	
	
	
}
