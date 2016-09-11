package edu.tp.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.StoreItemService;
import edu.tp.paw.interfaces.service.UserService;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;


@Controller
public class StoreItemController {

	@Autowired
	private StoreItemService storeItemService;
	
	@RequestMapping("/items")
	public ModelAndView itemBrowser(
			@RequestParam(value = "pageNumber", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "query", required = false) final String query) {
		
		final ModelAndView modelAndView = new ModelAndView("products");
		
		modelAndView.addObject("storeItems", storeItemService.findNMostSold(3));
		
		return modelAndView;
	}
	
	@RequestMapping("/item/{itemId}")
	public ModelAndView individualItem(@PathVariable("itemId") final int id) { // @RequestParam(value = "userId", required = true, defaultValue = "123")
		final ModelAndView modelAndView = new ModelAndView("product");
//		final List<User> sampleList = new ArrayList<>();
		modelAndView.addObject("storeItem", storeItemService.findById(id));
//		sampleList.add(userService.findById(id));
//		sampleList.add(userService.findById(id));
//		sampleList.add(userService.findById(id));
		
//		modelAndView.addObject("sampleList", sampleList);
		
		return modelAndView;
	}
	
	// TODO: create route for store items
	@RequestMapping(path = "/item/create", method = RequestMethod.POST)
	public ModelAndView createItem(@ModelAttribute StoreItem storeItem) {
		final StoreItem _storeItem = storeItemService.create(storeItem.getName(), storeItem.getDescription(), storeItem.getPrice());
		final ModelAndView modelAndView = new ModelAndView("sell2");
//		final List<User> sampleList = new ArrayList<>();
		modelAndView.addObject("storeItem", _storeItem);
		
		return modelAndView;
		
//		System.out.println(_storeItem);
		
		
		
//		return "sell2";
		
//		return new ModelAndView("redirect:/user/" + user.getId());
	}

}
