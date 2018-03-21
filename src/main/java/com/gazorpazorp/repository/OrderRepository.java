package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.OrderStatus;


public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findByCustomerIdAndStatusInOrderByCreatedAtDesc(@Param("customerId") Long customerId, @Param("status") List<OrderStatus> terminatingStatuses);
	
//	@Query("select o from Order o where o.customerId = ?1 and status != 'complete'")
	public Order findByCustomerIdAndStatusNotIn(@Param("customerId") Long customerId, @Param("status") List<OrderStatus> terminatingStatuses);
}
