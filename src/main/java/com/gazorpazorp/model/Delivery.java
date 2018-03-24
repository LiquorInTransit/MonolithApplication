package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Delivery {

	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	private Long id;
	private Long quoteId;
	private Long orderId;
	private Long driverId;
	@JsonIgnore
	private Long driverHold;
	@ElementCollection
	@JsonIgnore
	private List<Long> driverBlacklist;
	
	private Timestamp createdAt;
	
	@Embedded
	//@Column(name="pickup")
	private Pickup pickup;
	@Embedded
	//@Column(name="dropoff")
	private Dropoff dropoff;
	
//	private List<LineItem> items;
	
	private Double fee;
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	
	//private String trackingURL;
	private String trackingId;
	
	@PrePersist
	public void onCreate() {
		this.setCreatedAt(new Timestamp(new Date().getTime()));
	}

	@Transient
	@JsonProperty(access=JsonProperty.Access.READ_ONLY)
	public String getTrackingURL() {
		return "http://www.liquorintransit.com/api/tracking/"+this.trackingId;//trackingURL;
	}

	
	
	
	
}
