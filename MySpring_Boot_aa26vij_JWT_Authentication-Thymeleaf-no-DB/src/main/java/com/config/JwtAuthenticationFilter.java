package com.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.helper.JwtUtil;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// get jwt
		//check if it starting from Bearer
		//validate
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
	
	System.out.println("================================================================================================================================");
	System.out.println("requestTokenHeader: " + requestTokenHeader);
	System.out.println("================================================================================================================================");

	
	String username = null;
	String jwtToken = null;
	if(requestTokenHeader!= null && requestTokenHeader.startsWith("Bearer "))
	{
		jwtToken = requestTokenHeader.substring(7); // it will remove Bearer from
		
		System.out.println("===================================================================");
		System.out.println("jwtToken: " + jwtToken);
		System.out.println("===================================================================");
		
		try {
			username = jwtUtil.extractUsername(jwtToken);
			System.out.println("username: " + username);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
		 //security
		if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
 
			 System.out.println("name: "+ userDetails.getUsername());
			 System.out.println("password: "+ userDetails.getPassword());
			 
			 if(jwtUtil.validateToken(jwtToken, userDetails))
			 {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =	new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			
			}
			else {
				System.out.println("Token is not validate");
			}
		
		
	}
	filterChain.doFilter(request, response);
		
		
	}

}
