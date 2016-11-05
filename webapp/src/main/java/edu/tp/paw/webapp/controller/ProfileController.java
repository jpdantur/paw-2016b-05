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

import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.webapp.form.ChangePasswordForm;
import edu.tp.paw.webapp.form.ProfileForm;
import edu.tp.paw.webapp.form.validator.ChangePasswordFormValidator;
import edu.tp.paw.webapp.form.validator.ProfileFormValidator;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired private IStoreItemService itemService;
	@Autowired private IUserService userService;
	
	@Autowired private ProfileFormValidator validator;
	@Autowired private ChangePasswordFormValidator passwordValidator;
	
	@RequestMapping( value = "/details", method = RequestMethod.GET)
	public String profile(
			@ModelAttribute("profileForm") final ProfileForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		model.addAttribute("result", result);
		model.addAttribute("show", "account");
		
		return "profile";
	}
	
	@RequestMapping( value = "/details", method = RequestMethod.POST )
	public String profileSubmit(
			@Valid @ModelAttribute("profileForm") final ProfileForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		validator.validate(form, result);
		
		if (!result.hasErrors()) {
			
			final User modifiedUser = new UserBuilder(user).email(form.getEmail()).build();
			
			logger.debug("modifying user from {} to {}", user, modifiedUser);
			
			if (userService.updateUser(modifiedUser)) {
				
				model.addAttribute("loggedUser", modifiedUser);
				model.addAttribute("success", true);
				
			} else {
				model.addAttribute("success", false);
			}
			
		}
		
		model.addAttribute("result", result);
		
		return "profile";
	}
	
	@RequestMapping( value = "/password", method = RequestMethod.GET )
	public String password(
			@ModelAttribute("changePassword") final ChangePasswordForm form,
			final BindingResult result,
			final Model model
			) {
		
		model.addAttribute("show", "password");
		model.addAttribute("result", result);
		
		return "profile";
	}
	
	@RequestMapping( value = "/password", method = RequestMethod.POST )
	public String changePassword(
			@Valid @ModelAttribute("changePassword") final ChangePasswordForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		logger.debug("form: {}", form);
		
		passwordValidator.validate(form, result);
		
		if (!result.hasErrors()) {
			
			if (userService.changePassword(user, form.getPassword())) {
				
				model.addAttribute("success", true);
				
			} else {
				model.addAttribute("success", false);
			}
			
		}
		
		model.addAttribute("passSuccess", !result.hasErrors());
		model.addAttribute("result", result);
		
		return "profile";
	}
	
	@RequestMapping( value = "/items", method = RequestMethod.GET )
	public String items(final Model model, @ModelAttribute("loggedUser") final User user) {
		return "redirect:/profile/items/active";
	}
	
	@RequestMapping( value = "/items/active", method = RequestMethod.GET )
	public String activeItems(final Model model, @ModelAttribute("loggedUser") final User user) {
		
		model.addAttribute("items", userService.getActivePublishedItems(user));
		model.addAttribute("status", StoreItemStatus.ACTIVE);
		model.addAttribute("show", "items");
		
		return "profile";
	}
	
	@RequestMapping( value = "/items/paused", method = RequestMethod.GET )
	public String pausedItems(final Model model, @ModelAttribute("loggedUser") final User user) {
		
		model.addAttribute("items", userService.getPausedPublishedItems(user));
		model.addAttribute("status", StoreItemStatus.PAUSED);
		model.addAttribute("show", "items");
		
		return "profile";
	}
	
	@RequestMapping( value = "/sales", method = RequestMethod.GET )
	public String sales(final Model model, @ModelAttribute("loggedUser") final User user) {
		
		model.addAttribute("transactions", userService.getGroupedTransactions(user));
		model.addAttribute("show", "sales");
		
		return "profile";
	}
	
	@RequestMapping( value = "/purchases", method = RequestMethod.GET )
	public String purchases(final Model model, @ModelAttribute("loggedUser") final User user) {
		
		model.addAttribute("purchases", userService.getGroupedPurchases(user));
		model.addAttribute("show", "purchases");
		
		return "profile";
	}
	
}
