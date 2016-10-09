package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tp.paw.interfaces.service.IStoreItemService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IStoreItemService storeItemService;
	
	@RequestMapping({"/dashboard"})
	public String index(final Model model) {
		
		model.addAttribute("numberOfItems", storeItemService.getNumberOfItems());
		
		
		return "admin";
	}

}
