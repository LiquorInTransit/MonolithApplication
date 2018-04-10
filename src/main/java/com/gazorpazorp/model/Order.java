package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="LCBO_ORDER")
public class Order {

	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	private Long id;
	@Column(name="customer_id")
	private Long customerId;
	//Temporarily store hte quote id
	@Column(name="quote_id")
	private Long quoteId;
	@Column(name="total")
	private int total;
//	@Column(name="status")
	@Transient
//	@Enumerated
	private OrderStatus status;
	@Transient
	private String message;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<LineItem> items;
	@Column(name = "created_at")
	private Timestamp createdAt;

	@PrePersist
	void onCreate() {
		this.setCreatedAt(new Timestamp(new Date().getTime()));
	}
	
	public Order () {
		this.status = OrderStatus.CREATED;
	}
	public Order (Long customerId) {
		this();
		this.customerId = customerId;
	}
	
	public Order incorporate (OrderEvent orderEvent) {
		if (status == null)
			status = OrderStatus.CREATED;
		
		switch (status) {
			case CREATED:
				if (orderEvent.getOrderEventType() == OrderEventType.PURCHASED) {
					status = OrderStatus.PENDING;
				}
				break;
			case PENDING:
				if (orderEvent.getOrderEventType() == OrderEventType.PAYMENT_VERIFIED) {
					status = OrderStatus.ACTIVE;
				} else if (orderEvent.getOrderEventType() == OrderEventType.PAYMENT_DECLINED)
					status = OrderStatus.CANCELLED;
					message = orderEvent.getData();
				break;
			case ACTIVE:
				if (orderEvent.getOrderEventType() == OrderEventType.COMPLETED) {
					status = OrderStatus.COMPLETED;
				} else if (orderEvent.getOrderEventType() == OrderEventType.CANCELLED) {
					status = OrderStatus.CANCELLED;
					message = orderEvent.getData();
				} else if (orderEvent.getOrderEventType() == OrderEventType.UPDATED) {
					status = OrderStatus.ACTIVE;
					resolveQuantityUpdates(orderEvent);
				}
				break;
			case COMPLETED:
				//Nothing more can be done once completed
				break;
			case CANCELLED:
				//Nothing more can be done once cancelled
				break;
			default:
				//Invalid status
				break;
		}
		return this;
	}
	
	/**
	 * Updates items according to the orderEvent payload
	 * 
	 * @param orderEvent
	 */
	private void resolveQuantityUpdates(OrderEvent orderEvent) {
		items.stream().filter(item -> item.getProductId()==orderEvent.getProductId()).findFirst().ifPresent(item -> item.setQty(item.getQty()+orderEvent.getQty()));
	}
}
