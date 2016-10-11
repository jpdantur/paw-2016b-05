package edu.tp.paw.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.webapp.form.CategoryForm;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IUserService userService;
	
	
	@RequestMapping("/dashboard")
	public String index(final Model model) {
		
		model.addAttribute("numberOfItems", storeItemService.getNumberOfItems());
		model.addAttribute("numberOfCategories", categoryService.getNumberOfCategories());
		model.addAttribute("numberOfUsers", userService.getNumberOfUsers());
		
		
		return "admin";
	}
	
	@RequestMapping("/categories")
	public String categories(final Model model) {
		
		model.addAttribute("categories", categoryService.getCategoryTree());
		
		return "admin_categories";
	}
	
	@RequestMapping( value = "/categories/create", method = RequestMethod.GET)
	public String createCategory(
			@ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		model.addAttribute("category", form);
		model.addAttribute("result", result);
		
		return "create_category";
	}
	
	@RequestMapping( value = "/categories/create", method = RequestMethod.POST)
	public String createCategorySubmit(
			@Valid @ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		logger.debug("form is {}", form);
		
		if (!result.hasErrors()) {
		
			final CategoryBuilder builder = new CategoryBuilder(form.getName(), form.getParent());
			
			final Category category = categoryService.create(builder);
			
			if (category == null) {
				//TODO something wrong
			}
			
			model.addAttribute("success", true);	
			return "create_category";
		}
		
		//TODO check if parent exists
		
		model.addAttribute("category", form);
		model.addAttribute("result", result);
		
		return "create_category";
	}
	
	@RequestMapping( value = "/categories/{categoryId}/edit", method = RequestMethod.GET)
	public String editCategory(
			@PathVariable("categoryId") final long id,
			@ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		final Category category = categoryService.findById(id);
		
		if (category == null) {
			//TODO problem here
		}
		
		model.addAttribute("category", category);
		model.addAttribute("result", result);
		model.addAttribute("update", true);
		
		return "edit_category";
	}
	
	@RequestMapping( value = "/categories/{categoryId}/edit", method = RequestMethod.POST)
	public String editCategorySubmit(
			@PathVariable("categoryId") final long id,
			@Valid @ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		logger.debug("form is {}", form);
		
		if (!result.hasErrors()) {
		
			final Category category = new CategoryBuilder(form.getName(), form.getParent()).id(id).build();
			
			if (categoryService.updateCategory(category)) {
				model.addAttribute("success", true);	
				return "edit_category";
			}
			
			
		}
		
		//TODO check if parent exists
		
		model.addAttribute("category", form);
		model.addAttribute("result", result);
		
		return "edit_category";
	}

}
