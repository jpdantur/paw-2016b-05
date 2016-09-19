package edu.tp.paw.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.Category;


@Controller
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	/**
	 * Shows Category tree
	 * @return
	 */
	@RequestMapping("/categories")
	public ModelAndView itemBrowser() {
		
//		System.out.println("controller received request to create category with name = " + name + " parent = " + parent);
		
		List<Category> category = categoryService.getCategoryTree();
		
		final ModelAndView modelAndView = new ModelAndView("dummy");
		
		modelAndView.addObject("category", category);
		
		return modelAndView;
	}
	
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
		
		System.out.println("controller received request to create category with name = " + name + " parent = " + parent);
		
		Category category = categoryService.create(name, parent);
		
		final ModelAndView modelAndView = new ModelAndView("dummy");
		
		modelAndView.addObject("message", "Created category " + category.toString());
		
		return modelAndView;
	}
	
	
	
//	@RequestMapping("/item/{itemId}")
//	public ModelAndView individualItem(
//			@PathVariable("itemId") final int id,
//			@RequestParam(value = "s", defaultValue = "false") final boolean published) {
//		
//		final ModelAndView modelAndView = new ModelAndView("product");
//		
//		modelAndView.addObject("storeItem", storeItemService.fetchById(id));
//		modelAndView.addObject("published", published);
//		
//		return modelAndView;
//	}

}
