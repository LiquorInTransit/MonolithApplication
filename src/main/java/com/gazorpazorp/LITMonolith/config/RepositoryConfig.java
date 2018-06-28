package com.gazorpazorp.LITMonolith.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.gazorpazorp.model.Product;

@Configuration
	public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
	    @Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
	        config.exposeIdsFor(Product.class);
	        config.setBasePath("api");
	    }
	}