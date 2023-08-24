package com.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.config.CustomUserDetailsService;
import com.helper.JwtUtil;
import com.model.JwtRequest;


@Controller
public class JwtController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/*
	  In Postman App
	  Body->raw JSON
	  	
	  	{
	  		"username":"admin",
	  		"password":"admin"
	  	}
	 */	
	@PostMapping("/token") // when trying this url,select auth type: No Auth
	public String generateToken(Model m,HttpSession session,
			@ModelAttribute JwtRequest jwtRequest, HttpServletResponse res) throws Exception
	{
		System.out.println(jwtRequest);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (UsernameNotFoundException e) {
			
			session.setAttribute("msg","Bad Credentials");
			return "redirect:/login";
		
		} catch(BadCredentialsException e)
		{
			session.setAttribute("msg","Bad Credentials");
			return "redirect:/login";
		}
		
		// fine area..
		
		try {
		
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		
		
		System.out.println("userDetails.getUsername: "   +userDetails.getUsername());
		
	
	final String token =	jwtUtil.generateToken(userDetails);
	
	
	Cookie cookie = new Cookie("token",token);
	cookie.setMaxAge(Integer.MAX_VALUE);
	res.addCookie(cookie);
	
	
	System.out.println("token: " + token);
	
	
	
	return "redirect:/user/";
		}catch(Exception e)
		{
			session.setAttribute("msg","Credentials were right But something went wrong!!");
			return "redirect:/login";
		}
	}
	
	
	 @GetMapping("/log_out")
	    public String logout(HttpServletRequest request,HttpServletResponse res,Model m,HttpSession session) {
	       
		 
		 String msg = null;

		 Cookie[] cookies2 = request.getCookies();
		 for(int i = 0; i < cookies2.length; i++) 
		 {
		 	if (cookies2[i].getName().equals("token")) 
		 	{
		     cookies2[i].setMaxAge(0);
		     res.addCookie(cookies2[i]);
		 	msg = "Logout successfully";

		  }
	       
	    }
		 session.setAttribute("msg", msg);
		 

	        return "redirect:/login";

	 }
	 
}	 
