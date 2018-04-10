package com.gazorpazorp.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gazorpazorp.model.ShoppingCartEvent;

@Repository
public interface CartEventRepository extends JpaRepository<ShoppingCartEvent, Long>{

	
	//get most recent terminal event, and then get all the cart events created after that.
	//These methods need each other <3
	 ShoppingCartEvent findTopByCustomerIdAndCartEventTypeInOrderByCreatedAtDesc(@Param("customerId") Long customerId, @Param("cartEventType")List cartEvents);
	 Stream<ShoppingCartEvent> findByCustomerIdAndCreatedAtAfterOrderByCreatedAtAsc(@Param("customerId") Long customerId, @Param("createdAt")Timestamp createdAt);
}
