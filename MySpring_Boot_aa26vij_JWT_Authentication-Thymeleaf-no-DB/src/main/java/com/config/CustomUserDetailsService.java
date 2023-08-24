package com.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.User;
import com.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userServ;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
	User user =	this.userServ.getByUsername(userName);
	

	if(user == null)
	{
		throw new UsernameNotFoundException("User not Found !!");
	}
	else {
		return new CustomUserDetails(user);
	}
	
	}
	
	

}
