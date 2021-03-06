package com.gazorpazorp.LITMonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableAsync
@SpringBootApplication(scanBasePackages="com.gazorpazorp")
@EnableJpaRepositories("com.gazorpazorp.repository")
@EntityScan(basePackages="com.gazorpazorp")
@EnableFeignClients("com.gazorpazorp.client")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LITMonolithApplication {
	
//	@PostConstruct
//	public void getDbManager(){
//	   DatabaseManagerSwing.main(
//		new String[] { "--url", "jdbc:hsqldb:mem:test://localhost/test?characterEncoding=UTF-8", "--user", "SA", "--password", ""});
//	}


//	@Autowired
//	LITUserDetailsService userDetailsService;
//
	public static void main(String[] args) {
		SpringApplication.run(LITMonolithApplication.class, args);
	}
//	
//	//Product and Store Utilities
//	@Autowired
//	ProductRepositoryCreationService PRCService;
//	
//	
//	@Autowired
//	Environment env;
//	@PostConstruct
//	public void getProducts() {
////		if (Arrays.asList(env.getActiveProfiles()).contains("dev"))
////			PRCService.start();
//	}
//	@Configuration
//	public static class RepositoryConfig extends RepositoryRestConfigurerAdapter {
//	    @Override
//		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//	        config.exposeIdsFor(Product.class);
//	        config.setBasePath("api");
//	    }
//	}
//	
//	
//	
//	
//	@Autowired
//	public void init(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
//
//	@Configuration
//	@EnableAuthorizationServer
//	protected static class OAuthConfig extends AuthorizationServerConfigurerAdapter {
//
//		@Bean
//		protected JwtAccessTokenConverter accessTokenConverter() {
//			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//			KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "8167255".toCharArray());
//			converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
//			return converter;
//		}
////		@Autowired
////		TokenEnhancer customTokenEnhancer;
//		@Bean
//		public TokenStore tokenStore() {
//			return new JwtTokenStore(accessTokenConverter());
//		}
//		
//		@Bean
//		@Primary
//		public DefaultTokenServices tokenServices() {
//			DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//	        defaultTokenServices.setTokenStore(tokenStore());
//	        defaultTokenServices.setSupportRefreshToken(true);
//	        return defaultTokenServices;
//		}
//		
////		@Autowired
////		AuthenticationManager authenticationManager;
//
//
//		//This can stay the way it is
//		/* (non-Javadoc)
//		 * scope definitions:
//		 * cutomer: only for customers. doubles as 'customer profile'
//		 * driver: only for drivers. doubles as 'driver profile'
//		 * orders: allowed to view orders (technically customer only)
//		 * signup: allowed to make signup requests
//		 * system: only allowed for the system. no user/clients allowed
//		*/
//		
//		@Override
//		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//			clients
//			.inMemory()
//			.withClient("LITCustomerClient")
//			.authorizedGrantTypes("password", "refresh_token")
//			.authorities("ADMIN")
//			.scopes("customer", "orders")
//			.secret("LITSecret")
//			.accessTokenValiditySeconds(82000000)
//			.and()
//			.withClient("LITDriverClient")
//			.authorizedGrantTypes("password", "refresh_token")
//			.authorities("ADMIN")
//			.scopes("driver")
//			.secret("LITSecret")
//			.accessTokenValiditySeconds(82000000)
//			.and()
//			.withClient("LITSystem")
//			.authorizedGrantTypes("client_credentials")
//			.authorities("ADMIN")
//			.scopes("system")
//			.secret("LITSystem")
//			.accessTokenValiditySeconds(180)
//			.and()
//			.withClient("LITWebClient")
//			.authorizedGrantTypes("password", "refresh_token")
//			.authorities("ADMIN")
//			.scopes("read", "write")
//			.secret("LITSecret")
//			.accessTokenValiditySeconds(82000)			
//			.and()
//			.withClient("LITSignUpClient")
//			.authorizedGrantTypes("client_credentials")
//			.authorities("ADMIN")
//			.scopes(/*"system", */"signup")
//			.secret("LITSystem")
//			.accessTokenValiditySeconds(1000);
//		}		
//
//		@Autowired
//		LITUserDetailsService userDetailsService;
//		@Override
//		public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//			TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//			tokenEnhancerChain.setTokenEnhancers(Arrays.asList(/*customTokenEnhancer, */accessTokenConverter()));
//			
//			endpoints
//			.pathMapping("/oauth/token", "/uaa/oauth/token")
//			.tokenStore(tokenStore())
//			//.accessTokenConverter(accessTokenConverter())
//			.tokenEnhancer(tokenEnhancerChain)
//			.exceptionTranslator(loggingExceptionTranslator())
//			/*.authenticationManager(authenticationManager)*/.userDetailsService(userDetailsService);
//		}		
//		
//		@Bean
//	    public WebResponseExceptionTranslator loggingExceptionTranslator() {
//	        return new DefaultWebResponseExceptionTranslator() {
//	            @Override
//	            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
//	                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
//	                e.printStackTrace();
//
//	                // Carry on handling the exception
//	                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
//	                HttpHeaders headers = new HttpHeaders();
//	                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
//	                OAuth2Exception excBody = responseEntity.getBody();
//	                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
//	            }
//	        };
//	    }
//	}	
//	
//	@Configuration
//	@EnableResourceServer
//	protected static class ResourceConfig extends ResourceServerConfigurerAdapter {
//
//		@Override
//		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//			// TODO Auto-generated method stub
//			super.configure(resources);
//		}
//
//		@Override
//		public void configure(HttpSecurity http) throws Exception {
//			// TODO Auto-generated method stub
//			
//			http.authorizeRequests().antMatchers("/api/**").fullyAuthenticated();
//			http.authorizeRequests().antMatchers("/**").permitAll();
//			
//			super.configure(http);
//		}
//		
//	}
	
//	@Bean
//	@Profile("!dev")
//	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils utils) 
//	{
////		EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(utils);
//		final EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(utils)
//		{
//			@Scheduled(initialDelay = 30000L, fixedRate = 30000L)
//			public void refreshInfo() {
//				AmazonInfo newInfo = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
//				if (!this.getDataCenterInfo().equals(newInfo)) {
//					((AmazonInfo) this.getDataCenterInfo()).setMetadata(newInfo.getMetadata());
//					this.setHostname(newInfo.get(AmazonInfo.MetaDataKey.publicHostname));
//					this.setIpAddress(newInfo.get(AmazonInfo.MetaDataKey.publicIpv4));
//					this.setDataCenterInfo(newInfo);
//					this.setNonSecurePort(8080);
//				}
//			}         
//		};
//		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
//		instance.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
//		instance.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
//		instance.setDataCenterInfo(info);
//		instance.setNonSecurePort(8080);
//		return instance;
//	}
}
