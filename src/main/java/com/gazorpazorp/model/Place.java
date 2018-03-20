package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="PLACE")
public class Place {

	@Id
	@JsonIgnore
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	private Long id;
	@JsonIgnore
	@Column(name="customer_id")
	private Long customerId;		
	private String name;	
	@Embedded
	private Location location;
	
	public Place(String name, Location location, Long customerId) {
		this.name = name;
		this.location = location;
		this.customerId = customerId;
	}
}
