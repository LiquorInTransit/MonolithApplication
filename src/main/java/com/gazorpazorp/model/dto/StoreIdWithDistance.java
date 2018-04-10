package com.gazorpazorp.model.dto;

import com.gazorpazorp.model.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreIdWithDistance {
	private Long id;
	private double distance;
	public StoreIdWithDistance(Long id, double distance) {
		this.id = id;
		this.distance = distance;
	}
}
