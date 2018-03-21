package com.gazorpazorp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.Inventory;
import com.gazorpazorp.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	@GetMapping
	public ResponseEntity getInventoryForProductIds (@RequestParam("productIds")String productIds, @RequestParam("quote") Long quoteId) throws Exception {
		return Optional.ofNullable(inventoryService.getInventoryForProductIds(productIds, quoteId))
				.map(s -> new ResponseEntity<List<Inventory>>(s, HttpStatus.OK))
				.orElseThrow(() -> new Exception("There was an error fetching inventories."));
	}

	
	
}
