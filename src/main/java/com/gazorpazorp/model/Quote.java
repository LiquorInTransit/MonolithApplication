package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="QUOTE")
public class Quote {
	
	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Timestamp estimatedAt;
	@Embedded
	private Pickup pickup;
	@Embedded
	private Dropoff dropoff;
	private double fee;	
	@Column(name="dropoff_ETA")
	private double dropoffETA;
	
	@PrePersist
	public void onCreate() {
		this.setEstimatedAt(new Timestamp(new Date().getTime()));
	}
}
