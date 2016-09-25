package edu.tp.paw.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;

@ControllerAdvice
public class ExceptionHandlingController {
	
	
	@ExceptionHandler(StoreItemNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
	public String noSuchItem() {
		
		return "404";
		
	}
	
	
}
