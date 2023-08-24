package com.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.model.User;

@Service
public class UserService  {

	
	private static List<User> list = new ArrayList<>();
	
	static {
		list.add(new User(1,"admin","admin","ROLE_ADMIN",true));
		list.add(new User(2,"swapnil","1234","ROLE_NORMAL",true));
	}
	
	public User getByUsername(String userName)
	{
		User user = null;
	
		user = list.stream().filter(e ->e.getUsername().equals(userName)).findFirst().get();
	// user = 	new User(1,"admin","admin","ROLE_ADMIN",true);
		return user;
	}
	 
	
	
}
