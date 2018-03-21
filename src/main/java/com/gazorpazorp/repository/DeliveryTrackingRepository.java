package com.gazorpazorp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.DeliveryTracking;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, UUID>{

	Optional<DeliveryTracking> findByDeliveryId(@Param("deliveryId") Long deliveryId);
}
