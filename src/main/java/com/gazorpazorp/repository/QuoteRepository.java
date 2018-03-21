package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.Quote;


public interface QuoteRepository extends JpaRepository<Quote, Long> {
	public List<Quote> findByCustomerId(@Param("customerId") Long customerId);
}
