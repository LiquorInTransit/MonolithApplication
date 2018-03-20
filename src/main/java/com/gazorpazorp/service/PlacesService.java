package com.gazorpazorp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Location;
import com.gazorpazorp.model.Place;
import com.gazorpazorp.repository.PlaceRepository;

@Service
public class PlacesService {

	@Autowired
	PlaceRepository placeRepo;
	
	@Autowired
	CustomerService customerService;
	
	//TODO: Consider making this return the new list of places
	public Boolean createNewPlace(String name, Location location) throws Exception {
		//Get the customer ID
		Long customerId = getCustomerId();
		//Verify the place doesn't already exist for the customer
		Place place = placeRepo.findByNameAndCustomerId(name, customerId).orElse(null);
		//create the place
		if (place == null) {
			if (!verifyLocation(location))
				throw new Exception("The location entered is not valid");
			place = new Place(name, location, customerId);
			placeRepo.save(place);
			return true;
		}
		return null;
	}
	
	public List<Place> getAllPlaces() throws Exception {
		Long customerId = getCustomerId();
		List<Place> places =  placeRepo.findByCustomerId(customerId);
		if (places.isEmpty())
			return null;
		return places;
	}

	public Boolean updatePlace(String name, Location location) throws Exception {
		Long customerId = getCustomerId();
		Place place = placeRepo.findByNameAndCustomerId(name, customerId).orElse(null);
		if (place == null)
			return null;
		if (!verifyLocation(location))
			throw new Exception("The location entered is not valid");
		place.setLocation(location);
		placeRepo.save(place);
		return true;
	}	
	
	public Boolean deletePlace (String name) throws Exception {
		Long customerId = getCustomerId();
		Place place = placeRepo.findByNameAndCustomerId(name, customerId).orElse(null);
		if (place == null)
			return null;
		placeRepo.delete(place);
		return true;
	}
	
	
	
	private Long getCustomerId() throws Exception {
		Customer customer = customerService.getCurrentCustomer();
		if (customer == null)
			throw new Exception("Customer does not exist.");
		return customer.getId();
	}	
	//These locations must have SOMETHING in the address and city.
	private boolean verifyLocation(Location location) {
		if (location == null)
			return false;
		if (location.getAddress() == null || location.getAddress().isEmpty())
			return false;
		if (location.getCity() == null || location.getCity().isEmpty())
			return false;
		return true;
	}
}
