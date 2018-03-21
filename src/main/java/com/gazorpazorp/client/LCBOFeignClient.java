package com.gazorpazorp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gazorpazorp.model.Inventory;
import com.gazorpazorp.model.InventoryResult;
import com.gazorpazorp.model.ProductResult;
import com.gazorpazorp.model.StoreListResult;
import com.gazorpazorp.model.StoreResult;

import feign.Headers;
import feign.hystrix.FallbackFactory;

@FeignClient(name="lcbo-client"/*, url="${lcbo-client.url}"*/) //Name can be custom name defined in bootstrap.yml, or the name of a service registered with eureka
public interface LCBOFeignClient {
	
	@GetMapping(value="/products/{id}", consumes = "application/json")
	@Headers(value = { "Authorization: Token ${lcbo-client.key}", "Content-Type: application/json"})
	ProductResult getProductById(@PathVariable("id") Long id);
	
	@GetMapping(value="/stores", consumes = "application/json")
	@Headers(value = { "Authorization: Token ${lcbo-client.key}", "Content-Type: application/json"})
	StoreListResult getStoresNearPoints(@RequestParam("lat")double latitude, @RequestParam("lon")double longitude);
	
	@GetMapping(value="/stores/{id}", consumes = "application/json")
	@Headers(value = { "Authorization: Token ${lcbo-client.key}", "Content-Type: application/json"})
	StoreResult getStoreById(@PathVariable("id") Long id);
}

