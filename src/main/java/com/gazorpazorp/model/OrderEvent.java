package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
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
@Table(name="ORDER_EVENT", indexes = {@Index(name="IDX_ORDER_EVENT_ORDER", columnList="id, order_id")})
public class OrderEvent {
	
	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	private Long id;
	@Column(name="order_id")
	private Long orderId;
	@Column(name="order_event_type")
	private OrderEventType orderEventType;
	
	//Description for cancelled events
	private String data;
	
	//Variables used when the eventType is 'UPDATED'
	@Column(name="product_id")
	private Long productId;
	private int qty;
	private Timestamp createdAt;
	
	public OrderEvent (OrderEventType orderEventType, Long orderId) {
		this.orderEventType = orderEventType;
		this.orderId = orderId;
	}
	public OrderEvent (OrderEventType orderEventType, Long orderId, String data) {
		this(orderEventType, orderId);
		this.data = data;
	}
	
	@PrePersist
	void onCreate() {
		this.setCreatedAt(new Timestamp(new Date().getTime()));
	}
}
