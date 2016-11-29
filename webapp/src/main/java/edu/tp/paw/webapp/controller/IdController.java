package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;
import edu.tp.paw.webapp.form.ProfileItemSearchForm;

@Controller
@RequestMapping("/id")
public class IdController extends BaseController {

	@Autowired IUserService userService;
	
	@RequestMapping( value = {"/{username}", "/{username}/seller"})
	public String userProfileBuyer(
			@ModelAttribute("searchForm") final ProfileItemSearchForm form,
			@PathVariable("username") final String username,
			final Model model
		) {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			throw new StoreItemNotFoundException();
		}
		
		final Filter filter = FilterBuilder
				.create()
				.page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.end().status()
					.status(ItemStatusFilter.ACTIVE)
				.end().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("buyerRating", userService.getBuyerRating(user));
		model.addAttribute("sellerRating", userService.getSellerRating(user));
		
		model.addAttribute("approvedSales", userService.getNumberOfApprovedTransactions(user));
		model.addAttribute("rejectedSales", userService.getNumberOfDeclinedTransactions(user));
		
		model.addAttribute("view", "seller");
		
		model.addAttribute("filter", filter);
		model.addAttribute("items", userService.getPublishedItems(user, filter));
		model.addAttribute("user", user);
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "id";
	}
	
	@RequestMapping( value = "/{username}/buyer")
	public String userProfileSeller(
			@ModelAttribute("searchForm") final ProfileItemSearchForm form,
			@PathVariable("username") final String username,
			final Model model
		) {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			throw new StoreItemNotFoundException();
		}
		
		final Filter filter = FilterBuilder
				.create()
				.page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.end().status()
					.status(ItemStatusFilter.ACTIVE)
				.end().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		model.addAttribute("buyerRating", userService.getBuyerRating(user));
		model.addAttribute("sellerRating", userService.getSellerRating(user));
		
		model.addAttribute("approvedSales", userService.getNumberOfApprovedPurchases(user));
		model.addAttribute("rejectedSales", userService.getNumberOfDeclinedPurchases(user));
		
		model.addAttribute("view", "buyer");
		
		model.addAttribute("filter", filter);
		model.addAttribute("items", userService.getPublishedItems(user, filter));
		model.addAttribute("user", user);
		model.addAttribute("sort", String.format("%s-%s", filter.getOrderFilter().getField().toString(), filter.getOrderFilter().getOrder().toString()));
		
		return "id";
	}
	
}
