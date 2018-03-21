package com.gazorpazorp.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dataset {
	public Dataset() {}
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("total_products")
	private Long totalProducts;
	@JsonProperty("total_stores")
	private Long totalStores;
	
	@JsonProperty("created_at")
	private Date createdAt;
	
	@JsonProperty("product_ids")
	private List<Long> productIds;
	@JsonProperty("store_ids")
	private List<Long> storeIds;
	
	@JsonProperty("added_product_ids")
	private List<Long> addedProductIds;
	@JsonProperty("added_store_ids")
	private List<Long> addedStoreIds;
	@JsonProperty("removed_product_ids")
	private List<Long> removedProductIds;
	@JsonProperty("removed_store_ids")
	private List<Long> removedStoreIds;
}
