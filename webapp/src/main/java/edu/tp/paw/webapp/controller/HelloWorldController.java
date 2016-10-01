package edu.tp.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;


@Controller
public class HelloWorldController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping("/helloWorld")
	public ModelAndView helloWorld() {
		final ModelAndView modelAndView = new ModelAndView("index");
		final List<User> sampleList = new ArrayList<>();
		modelAndView.addObject("user", userService.findById(1));
		sampleList.add(userService.findById(1));
		sampleList.add(userService.findById(1));
		sampleList.add(userService.findById(1));
		
		modelAndView.addObject("sampleList", sampleList);
		
		return modelAndView;
	}
	
	@RequestMapping("/helloWorld/user/{userId}")
	public ModelAndView helloWorld(@PathVariable("userId") final int id) { // @RequestParam(value = "userId", required = true, defaultValue = "123")
		final ModelAndView modelAndView = new ModelAndView("index");
		final List<User> sampleList = new ArrayList<>();
		modelAndView.addObject("user", userService.findById(id));
		sampleList.add(userService.findById(id));
		sampleList.add(userService.findById(id));
		sampleList.add(userService.findById(id));
		
		modelAndView.addObject("sampleList", sampleList);
		
		return modelAndView;
	}
	
	@RequestMapping("/helloWorld/create")
	public ModelAndView helloWorld(@RequestParam(value = "name", required = true) final String username) {
		final User user = userService.create(username, "pass");
		
		return new ModelAndView("redirect:/user/" + user.getId());
	}

}
