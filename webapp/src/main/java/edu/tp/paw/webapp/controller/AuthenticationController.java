package edu.tp.paw.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.webapp.form.LoginForm;
import edu.tp.paw.webapp.form.RegisterForm;

@Controller
public class AuthenticationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	IUserService userService;
	
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
			@ModelAttribute("registerForm") RegisterForm form,
			BindingResult result,
			Model model
			) {
		model.addAttribute("result", result);
		model.addAttribute("user", form);
		
		return "register";
	}
	
	@RequestMapping( value = "/register", method = RequestMethod.POST)
	public String register(
			@Valid @ModelAttribute("registerForm") RegisterForm form,
			BindingResult result,
			Model model) {
		
		if (!result.hasErrors()) {
			
			System.out.println(form);
			
			final UserBuilder userBuiler = new UserBuilder(
					form.getFirstName(),
					form.getLastName(),
					form.getUsername(),
					form.getEmail()
				).password(form.getPassword());
			
			userService.create(userBuiler);
			
			return "redirect:/login?r=1";
			
		}
		
		model.addAttribute("result", result);
		model.addAttribute("user", form);
		
		return "register";
		
	}
	
}
