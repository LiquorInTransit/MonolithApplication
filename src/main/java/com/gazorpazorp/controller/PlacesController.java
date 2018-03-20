package com.gazorpazorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.Location;
import com.gazorpazorp.model.dto.PlaceListDto;
import com.gazorpazorp.service.PlacesService;

@RestController
@RequestMapping("/api/places")
public class PlacesController {
	
	@Autowired
	PlacesService placeService;
	
	@PostMapping("/{name}")
	public ResponseEntity createPlace (@PathVariable("name") String name, @RequestBody Location location) throws Exception {
		return Optional.ofNullable(placeService.createNewPlace(name, location))
				.map(p -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Customer already has place with name: " + name + "!"));
	}
	
	@GetMapping
	public ResponseEntity<PlaceListDto> getAll() throws Exception{
		return Optional.ofNullable(placeService.getAllPlaces())
				.map(p -> new ResponseEntity<>(new PlaceListDto(p), HttpStatus.OK))
				.orElseThrow(() -> new Exception("Customer has not saved any places"));
	}
	
	@PutMapping("/{name}")
	public ResponseEntity updatePlace (@PathVariable("name") String name, @RequestBody Location location) throws Exception {
		return Optional.ofNullable(placeService.updatePlace(name, location))
				.map(p -> new ResponseEntity<>(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Place with name: " + name + " does not exist."));
	}

	@DeleteMapping("/{name}")
	public ResponseEntity deletePlace(@PathVariable("name") String name) throws Exception {
		return Optional.ofNullable(placeService.deletePlace(name))
				.map(p -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Place with name: " + name + " does not exist."));
	}
	
}
