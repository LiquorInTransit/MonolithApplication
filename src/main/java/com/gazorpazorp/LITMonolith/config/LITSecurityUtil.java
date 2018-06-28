package com.gazorpazorp.LITMonolith.config;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import com.gazorpazorp.model.UserPrincipal;

@Component
public class LITSecurityUtil {

	public static UserPrincipal currentUser() {
		Map<String, Object> info = getInfo();
		Long userId = Long.parseLong(String.valueOf(info.get("userId")));
		Long customerId =  Optional.ofNullable(info.get("customerId")).map(id -> Long.parseLong(String.valueOf(id))).orElse(null);
		Long driverId = Optional.ofNullable(info.get("driverId")).map(id -> Long.parseLong(String.valueOf(id))).orElse(null);
		String email = (String) info.get("user_name");
		return new UserPrincipal(userId, customerId, driverId, email);
	}

	public static Map<String, Object> getInfo () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		return (Map<String, Object>) oauthDetails.getDecodedDetails();
	}
}
