package com.gazorpazorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gazorpazorp.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	public void deleteByUserId(@Param("userId") Long userId);
	Customer findByUserId(@Param("userId") Long userId);
}
