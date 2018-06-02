//package com.gazorpazorp.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//
//import com.gazorpazorp.model.DeliveryEvent;
//
//public interface DeliveryEventRepository extends JpaRepository<DeliveryEvent, Long> {
//	List<DeliveryEvent> findByDeliveryIdOrderByCreatedAtAsc(@Param("deliveryId") Long deliveryId);
//}
