package edu.tp.paw.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.webapp.auth.SiglasUserDetailsService;
import edu.tp.paw.webapp.form.RegisterForm;
import edu.tp.paw.webapp.form.validator.RegisterFormValidator;

@Controller
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired private IUserService userService;
	@Autowired private RegisterFormValidator validator;
	@Autowired private SiglasUserDetailsService userDetailsService;
	@Autowired private AuthenticationProvider authenticationProvider;
	
	@RequestMapping("/login")
	public String login(
			@RequestParam( value = "r", required = false) boolean hasRegistered,
			@RequestParam( value = "error", required = false) String error,
			@RequestParam( value = "logout", required = false ) String logout,
			@RequestParam( value = "next", required = false ) String next,
			Model model) {
		
		if (logout != null) {
			// user just logout
		}
		
		if (error != null) {
			model.addAttribute("error", "user or password incorrect");
		}
		
		logger.debug("received error: {}", error);
		
		model.addAttribute("hasRegistered", hasRegistered);
		model.addAttribute("next", next);
		
		return "login";
	}
	
	@RequestMapping( value = "/register", method = RequestMethod.GET)
	public String registerUser(
			@ModelAttribute("registerForm") final RegisterForm form,
			final BindingResult result,
			final Model model
			) {
		model.addAttribute("result", result);
		model.addAttribute("user", form);
		
		return "register";
	}
	
	@RequestMapping( value = "/register", method = RequestMethod.POST)
	public String register(
			@Valid @ModelAttribute("registerForm") final RegisterForm form,
			final BindingResult result,
			final Model model) {
		
		validator.validate(form, result);
		
		if (!result.hasErrors()) {
			
			final UserBuilder userBuiler = new UserBuilder(
					form.getUsername()
				)
				.firstName(form.getFirstName())
				.lastName(form.getLastName())
				.email(form.getEmail())
				.password(form.getPassword());
			
			final User u = userService.registerUser(userBuiler);
			
			authenticateUserAndSetSession(u, form.getPassword());
			
			return "redirect:/";
			
		}
		
		model.addAttribute("result", result);
		model.addAttribute("user", form);
		
		return "register";
		
	}
	
	private void authenticateUserAndSetSession(final User user, final String password) {
		
		logger.trace("user pass is: {}", user.getPassword());
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		final Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		
		authenticationProvider.authenticate(auth);
		
		if (auth.isAuthenticated()) {
		    SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}
	
}
