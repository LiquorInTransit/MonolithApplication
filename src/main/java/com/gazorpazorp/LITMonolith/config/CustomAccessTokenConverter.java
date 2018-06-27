package com.gazorpazorp.LITMonolith.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

	@Override
	public OAuth2Authentication extractAuthentication(java.util.Map<String,?> map) {
		OAuth2Authentication authentication = super.extractAuthentication(map);
		authentication.setDetails(map);
		return authentication;
	}

}
