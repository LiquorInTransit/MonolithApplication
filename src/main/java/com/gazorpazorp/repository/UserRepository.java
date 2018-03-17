package com.gazorpazorp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.User;


public interface UserRepository extends CrudRepository<User, Long>	{
	User findByEmail(@Param("email") String email);
}
