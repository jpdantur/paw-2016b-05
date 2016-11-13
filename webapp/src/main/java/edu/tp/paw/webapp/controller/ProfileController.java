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
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;
import edu.tp.paw.webapp.form.ChangePasswordForm;
import edu.tp.paw.webapp.form.ProfileForm;
import edu.tp.paw.webapp.form.ProfileItemSearchForm;
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
	
	@RequestMapping( value = {"/items", "/items/all"}, method = RequestMethod.GET )
	public String allItems(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().status()
					.status(ItemStatusFilter.ANY)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getPublishedItems(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", ItemStatusFilter.ANY);
		model.addAttribute("show", "items");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
		
	}
	
	@RequestMapping( value = "/items/active", method = RequestMethod.GET )
	public String activeItems(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().status()
					.status(ItemStatusFilter.ACTIVE)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getPublishedItems(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", ItemStatusFilter.ACTIVE);
		model.addAttribute("show", "items");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
	@RequestMapping( value = "/items/paused", method = RequestMethod.GET )
	public String pausedItems(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().status()
					.status(ItemStatusFilter.PAUSED)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getPublishedItems(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", ItemStatusFilter.PAUSED);
		model.addAttribute("show", "items");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
	@RequestMapping( value = {"/sales", "/sales/pending"}, method = RequestMethod.GET )
	public String pendingSales(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().purchaseStatus()
					.status(PurchaseStatus.PENDING)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getTransactions(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", PurchaseStatus.PENDING);
		model.addAttribute("show", "sales");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
	@RequestMapping( value = "/sales/approved", method = RequestMethod.GET )
	public String approvedSales(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().purchaseStatus()
					.status(PurchaseStatus.APPROVED)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getTransactions(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", PurchaseStatus.APPROVED);
		model.addAttribute("show", "sales");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
	@RequestMapping( value = "/sales/declined", method = RequestMethod.GET )
	public String declinedSales(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().purchaseStatus()
					.status(PurchaseStatus.DECLINED)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getTransactions(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", PurchaseStatus.DECLINED);
		model.addAttribute("show", "sales");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
	@RequestMapping( value = {"/purchases", "/purchases/pending" }, method = RequestMethod.GET )
	public String pendingPurchases(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		logger.trace("form is: {}", form);
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().purchaseStatus()
					.status(PurchaseStatus.PENDING)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getPurchases(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", PurchaseStatus.PENDING);
		model.addAttribute("show", "purchases");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
	@RequestMapping( value = "/purchases/approved", method = RequestMethod.GET )
	public String approvedPurchases(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().purchaseStatus()
					.status(PurchaseStatus.APPROVED)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getPurchases(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", PurchaseStatus.APPROVED);
		model.addAttribute("show", "purchases");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
	@RequestMapping( value = "/purchases/declined", method = RequestMethod.GET )
	public String declinedPurchases(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().purchaseStatus()
					.status(PurchaseStatus.DECLINED)
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("items", userService.getPurchases(user, filter));
		model.addAttribute("filter", filter);
		model.addAttribute("status", PurchaseStatus.DECLINED);
		model.addAttribute("show", "purchases");
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "profile";
	}
	
//	@RequestMapping( value = "/purchases", method = RequestMethod.GET )
//	public String purchases(final Model model, @ModelAttribute("loggedUser") final User user) {
//		
//		model.addAttribute("purchases", userService.getGroupedPurchases(user));
//		model.addAttribute("show", "purchases");
//		
//		return "profile";
//	}
	
}
