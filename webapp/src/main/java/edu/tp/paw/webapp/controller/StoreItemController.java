package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.IStoreItemService;


@Controller
public class StoreItemController {

	@Autowired
	private IStoreItemService storeItemService;
	
	@RequestMapping("/items")
	public ModelAndView itemBrowser(
			@RequestParam(value = "pageNumber", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "query", required = true) final String query) {
		
		final ModelAndView modelAndView = new ModelAndView("products");
		
		modelAndView.addObject("storeItems", storeItemService.findByTerm(query));
		modelAndView.addObject("query", query);
		modelAndView.addObject("pageNumber", pageNumber);
		
		return modelAndView;
	}
	
	@RequestMapping("/item/{itemId}")
	public ModelAndView individualItem(
			@PathVariable("itemId") final int id,
			@RequestParam(value = "s", defaultValue = "false") final boolean published) {
		
		final ModelAndView modelAndView = new ModelAndView("product");
		
		modelAndView.addObject("storeItem", storeItemService.fetchById(id));
		modelAndView.addObject("published", published);
		
		return modelAndView;
	}

}
