package com.gazorpazorp.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DeliveryTracking {

	private UUID id;
//	private UUID trackingId;	
	private Long deliveryId;
	
	private Set<TrackingEvent> trackingEvents;

	private TrackingEventType status;
	private Location location;

//	@Id
//	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
//	@GeneratedValue(generator = "incrementGenerator")
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", updatable = false, nullable = false)
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

//	public UUID getTrackingId() {
//		return trackingId;
//	}
//	public void setTrackingId(UUID trackingId) {
//		this.trackingId = trackingId;
//	}

	@JsonIgnore
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	@OrderBy("createdAt desc")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryTracking", cascade = CascadeType.ALL)
	public Set<TrackingEvent> getTrackingEvents() {
		return trackingEvents;
	}
	public void setTrackingEvents(Set<TrackingEvent> trackingEvents) {
		this.trackingEvents = trackingEvents;
	}
	
	@Transient
	public TrackingEventType getStatus() {
		return status;
	}
	public void setStatus(TrackingEventType status) {
		this.status = status;
	}
	
	@Transient
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "DeliveryTracking [id=" + id.toString() + ", deliveryId=" + deliveryId + ", trackingEvents=" + trackingEvents
				+ ", status=" + status + ", location=" + location + "]";
	}
	
	
	
	
}
