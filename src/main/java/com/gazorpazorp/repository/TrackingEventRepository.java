package com.gazorpazorp.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.TrackingEvent;


public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {
//	public Stream<TrackingEvent> findByDeliveryIdOrderByCreatedAtDesc(@Param("deliveryId") Long deliveryId);
//	public TrackingEvent findTopByDeliveryIdAndTrackingEventTypeInOrderByCreatedAtDesc(@Param("deliveryId") Long deliveryId, @Param("trackingEventType")List trackingEvents);
//	public TrackingEvent findTopByDeliveryIdOrderByCreatedAtDesc(@Param("deliveryId") Long deliveryId);
//	
//	public List<TrackingEvent> findByDeliveryId(@Param("deliveryId") Long deliveryId);
}
