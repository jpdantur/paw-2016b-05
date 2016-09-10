package edu.tp.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.UserService;
import edu.tp.paw.model.User;


@Controller
public class StoreItemController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/items")
	public ModelAndView itemBrowser() {
		
		final ModelAndView modelAndView = new ModelAndView("index");
		
		return modelAndView;
	}
	
	@RequestMapping("/items?page={pageNumber}&q={query}")
	public ModelAndView itemBrowser(@RequestParam(value = "pageNumber", defaultValue = "0") final int pageNumber, @RequestParam("query") final String query) {
		
		final ModelAndView modelAndView = new ModelAndView("index");
		
		return modelAndView;
	}
	
	@RequestMapping("/item/{itemId}")
	public ModelAndView individualItem(@PathVariable("userId") final int id) { // @RequestParam(value = "userId", required = true, defaultValue = "123")
		final ModelAndView modelAndView = new ModelAndView("index");
		final List<User> sampleList = new ArrayList<>();
		modelAndView.addObject("user", userService.findById(id));
		sampleList.add(userService.findById(id));
		sampleList.add(userService.findById(id));
		sampleList.add(userService.findById(id));
		
		modelAndView.addObject("sampleList", sampleList);
		
		return modelAndView;
	}
	
	// TODO: create route for store items
//	@RequestMapping(path = "/item/create", method = RequestMethod.POST)
//	public ModelAndView createItem(@RequestBody(required = true) final String name, @RequestBody()) {
//		final User user = userService.create(username);
//		
//		return new ModelAndView("redirect:/user/" + user.getId());
//	}

}
