package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gazorpazorp.model.Order;

@Repository
@Scope("prototype")
public interface OrderRepository extends JpaRepository<Order, Long> {
//	public List<Order> findByCustomerIdAndStatusInOrderByCreatedAtDesc(@Param("customerId") Long customerId, @Param("status") List<OrderStatus> terminatingStatuses);
	public List<Order> findByCustomerIdOrderByCreatedAtDesc(@Param("customerId") Long customerId);
	
//	@Query("select o from Order o where o.customerId = ?1 and status != 'complete'")
//	public Order findByCustomerIdAndStatusNotIn(@Param("customerId") Long customerId, @Param("status") List<OrderStatus> terminatingStatuses);
	public Order findByCustomerId(@Param("customerId") Long customerId);
	
}
