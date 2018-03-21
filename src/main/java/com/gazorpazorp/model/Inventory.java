package com.gazorpazorp.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
	
	@JsonAlias("product_id")
	private Long productId;
	@JsonAlias("quantity")
	private Integer count;
}
