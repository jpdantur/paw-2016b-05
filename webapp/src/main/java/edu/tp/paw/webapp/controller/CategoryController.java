package edu.tp.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;


@Controller
public class CategoryController {

	private final static Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired private ICategoryService categoryService;
	
	/**
	 * Add a new category
	 * @param name New Category name
	 * @param parent New Category Parent
	 * @return
	 */
	@RequestMapping("/category")
	public ModelAndView itemBrowser(
			@RequestParam(value = "name", required = true) final String name,
			@RequestParam(value = "parent", required = true, defaultValue = "0") final long parent) {
		
		logger.debug("controller received request to create category with name = {} parent = {}", name, parent);
		
		final CategoryBuilder builder = new CategoryBuilder(name, parent);
		final Category category = categoryService.create(builder);
		
		final ModelAndView modelAndView = new ModelAndView("dummy");
		
		modelAndView.addObject("message", "Created category " + category.toString());
		
		return modelAndView;
	}

}
