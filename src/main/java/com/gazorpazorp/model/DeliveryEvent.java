//package com.gazorpazorp.model;
//
//import java.sql.Timestamp;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Index;
//import javax.persistence.PrePersist;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Entity
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@Table(name="DELIVERY_EVENT", indexes={@Index(name="IDX_DELIVERY_EVENT_DELIVERY", columnList="id, delivery_id")})
//public class DeliveryEvent {
//
//	@Id
//	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
//	@GeneratedValue(generator="incrementGenerator")
//	private Long id;
//	@Column(name="delivery_id")
//	private Long deliveryId;
//	@Column(name="delivery_event_type")
//	private DeliveryEventType deliveryEventType;
//	
//	//Description for cancelled deliveries, etc.
//	private String data;
//	
//	//Used when driver declines the delivery
//	private Long driverDeclinedId;
//	private Long driverHeldId;
//	
//	private Timestamp createdAt;
//	
//	public DeliveryEvent (DeliveryEventType deliveryEventType, Long deliveryId) {
//		this.deliveryEventType = deliveryEventType;
//		this.deliveryId = deliveryId;
//	}
//	public DeliveryEvent (DeliveryEventType deliveryEventType, Long deliveryId, String data) {
//		this.deliveryEventType = deliveryEventType;
//		this.deliveryId = deliveryId;
//		this.data = data;
//	}
//	
//	@PrePersist
//	void onCreate() {
//		this.setCreatedAt(new Timestamp(new Date().getTime()));
//	}
//}
