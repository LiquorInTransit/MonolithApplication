package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.DeliveryStatus;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
	public Delivery findByOrderId(@Param("orderId") Long orderId);
	public void deleteByOrderId(@Param("orderId") Long orderId);
	
	public List<Delivery> findByDropoffCustomerId(@Param("dropoff.customerId") Long id);
	
	public List<Delivery> findByDriverId(@Param("driverId") Long id);
	
//	@Query("From User u where :role member u.roles")
	@Query("select d from Delivery d where d.driverId is null and d.driverHold is null and ?1 not member d.driverBlacklist and d.status not in ?2 order by d.createdAt asc")
	public List<Delivery> findOpenDeliveries(@Param("driverId") Long driverId, @Param("status")List<DeliveryStatus> terminatingStatuses);
	
	//@Query("select d from Delivery d where d.driverId = ?1 and status != 'complete'")
	public Delivery findByDriverIdAndStatusNotIn(@Param("driverId") Long driverId, @Param("status")List<DeliveryStatus> terminatingStatuses);
}
