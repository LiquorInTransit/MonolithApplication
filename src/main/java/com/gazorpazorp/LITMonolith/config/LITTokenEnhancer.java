package com.gazorpazorp.LITMonolith.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.gazorpazorp.model.User;
import com.gazorpazorp.repository.UserRepository;

public class LITTokenEnhancer implements TokenEnhancer {
	@Autowired
	UserRepository userRepository;
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		User user = userRepository.findByEmail(authentication.getName());
		if (user != null) {
			additionalInfo.put("userId", user.getId());
			Set<String> scopes =  accessToken.getScope();
			if (scopes.contains("customer")) {
				additionalInfo.put("customerId", user.getId());
			} else if (scopes.contains("driver")) {
				additionalInfo.put("driverId", user.getId());
			}
			((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
		}
		return accessToken;
	}
}