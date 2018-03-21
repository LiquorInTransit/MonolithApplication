package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Pickup {
	@Embedded
	private Store store;
	private Integer pickupETA;
	
	public Pickup(Store store) {
		this.store = store;
	}	
}
