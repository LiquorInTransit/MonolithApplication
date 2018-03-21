package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tracking_event")
public class TrackingEvent {

	@JsonIgnore
	private Long id;
	private DeliveryTracking deliveryTracking;
//	@JsonIgnore
//	private Long deliveryId;
	
	@Enumerated(EnumType.STRING)
	private TrackingEventType trackingEventType;

	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private Location location;
	
	private Timestamp createdAt;
	
	public TrackingEvent() {
	}

	

	@PrePersist
	void onCreate() {
		this.setCreatedAt(new Timestamp(new Date().getTime()));
	}

	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator = "incrementGenerator")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tracking_id", nullable = false)
	public DeliveryTracking getDeliveryTracking() {
		return deliveryTracking;
	}
	public void setDeliveryTracking(DeliveryTracking deliveryTracking) {
		this.deliveryTracking = deliveryTracking;
	}

	//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public TrackingEventType getTrackingEventType() {
		return trackingEventType;
	}
	public void setTrackingEventType(TrackingEventType trackingEventType) {
		this.trackingEventType = trackingEventType;
	}

//	public Long getDeliveryId() {
//		return deliveryId;
//	}
//	public void setDeliveryId(Long deliveryId) {
//		this.deliveryId = deliveryId;
//	}

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "TrackingEvent [id=" + id + ", trackingEventType=" + trackingEventType
				+ ", location=" + location + ", createdAt=" + createdAt + "]";
	}
}
