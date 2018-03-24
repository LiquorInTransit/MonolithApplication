package com.gazorpazorp.model.dto;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.gazorpazorp.model.Location;

@Embeddable
public class StoreDto {
	private Long id;
	
	@Embedded
	private Location location;
	
	public StoreDto() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", location=" + location + "]";
	}
	
	
	
	
	
}
