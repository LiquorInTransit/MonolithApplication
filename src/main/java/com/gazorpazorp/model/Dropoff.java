package com.gazorpazorp.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Dropoff {
	private String customerName;
	@Embedded
	private Location location;
	private Integer dropoffETA;
}
