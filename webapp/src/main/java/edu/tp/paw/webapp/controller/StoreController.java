package edu.tp.paw.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.webapp.form.SellForm;


@Controller
public class StoreController {

	private static final int MOST_SOLD_ITEMS = 6;
	@Autowired
	private IStoreItemService storeItemService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IStoreService storeService;
	
	/**
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView index() {
		
		final ModelAndView modelAndView = new ModelAndView("index");
		
		modelAndView.addObject("mostSoldItems", storeItemService.fetchMostSold(MOST_SOLD_ITEMS));
		
		modelAndView.addObject("categories", categoryService.getCategoryTree());
		
		return modelAndView;
	}
	
	@RequestMapping( value = "/sell", method = RequestMethod.GET)
	public String sellItem(
			@ModelAttribute("sellForm") SellForm form,
			BindingResult bindingResult,
			Model model) {
		
		model.addAttribute("categories", categoryService.getCategoryTree());
		model.addAttribute("bindingResult", bindingResult);
		model.addAttribute("item", form);
		
		return "sell";
	}
	
	@RequestMapping( value = "/sell", method = RequestMethod.POST)
	public String sell(
			@Valid @ModelAttribute("sellForm") SellForm form,
			BindingResult bindingResult,
			Model model) {
		
		if (!bindingResult.hasErrors()) {
			
			final StoreItem storeItem = storeService.sell(form.getName(), form.getDescription(), form.getPrice(), form.getCategoryId(), form.getEmail());
			
			return "redirect:/item/"+storeItem.getId()+"?s=1";
		}
		
		model.addAttribute("categories", categoryService.getCategoryTree());
		model.addAttribute("bindingResult", bindingResult);
		model.addAttribute("item", form);
		
		return "sell";
		
//		return modelAndView;
	}

}
