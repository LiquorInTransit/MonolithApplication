package com.gazorpazorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gazorpazorp.model.LineItem;

public interface LineItemRepository  extends JpaRepository<LineItem, Long>{

}
