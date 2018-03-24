package com.gazorpazorp.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.gazorpazorp.model.dto.StoreDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Pickup {
	@Embedded
	private StoreDto store;
	private Integer pickupETA;
	
	public Pickup(StoreDto store) {
		this.store = store;
	}	
}
