package com.gazorpazorp.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import feign.RequestInterceptor;

@Configuration
@EnableConfigurationProperties
public class ImgurTokenRequestPasswordConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "imgur.oauth2.resource")
	public ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails() {
		return new ResourceOwnerPasswordResourceDetails();
	}
	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(){
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceOwnerPasswordResourceDetails());
	}
	@Bean
	public OAuth2RestTemplate resourceOwnerRestTemplate() {
		return new OAuth2RestTemplate(resourceOwnerPasswordResourceDetails());
	}
	
}
