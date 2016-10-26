package edu.tp.paw.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.tp.paw.model.User;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.StoreItem;

@Controller
public class BaseController {
	
	final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired private ApplicationContext appContext;
	@Autowired private MessageSource messageSource;
	@Autowired private IUserService userService;
	
	
	
	@ModelAttribute("locale")
	public Locale currentLocale() {
		final Locale locale = LocaleContextHolder.getLocale();
		logger.trace("request has locale: {}", locale);
		return locale;
	}
	
	@ModelAttribute("messageSource")
	public MessageSource messageSource() {
		return messageSource;
	}
	
	@ModelAttribute("loggedUser")
	public User user() {
		
		logger.trace("fetching logged user");
		
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			
			logger.trace("user is not authenticated");
			
			return null;
		}
		
		logger.trace("user is authenticated");
		
		Object principal = auth.getPrincipal();
		
		if (principal instanceof String) {
			return userService.findByUsername((String)principal);
		} else if (principal instanceof UserDetails) {
			return userService.findByUsername(((UserDetails)principal).getUsername());
		} else {
			return userService.findByUsername(principal.toString());
		}
	}
	
//	@ModelAttribute("userFavourites")
//	public Set<StoreItem> favourites(
//			@ModelAttribute("loggedUser") final User user
//			) {
//		if (user == null) {
//			return null;
//		}
//		return userService.getFavourites(user);
//	}
	
	@ModelAttribute("currentURI")
	public String currentUrl(HttpServletRequest request) {
		
		return String.format("%s?%s", request.getRequestURI().replace(appContext.getApplicationName(), ""), Optional.ofNullable(request.getQueryString()).orElse(""));
	}
	
	@ModelAttribute("dateFormatter")
	public SimpleDateFormat dateFormatter(
			@ModelAttribute("locale") final Locale locale
			) {
		return new SimpleDateFormat("dd-MM-yyyy", locale);
	}
	
}
