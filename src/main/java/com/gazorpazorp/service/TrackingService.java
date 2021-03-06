package com.gazorpazorp.service;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gazorpazorp.LITMonolith.config.LITSecurityUtil;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.DeliveryTracking;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.model.TrackingEventType;
import com.gazorpazorp.repository.DeliveryTrackingRepository;
import com.gazorpazorp.repository.TrackingEventRepository;

@Service
public class TrackingService {

	@Autowired
	TrackingEventRepository trackingEventRepo;
	@Autowired
	DeliveryTrackingRepository deliveryTrackingRepo;
	@Autowired
	DeliveryService deliveryService;
	@Autowired
	CustomerService customerService;
	@Autowired
	DriverService driverService;
	@Autowired
	QuoteService quoteService;
	
	@Transactional(readOnly=false)
	public DeliveryTracking getTrackingInfoById(UUID trackingId) throws Exception {
		DeliveryTracking dt = deliveryTrackingRepo.findById(trackingId).orElse(null);
		if (dt == null)
			throw new Exception("No tracking information for trackingId " + trackingId);
			
		if (!verifyCustomer(dt.getDeliveryId()))
			throw new Exception ("You are not authorized to track this delivery");
		
		TrackingEvent latest = dt.getTrackingEvents().stream().findFirst().orElse(null);
		if (latest == null)
			return dt;
		dt.setLocation(latest.getLocation());
		dt.setStatus(latest.getTrackingEventType());
		dt.setTrackingEvents(dt.getTrackingEvents().stream().filter(t->t.getTrackingEventType()!=TrackingEventType.UPDATE_LOCATION).collect(Collectors.toSet()));
		return dt;
	}
	
	public UUID createDeliveryTracking (Long deliveryId) throws Exception {
		DeliveryTracking tracking = deliveryTrackingRepo.findByDeliveryId(deliveryId).orElse(null);
		if (tracking == null) {
			DeliveryTracking dt = new DeliveryTracking();
			dt.setDeliveryId(deliveryId);
			dt = deliveryTrackingRepo.save(dt);			
			
			TrackingEvent ev = new TrackingEvent();
			ev.setLocation(null);
			ev.setTrackingEventType(TrackingEventType.AWAITING_ACCEPTANCE);
			ev.setDeliveryTracking(dt);
			trackingEventRepo.save(ev);
			return /*"www.liquorintransit.party/api/tracking/"+*/dt.getId();
		} else {
			throw new Exception ("Tracking for this delivery has already begun");
		}
	}
	
	public Boolean createEvent(TrackingEvent trackingEvent, UUID trackingId, boolean verify) throws Exception {
		DeliveryTracking dt = deliveryTrackingRepo.findById(trackingId).orElse(null);
		if (dt == null)
			throw new Exception("No tracking information with trackingId " + trackingId);		
		if (verify && !verifyDriver(dt.getDeliveryId())) 
			throw new Exception ("Not authorized to post updates to this delivery");
		if (dt.getTrackingEvents().stream().anyMatch(ev -> ev.getTrackingEventType().equals(TrackingEventType.CANCELLED)||ev.getTrackingEventType().equals(TrackingEventType.DELIVERED))) {
			throw new Exception ("This delivery has completed. No new information accepted.");
		} else {
			trackingEvent.setDeliveryTracking(dt);
			trackingEventRepo.save(trackingEvent);
			if (TrackingEventType.DELIVERED.equals(trackingEvent.getTrackingEventType()))
				deliveryService.completeDelivery(dt.getDeliveryId());
			else if (TrackingEventType.CANCELLED.equals(trackingEvent.getTrackingEventType()))
				deliveryService.cancelDeliveryById(dt.getDeliveryId());
			return true;
		}
	}
	
	//Getting functions
/*	@Transactional(readOnly=false)
	public DeliveryStatusDto aggregateTrackingEvents(Long deliveryId) throws Exception{		
		Flux<TrackingEvent>trackingEvents = Flux.fromStream(trackingEventRepo.findByDeliveryIdOrderByCreatedAtDesc(deliveryId));
		DeliveryStatusDto status = trackingEvents
			//	.takeWhile(trackingEvent -> trackingEvent.getTrackingEventType() != TrackingEventType.DELIVERED)
				.reduceWith(() -> new DeliveryStatusDto(), DeliveryStatusDto::incorporate)
				.get();
		
		TrackingEvent ev = trackingEventRepo.findTopByDeliveryIdOrderByCreatedAtDesc(deliveryId);
		if (ev != null)
			status.setLocation(ev.getLocation());
		else 
			status.setLocation(null);
		return status;
	}*/  
	
	public boolean verifyCustomer(Long deliveryId) throws Exception{
		Long customerId = getCustomerId();
		Delivery delivery = getDelivery(deliveryId);
		
		if (delivery==null || customerId==null)
			throw new Exception("Delivery or customer does not exist");
		
		return quoteService.getQuoteById(delivery.getQuoteId()).getCustomerId().equals(customerId);
	}
	public boolean verifyDriver(Long deliveryId) throws Exception {
		Long driverId = getDriverId();
		Delivery delivery = getDelivery(deliveryId);
		
		if (delivery == null || driverId == null) 
			throw new Exception("Delivery or driver does not exist");
		
		return delivery.getDriverId() == driverId;		
	}
	private Long getCustomerId() {
		return LITSecurityUtil.currentUser().getCustomerId();//customerService.getCurrentCustomer();
	}
	private Long getDriverId() {
		return LITSecurityUtil.currentUser().getDriverId();//driverService.getCurrentDriver();
	}
	private Delivery getDelivery(Long deliveryId) throws Exception {
		Delivery delivery = deliveryService.getDeliveryById(deliveryId, false);
		if (delivery == null) 
			throw new Exception ("Delivery you are tracking does not exist");
		return delivery;
	}
}
