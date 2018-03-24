package com.gazorpazorp.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "tracking_event")
public class TrackingEvent {

	@Id
	@JsonIgnore
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator = "incrementGenerator")
	private Long id;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tracking_id", nullable = false)
	private DeliveryTracking deliveryTracking;
//	@JsonIgnore
//	private Long deliveryId;
	
	@Enumerated(EnumType.STRING)
	private TrackingEventType trackingEventType;

	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private Location location;
	
	private Timestamp createdAt;
	
}
