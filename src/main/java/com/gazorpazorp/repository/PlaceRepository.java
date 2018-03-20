package com.gazorpazorp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.Place;


public interface PlaceRepository extends JpaRepository<Place, Long> {
	public Optional<Place> findByNameAndCustomerId(@Param("name") String name, @Param("customerId") Long customerId);
	public List<Place> findByCustomerId(@Param("customerId") Long customerId);
}
