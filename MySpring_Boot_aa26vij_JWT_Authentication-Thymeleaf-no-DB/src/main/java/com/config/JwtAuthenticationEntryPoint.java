package com.config;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.exceptions.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
	{
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
	
		//	response.sendError(401,"Unauthorized");
			
			ExceptionMessage e = new ExceptionMessage("401,Unauthorised",  false);
			
			OutputStream out = response.getOutputStream();
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, e);
			out.flush();
	}

}
