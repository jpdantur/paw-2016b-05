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
import org.springframework.web.bind.annotation.ResponseBody;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
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
		
		model.addAttribute("show", "details");
		model.addAttribute("bindingResult", result);
		model.addAttribute("item", item);
		model.addAttribute("categories", categoryService.getCategoryTree());
		
		return "edit";
	}
	
	@RequestMapping( value = "/{itemId}/images", method = RequestMethod.GET)
	public String images(
			@PathVariable("itemId") final long id,
			@RequestParam( name = "s", required = false) final boolean success,
			final Model model) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			logger.info("item with id {} was not found", id);
			throw new StoreItemNotFoundException();
		}
		
		model.addAttribute("show", "images");
		model.addAttribute("item", item);
		model.addAttribute("success", success);
		
		return "edit";
	}
	
	@RequestMapping( value = "/{itemId}/details", method = RequestMethod.POST)
	public String detailsSubmit(
			@Valid @ModelAttribute("sellForm") final SellForm form,
			final BindingResult result,
			final Model model,
			@ModelAttribute("loggedUser") final User user,
			@PathVariable("itemId") final long id) {
		
		boolean success = false;
		
		final StoreItem originalItem = itemService.findById(id);
		
		if (originalItem == null) {
			logger.info("item with id {} was not found", id);
			throw new StoreItemNotFoundException();
		}
		
		if (!result.hasErrors()) {
			
			final Category category = categoryService.findById(form.getCategoryId());
			final StoreItem item = new StoreItemBuilder(
						form.getName(),
						form.getDescription(),
						form.getPrice(),
						form.isUsed()
					)
					.category(category)
					.owner(user)
					.id(id)
					.created(originalItem.getCreated())
					.status(form.getStatus())
					.sold(originalItem.getSold())
					.build();
			
			itemService.updateItem(item);
			
			model.addAttribute("success", true);
			model.addAttribute("item", item);
			success = true;
			
		}
		model.addAttribute("show", "details");
		model.addAttribute("categories", categoryService.getCategoryTree());
		model.addAttribute("bindingResult", result);
		if (!success) {
			model.addAttribute("item", originalItem);
		}
		
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
			final CommentBuilder builder = new CommentBuilder(user, form.getContent(), form.getRating()).item(item);
			itemService.addComment(builder);
			
			
			return "redirect:/store/items/"+id;
			
		}
		
		return "redirect:/store/items/"+id+"?e=1";
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
		
		final PurchaseBuilder builder = new PurchaseBuilder(user, item);
		final Purchase purchase = storeService.purchase(builder);
		if (purchase != null) {
			logger.trace("user {} purchased item {}", user.getUsername(), item.getId());
			return "{\"err\":0 }";
		}
		
		return "{\"err\": 1}";
	}
	
	@RequestMapping( value = "/{itemId}/resume", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String resumeItem(
			@PathVariable("itemId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			return "{\"err\":3 }";
		}
		
		if (itemService.resumeStoreItem(item)) {
			return "{\"err\":0 }";
		}
		
		return "{\"err\": 1}";
	}
	
	@RequestMapping( value = "/{itemId}/pause", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String pauseItem(
			@PathVariable("itemId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			return "{\"err\":3 }";
		}
		
		if (itemService.pauseStoreItem(item)) {
			return "{\"err\":0 }";
		}
		
		
		return "{\"err\": 1}";
	}
	
	@RequestMapping( value = "/{itemId}/publish", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String publishItem(
			@PathVariable("itemId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			return "{\"err\":3 }";
		}
		
		if (itemService.publishStoreItem(item)) {
			return "{\"err\":0 }";
		}
		
		
		return "{\"err\": 1}";
	}

}
