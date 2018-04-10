package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.OrderEvent;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
	List<OrderEvent> findByOrderIdOrderByCreatedAtAsc(@Param("orderId") Long orderId);
}
