package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;

@Controller
public class IndexController extends BaseController {
	
	private static final int MOST_SOLD_ITEMS = 6;
	
	@Autowired
	private IStoreItemService storeItemService;
	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		
		final ModelAndView modelAndView = new ModelAndView("index");
		
		modelAndView.addObject("mostSoldItems", storeItemService.getMostSold(MOST_SOLD_ITEMS));
		modelAndView.addObject("categories", categoryService.getCategoryTree());
		
		return modelAndView;
	}
	
	@RequestMapping("/403")
	public String forbidden() {
		return "403";
	}
	
	@RequestMapping("/404")
	public String notFound() {
		return "404";
	}

}
