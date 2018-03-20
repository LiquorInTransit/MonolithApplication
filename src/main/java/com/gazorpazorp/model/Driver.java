package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "DRIVER")
public class Driver {

	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	@Column(name = "id", length = 20)
	private Long id;
	@Column(name = "user_id", length = 20)
	private Long userId;
	@Column(name="stripe_id")
	private String stripeId;
	@Embedded
	private Car car;
	@Column(name="profile_image_id")
	private String profileImageId;
	
	public Driver(Long userId) {
		this.userId = userId;
	}
	
	@Embeddable
	@ToString
	@NoArgsConstructor
	@Getter
	@Setter
	public static class Car {
		private String make;
		private String model;
		private String year;
		private String colour;
		private String plate;
	}
}
