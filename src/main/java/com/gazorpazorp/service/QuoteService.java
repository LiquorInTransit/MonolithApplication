package com.gazorpazorp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.model.Dropoff;
import com.gazorpazorp.model.Pickup;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.dto.CustomerDetailsDto;
import com.gazorpazorp.model.dto.StoreDto;
import com.gazorpazorp.repository.QuoteRepository;

@Service
public class QuoteService {
	Logger logger = LoggerFactory.getLogger(QuoteService.class);
	
	private final double FEE = 8.00;
	private double feeMultiplier = 1.00;

	@Autowired
	QuoteRepository quoteRepo;
	@Autowired
	MeService meService;
	@Autowired
	StoreService storeService;
	
	public Quote createQuote () throws Exception {
		Quote quote = new Quote();
		CustomerDetailsDto customer = meService.getCustomer();
		logger.error("These are the coordinates obtained from the customer: " + customer.getLocation().getLatitude()+", "+customer.getLocation().getLongitude());
		StoreDto store = storeService.locateClosestStoreToCoords(customer.getLocation().getLatitude(), customer.getLocation().getLongitude());
		
		Dropoff dropoff = new Dropoff();
		dropoff.setLocation(customer.getLocation());
		dropoff.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
		
		quote.setCustomerId(customer.getId());
		quote.setPickup(new Pickup(store));
		quote.setDropoff(dropoff);
		//TODO: contact order service to find all new orders in last hour. if its higher than 20, change feeMultiplier
		quote.setFee(FEE*feeMultiplier);
		
		//TODO: Contact google maps to get drive times from pickup to delivery
		int dropoffETA = 11;
		quote.setDropoffETA(dropoffETA);
		
		
		return quoteRepo.save(quote);
	}
	
	public Quote getQuoteById(Long id) {
		return quoteRepo.findById(id).orElse(null);
	}
}
