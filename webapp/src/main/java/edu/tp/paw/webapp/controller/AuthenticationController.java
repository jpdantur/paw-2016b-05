package edu.tp.paw.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tp.paw.interfaces.service.IPasswordRecoveryService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.webapp.auth.SiglasUserDetailsService;
import edu.tp.paw.webapp.form.PasswordRecoveryForm;
import edu.tp.paw.webapp.form.RegisterForm;
import edu.tp.paw.webapp.form.validator.PasswordRecoveryFormValidator;
import edu.tp.paw.webapp.form.validator.RegisterFormValidator;

@Controller
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired private IUserService userService;
	@Autowired private RegisterFormValidator validator;
	@Autowired private PasswordRecoveryFormValidator passRecoveryValidator;
	@Autowired private SiglasUserDetailsService userDetailsService;
	@Autowired private AuthenticationProvider authenticationProvider;
	
	@Autowired private IPasswordRecoveryService passwordService;
	
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
	
	@RequestMapping( value = "/forgot-pass", method = RequestMethod.GET)
	public String forgotPass(
			@ModelAttribute("registerForm") final RegisterForm form,
			final BindingResult result,
			final Model model
			) {
		model.addAttribute("result", result);
		model.addAttribute("user", form);
		
		return "forgot-pass";
	}
	
	@RequestMapping( value = "/forgot-pass", method = RequestMethod.POST)
	public String recoverPass(
			@Valid @ModelAttribute("passRecoveryForm") final PasswordRecoveryForm form,
			final BindingResult result,
			final Model model) {
		
		passRecoveryValidator.validate(form, result);
		
		if (!result.hasErrors()) {
			
			final User user = userService.findByEmail(form.getEmail());
			
			userService.recoverPassword(user);
			
			model.addAttribute("success", true);
			
		}
		
		model.addAttribute("result", result);
		model.addAttribute("user", form);
		
		return "forgot-pass";
		
	}
	
	@RequestMapping( value = "/reset-pass", method = RequestMethod.GET)
	public String resetPass(
			@RequestParam( value = "token", required = false) final String token,
			@RequestParam( value = "username", required = false) final String username,
			@ModelAttribute("registerForm") final RegisterForm form,
			final BindingResult result,
			final Model model
			) {
		
		final User user = userService.findByUsername(username);
		
		model.addAttribute("tokenValidity", passwordService.checkTokenValidity(user, token));
		model.addAttribute("result", result);
		model.addAttribute("user", form);
		
		
		return "reset-pass";
	}
	
}
