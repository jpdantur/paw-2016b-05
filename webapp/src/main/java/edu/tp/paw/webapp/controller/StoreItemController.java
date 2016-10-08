package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;
import edu.tp.paw.webapp.form.SellForm;

@Controller
@RequestMapping("/store/item")
public class StoreItemController extends BaseController {

	@Autowired
	private IStoreItemService itemService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping( value = "/{itemId}/details", method = RequestMethod.GET)
	public String details(
			@PathVariable("itemId") final long id,
			@ModelAttribute("sellForm") final SellForm form,
			final BindingResult result,
			final Model model) {
		
		final StoreItem item = itemService.fetchById(id);
		
		if (item == null) {
			throw new StoreItemNotFoundException();
		}
		
		model.addAttribute("bindingResult", result);
		model.addAttribute("item", item);
		model.addAttribute("categories", categoryService.getCategories());
		
		return "edit";
	}
	
	@RequestMapping( value = "/{itemId}/details", method = RequestMethod.POST)
	public String detailsSubmit(
			@ModelAttribute("sellForm") final SellForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user,
			@PathVariable("itemId") final long id) {
		
		if (!result.hasErrors()) {
			
			final Category category = categoryService.findById(form.getCategoryId());
			final StoreItem item = new StoreItemBuilder(
						form.getName(),
						form.getDescription(),
						form.getPrice(),
						category,
						form.isUsed()
					)
					.owner(user)
					.id(id)
					.build();
			
			itemService.updateItem(item);
			
			return "redirect:/profile/details#items";
			
		}
		
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("bindingResult", result);
		model.addAttribute("item", form);
		
		return "sell";
	}
	
	

}
