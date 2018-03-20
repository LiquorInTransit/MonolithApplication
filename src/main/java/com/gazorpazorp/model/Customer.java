package com.gazorpazorp.model;

import javax.persistence.Column;
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
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	@Column(name = "id", length = 20)
	private Long id;
	@Column(name = "user_id", length = 20)
	private Long userId;
	@Column(name = "payment_method", length = 255)
	private String paymentMethod;	
	@Embedded
	private Location location;
	@Column(name="profile_image_id")
	private String profileImageId;
	@Column(name="stripe_id")
	private String stripeId;
	
	public Customer(Long userId) {
		super();
		this.userId = userId;
	}
}
