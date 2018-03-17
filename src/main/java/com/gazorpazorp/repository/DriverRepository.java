package com.gazorpazorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gazorpazorp.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>{
	public Driver findByUserId(@Param("userId") Long userId);
	public void deleteByUserId(@Param("userId") Long userId);
}
