package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.StoreItem;


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
		
		return modelAndView;
	}
	
	@RequestMapping("/item/{itemId}")
	public ModelAndView individualItem(@PathVariable("itemId") final int id) { // @RequestParam(value = "userId", required = true, defaultValue = "123")
		final ModelAndView modelAndView = new ModelAndView("product");
//		final List<User> sampleList = new ArrayList<>();
		modelAndView.addObject("storeItem", storeItemService.fetchById(id));
//		sampleList.add(userService.findById(id));
//		sampleList.add(userService.findById(id));
//		sampleList.add(userService.findById(id));
		
//		modelAndView.addObject("sampleList", sampleList);
		
		return modelAndView;
	}
	
	// TODO: create route for store items
	@RequestMapping(path = "/item/sell", method = RequestMethod.POST)
	public ModelAndView createItem(@ModelAttribute StoreItem storeItem) {
		
		final StoreItem _storeItem = storeItemService.sell(storeItem.getName(), storeItem.getDescription(), storeItem.getPrice());
		final ModelAndView modelAndView = new ModelAndView("sell2");
		
		modelAndView.addObject("storeItem", _storeItem);
		
		return modelAndView;
	}

}
