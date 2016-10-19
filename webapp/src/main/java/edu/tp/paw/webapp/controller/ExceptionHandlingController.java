package edu.tp.paw.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.webapp.exceptions.CategoryNotFoundException;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;

@ControllerAdvice
public class ExceptionHandlingController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);
	
	@ExceptionHandler(StoreItemNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
	public ModelAndView noSuchItem() {
		
		ModelAndView mav = new ModelAndView("404");
		
		mav.addObject("locale", currentLocale());
		mav.addObject("messageSource", messageSource());
		
		return mav;
		
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
	public ModelAndView noSuchCategory() {
		
		ModelAndView mav = new ModelAndView("404");
		
		mav.addObject("locale", currentLocale());
		mav.addObject("messageSource", messageSource());
		
		return mav;
		
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
	public ModelAndView exception(final HttpServletRequest req, final Exception ex) {
		
		logger.error("Request: " + req.getRequestURL() + " failed", ex);
		
		ModelAndView mav = new ModelAndView("500");
		
		mav.addObject("locale", currentLocale());
		mav.addObject("messageSource", messageSource());
		
		return mav;
		
	}
	
}
