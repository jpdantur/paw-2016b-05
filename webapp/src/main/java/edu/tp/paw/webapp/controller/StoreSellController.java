package edu.tp.paw.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;
import edu.tp.paw.webapp.form.SellForm;


@Controller
@RequestMapping("/store/sell")
public class StoreSellController extends BaseController {
	
	@Autowired
	private IStoreItemService storeItemService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IStoreService storeService;
	
	@RequestMapping( value = "/details", method = RequestMethod.GET)
	public String sellItem(
			@ModelAttribute("sellForm") SellForm form,
			BindingResult bindingResult,
			Model model) {
		
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("bindingResult", bindingResult);
		model.addAttribute("item", form);
		
		return "sell";
	}
	
	@RequestMapping( value = "/details", method = RequestMethod.POST)
	public String sell(
			@Valid @ModelAttribute("sellForm") SellForm form,
			final BindingResult bindingResult,
			final Model model,
			@ModelAttribute("loggedUser") final User user) {
		
		System.out.println(form);
		
		if (!bindingResult.hasErrors()) {
			
			System.out.println(user);
			
			Category category = categoryService.findById(form.getCategoryId());
			StoreItemBuilder storeItemBuilder = new StoreItemBuilder(form.getName(), form.getDescription(), form.getPrice(), category, form.isUsed()).owner(user);
			
			final StoreItem storeItem = storeService.sell(storeItemBuilder);
			
			if (storeItem == null) {
				//something wrong happened
			}
			
			
			return "redirect:/store/sell/images/"+storeItem.getId()+"?s=1";
		}
		
		model.addAttribute("categories", categoryService.getCategoryTree());
		model.addAttribute("bindingResult", bindingResult);
		model.addAttribute("item", form);
		
		return "sell";
		
//		return modelAndView;
	}
	
	@RequestMapping( value = "/images/{itemId}", method = RequestMethod.GET )
	public String sellStage2(
			@RequestParam( value = "s", required = false) boolean s,
			@PathVariable("itemId") long itemId,
			Model model) {
		
		final StoreItem item = storeItemService.fetchById(itemId);
		
		if (item == null) {
			throw new StoreItemNotFoundException();
		}
		
		model.addAttribute("publishedItem", s);
		model.addAttribute("item", item);
		
		return "sell2";
		
	}

}
