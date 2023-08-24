package com.config;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.exceptions.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ExceptionMessage e = new ExceptionMessage("Access Denied",  false);
	
		OutputStream out = response.getOutputStream();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, e);
		out.flush();
	
	}
	
	

}
