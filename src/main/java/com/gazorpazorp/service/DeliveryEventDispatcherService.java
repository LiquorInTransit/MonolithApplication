//package com.gazorpazorp.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.gazorpazorp.model.Delivery;
//import com.gazorpazorp.model.DeliveryEvent;
//import com.gazorpazorp.repository.DeliveryRepository;
//
//@Service
//public class DeliveryEventDispatcherService {
//	
//	@Autowired
//	DeliveryRepository deliveryRepo;
//	
//	public void dispatch (DeliveryEvent event, Long deliveryId) {
//		Delivery delivery = deliveryRepo.getOne(deliveryId);
//		switch (event.getDeliveryEventType()) {
//		case CREATED:
//			//TODO: Create the delivery tracking			
//			break;
//		case ACCEPTED:
//			
//			break;
//		case HELD:
//			
//			break;
//		case DECLINED:
//			
//			break;
//		case COMPLETED:
//			
//			break;
//		case CANCELLED:
//			
//			break;
//		default:
//			break;
//		}
//	}
//}
