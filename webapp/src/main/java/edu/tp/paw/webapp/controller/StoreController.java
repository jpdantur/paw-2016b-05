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
import edu.tp.paw.interfaces.service.StoreItemService;
import edu.tp.paw.model.User;


@Controller
public class StoreController {

	@Autowired
	private StoreItemService userService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		
		final ModelAndView modelAndView = new ModelAndView("index");
		
		return modelAndView;
	}

}
