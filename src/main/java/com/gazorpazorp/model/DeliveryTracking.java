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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeliveryTracking {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@JsonIgnore
	private Long deliveryId;
	@OrderBy("createdAt desc")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryTracking", cascade = CascadeType.ALL)
	private Set<TrackingEvent> trackingEvents;
	@Transient
	private TrackingEventType status;
	@Transient
	private Location location;
	
	
	
	
}
