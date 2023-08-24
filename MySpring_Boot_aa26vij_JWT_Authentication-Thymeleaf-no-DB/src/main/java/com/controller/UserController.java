package com.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exceptions.ResourceNotFoundException;
import com.model.User;
import com.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userRepo;
	
	@GetMapping("/")
	public String welcome(Model m)	
	{
			String text = "\n This is private page. "
			     + "This page is not allowed to unauthenticated users. "
					+ "This page is only allowed to Users login with token.";
			m.addAttribute("msg",text);
			
			
			return "user/dashboard";
	}
	
	@GetMapping("/getuser")
	public String getUser(Principal p,Model m,HttpServletRequest request)
	{
		User user = userRepo.getByUsername(p.getName());
		
		m.addAttribute("user",user);
		
		
		String t = null;
		if(request.getCookies()!=null)
		{
			Cookie[]rc =request.getCookies();
			
			for(int i=0;i<rc.length;i++)
			{
				if(rc[i].getName().equals("token")==true)
				{
					
				t = rc[i].getValue().toString();
				}
			}
		}
		
		
	String requestTokenHeader = "Bearer "+t;
	m.addAttribute("token",requestTokenHeader);
		
		return "user/getuser";
	}
	
}
