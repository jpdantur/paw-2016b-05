package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IUserService userService;
	
	
	@RequestMapping({"/dashboard"})
	public String index(final Model model) {
		
		model.addAttribute("numberOfItems", storeItemService.getNumberOfItems());
		model.addAttribute("numberOfCategories", categoryService.getNumberOfCategories());
		model.addAttribute("numberOfUsers", userService.getNumberOfUsers());
		
		
		return "admin";
	}
	
	@RequestMapping({"/categories"})
	public String categories(final Model model) {
		
		model.addAttribute("categories", categoryService.getCategoryTree());
		
		return "admin_categories";
	}

}
