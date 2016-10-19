package edu.tp.paw.webapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.StoreItem;

@Controller
public class IndexController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private static final int MOST_SOLD_ITEMS = 12;
	
	@Autowired
	private IStoreItemService storeItemService;
	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		
		logger.trace("showing frontpage");
		
		final ModelAndView modelAndView = new ModelAndView("index");
		
		List<StoreItem> l = storeItemService.getMostSold(MOST_SOLD_ITEMS);
		
		logger.debug("most sold items: {}", l);
		
		modelAndView.addObject("mostSoldItems", l);
		modelAndView.addObject("categories", categoryService.getCategoryTree());
		
		return modelAndView;
	}
	
	@RequestMapping("/403")
	public String forbidden() {
		logger.trace("showing 403");
		return "403";
	}
	
	@RequestMapping("/404")
	public String notFound() {
		logger.trace("showing 404");
		return "404";
	}
	
	@RequestMapping("/500")
	public String serverError() {
		logger.warn("showing 500");
		return "500";
	}

}
