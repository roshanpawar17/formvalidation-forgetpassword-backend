package com.reactspringboot.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.reactspringboot.entities.User;

public interface UserDataRepository extends CrudRepository<User, Integer>{
	public User findById(int id);
	@Query("select u from User u where u.email=:email")
	public User findByEmail(@Param("email") String email);
}
