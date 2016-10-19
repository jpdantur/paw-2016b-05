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
import org.springframework.web.bind.annotation.ResponseBody;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;
import edu.tp.paw.webapp.form.CommentForm;
import edu.tp.paw.webapp.form.SellForm;

@Controller
@RequestMapping("/store/item")
public class StoreItemController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(StoreItemController.class);
	
	@Autowired private IStoreService storeService;
	@Autowired private IStoreItemService itemService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IUserService userService;
	
	@RequestMapping( value = "/{itemId}/details", method = RequestMethod.GET)
	public String details(
			@PathVariable("itemId") final long id,
			@ModelAttribute("sellForm") final SellForm form,
			final BindingResult result,
			final Model model) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			logger.info("item with id {} was not found", id);
			throw new StoreItemNotFoundException();
		}
		
		model.addAttribute("bindingResult", result);
		model.addAttribute("item", item);
		model.addAttribute("categories", categoryService.getCategories());
		
		return "edit";
	}
	
	@RequestMapping( value = "/{itemId}/details", method = RequestMethod.POST)
	public String detailsSubmit(
			@Valid @ModelAttribute("sellForm") final SellForm form,
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
		
		return "edit";
	}
	
	@RequestMapping( value = "/{itemId}/comment", method = RequestMethod.POST)
	public String commentSubmit(
			@Valid @ModelAttribute("commentForm") final CommentForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user,
			@PathVariable("itemId") final long id) {
		
		if (!result.hasErrors()) {
			
			final StoreItem item = itemService.findById(id);
			
			if (item == null) {
				throw new StoreItemNotFoundException();
			}
			itemService.addCommentBy(user, item, form.getContent());
			
			
			return "redirect:/store/items/"+id;
			
		}
		
		return "redirect:/store/items/"+id+"?e=1";
	}
	
	@RequestMapping( value = "/{itemId}/favourite", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String makeFavourite(
			@PathVariable("itemId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			return "{\"err\":3 }";
		}
		
		if (userService.addFavourite(user, item)) {
			
			return "{\"err\":0 }";
		}
		
		return "{\"err\":2 }";
	}
	
	@RequestMapping( value = "/{itemId}/favourite/remove", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String removeFavourite(
			@PathVariable("itemId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			return "{\"err\":3 }";
		}
		
		if (userService.removeFavourite(user, item)) {
			
			return "{\"err\":0 }";
		}
		
		return "{\"err\":2 }";
	}
	
	@RequestMapping( value = "/{itemId}/purchase", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String purchaseItem(
			@PathVariable("itemId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			return "{\"err\":3 }";
		}
		
		if (storeService.purchase(item)) {
			return "{\"err\":0 }";
		}
		
		return "{\"err\": 1}";
	}

}
