package edu.tp.paw.webapp.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.User;
import edu.tp.paw.webapp.form.ChangePasswordForm;
import edu.tp.paw.webapp.form.ProfileForm;
import edu.tp.paw.webapp.form.validator.ChangePasswordFormValidator;
import edu.tp.paw.webapp.form.validator.ProfileFormValidator;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private IStoreItemService itemService;
	
	@Autowired
	private ProfileFormValidator validator;
	
	@Autowired
	private ChangePasswordFormValidator passwordValidator;
	
	@RequestMapping( value = "/details", method = RequestMethod.GET)
	public String profile(
			@ModelAttribute("profileForm") final ProfileForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		
		model.addAttribute("items", itemService.getUserItems(user));
		model.addAttribute("result", result);
		
		return "profile";
	}
	
	@RequestMapping( value = "/details", method = RequestMethod.POST )
	public String profileSubmit(
			@Valid @ModelAttribute("profileForm") final ProfileForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		validator.validate(form, result);
		
		model.addAttribute("items", itemService.getUserItems(user));
		model.addAttribute("success", !result.hasErrors());
		model.addAttribute("result", result);
		
		return "profile";
	}
	
	@RequestMapping( value = "/password", method = RequestMethod.GET )
	public String password() {
		return "redirect:/profile/details#password";
	}
	
	@RequestMapping( value = "/items", method = RequestMethod.GET )
	public String items() {
		return "redirect:/profile/details#items";
	}
	
	@RequestMapping( value = "/password", method = RequestMethod.POST )
	public String changePassword(
			@Valid @ModelAttribute("changePassword") final ChangePasswordForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		logger.debug("form: {}", form);
		
		passwordValidator.validate(form, result);
		
		model.addAttribute("items", itemService.getUserItems(user));
		model.addAttribute("passSuccess", !result.hasErrors());
		model.addAttribute("result", result);
		
		return "profile";
	}
	
}
