package com.reactspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reactspringboot.dao.UserDataRepository;
import com.reactspringboot.entities.User;

@Component
public class MyService {
	@Autowired
	private UserDataRepository userDataRepository;
	
	public User addUser(User user) {
		return this.userDataRepository.save(user);
	}

}
