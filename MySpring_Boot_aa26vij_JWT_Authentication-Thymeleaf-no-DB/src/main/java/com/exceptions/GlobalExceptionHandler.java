package com.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)  // these exception is working from created class
	public ResponseEntity<ExceptionMessage> resourceNotFoundExceptionHandler()
	{
		String message = "User not found";
		ExceptionMessage em = new ExceptionMessage(message,false);
		
		System.out.println(em);
		return new ResponseEntity<ExceptionMessage>(em,HttpStatus.NOT_FOUND);
		
	}
	
}
