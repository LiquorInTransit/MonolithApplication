package com.gazorpazorp.LITMonolith.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.gazorpazorp.service.LITUserDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	LITUserDetailsService userDetailsService;
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomAccessTokenConverter customAccessTokenConverter;
	
	/////////////////////////BEAN Setup
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "8167255".toCharArray());
			converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
			converter.setAccessTokenConverter(customAccessTokenConverter);
			return converter;
	}	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		 DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        defaultTokenServices.setSupportRefreshToken(true);
	        return defaultTokenServices;
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory()
		.withClient("LITCustomerClient")
		.authorizedGrantTypes("password", "refresh_token")
		.authorities("ADMIN")
		.scopes("customer", "orders")
		.secret(passwordEncoder().encode("LITSecret"))
		.accessTokenValiditySeconds(82000000)
		.and()
		.withClient("LITDriverClient")
		.authorizedGrantTypes("password", "refresh_token")
		.authorities("ADMIN")
		.scopes("driver")
		.secret(passwordEncoder().encode("LITSecret"))
		.accessTokenValiditySeconds(82000000)
		.and()
		.withClient("LITSystem")
		.authorizedGrantTypes("client_credentials")
		.authorities("ADMIN")
		.scopes("system")
		.secret(passwordEncoder().encode("LITSystem"))
		.accessTokenValiditySeconds(180)
		.and()
		.withClient("LITWebClient")
		.authorizedGrantTypes("password", "refresh_token")
		.authorities("ADMIN")
		.scopes("read", "write")
		.secret(passwordEncoder().encode("LITSecret"))
		.accessTokenValiditySeconds(82000)			
		.and()
		.withClient("LITSignUpClient")
		.authorizedGrantTypes("client_credentials")
		.authorities("ADMIN")
		.scopes(/*"system", */"signup")
		.secret(passwordEncoder().encode("LITSystem"))
		.accessTokenValiditySeconds(1000);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		endpoints
			.pathMapping("/oauth/token", "/uaa/oauth/token")
			.tokenStore(tokenStore())
			.tokenEnhancer(chain)
			.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}
	@Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
	
	@Bean
	TokenEnhancer tokenEnhancer() {
		return new LITTokenEnhancer();
	}
	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	
	

}
