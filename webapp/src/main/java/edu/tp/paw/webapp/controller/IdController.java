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
	
	@RequestMapping( value = {"/{username}", "/{username}/buyer"})
	public String userProfileBuyer(
			@ModelAttribute("profileItemSearch") final ProfileItemSearchForm form,
			@PathVariable("username") final String username,
			final Model model
		) {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			throw new StoreItemNotFoundException();
		}
		
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
		model.addAttribute("user", user);
		
		return "id";
	}
	
	@RequestMapping( value = "/{username}/seller")
	public String userProfileSeller(
			@PathVariable("username") final String username,
			final Model model
		) {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			throw new StoreItemNotFoundException();
		}
		
		model.addAttribute("user", user);
		
		return "id";
	}
	
}
