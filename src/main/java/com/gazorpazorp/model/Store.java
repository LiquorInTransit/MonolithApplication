package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@Table(name="store")
public class Store {

	@Id
	private Long id;
	@NumberFormat(pattern="##.###########")
	@Column(columnDefinition="DECIMAL(15,13)")
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private double latitude;
	@NumberFormat(pattern="##.###########")
	@Column(columnDefinition="DECIMAL(16,13)")
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private double longitude;
	@JsonAlias("address_line_1")
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String address;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String city;
	@JsonAlias("postal_code")
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String postalCode;
	
	@Transient
	private Location location;
	
	public void Incorporate() {
		location = new Location();
		location.setAddress(this.address);
		location.setCity(this.city);
		location.setLatitude(this.latitude);
		location.setLongitude(this.longitude);
		location.setPostalCode(this.postalCode);
	}
}
