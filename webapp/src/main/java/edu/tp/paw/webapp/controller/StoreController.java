package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.StoreItemService;
import edu.tp.paw.model.StoreItem;


@Controller
public class StoreController {

	@Autowired
	private StoreItemService userService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		
		final ModelAndView modelAndView = new ModelAndView("index");
		
		return modelAndView;
	}
	
	@RequestMapping("/sell")
	public String sell(Model model) {
		
//		model.addAttribute("storeItem", new StoreItem());
		
//		final ModelAndView modelAndView = new ModelAndView("sell");
		
		return "sell";
		
//		return modelAndView;
	}

}
