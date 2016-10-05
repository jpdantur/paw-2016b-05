package edu.tp.paw.webapp.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController {
	
	final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@ModelAttribute("locale")
	public Locale currentLocale() {
		return LocaleContextHolder.getLocale();
	}
	
	@ModelAttribute("messageSource")
	public MessageSource messageSource() {
		return messageSource;
	}
	
}
