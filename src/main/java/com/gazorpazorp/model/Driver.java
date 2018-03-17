package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DRIVER")
public class Driver {

	private Long id;
	private Long userId;
	private String stripeId;
	@Embedded
	private Car car;
	
	private String profileImageId;
	
	public Driver() {}
	public Driver(Long userId) {
		super();
		this.userId = userId;
	}


	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	@Column(name = "id", length = 20)
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "user_id", length = 20)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	
	@Column(name="profile_image_id")
	public String getProfileImageId() {
		return profileImageId;
	}
	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}
		
	@Column(name="stripe_id")
	public String getStripeId() {
		return stripeId;
	}
	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}
	
	@Override
	public String toString() {
		return "Driver [id=" + id + ", userId=" + userId + ", car=" + car + ", profileImageId=" + profileImageId + "]";
	}
	
	@Embeddable
	public static class Car {
		private String make;
		private String model;
		private String year;
		private String colour;
		private String plate;
		
		public Car() {}
		
		public String getMake() {
			return make;
		}

		public void setMake(String make) {
			this.make = make;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getColour() {
			return colour;
		}

		public void setColour(String colour) {
			this.colour = colour;
		}

		public String getPlate() {
			return plate;
		}

		public void setPlate(String plate) {
			this.plate = plate;
		}
		

		@Override
		public String toString() {
			return "Car [make=" + make + ", model=" + model + ", year=" + year + ", colour=" + colour + ", plate="
					+ plate + "]";
		}	
	}
}
