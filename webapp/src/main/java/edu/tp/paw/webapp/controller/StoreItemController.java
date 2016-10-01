package edu.tp.paw.webapp.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;


@Controller
public class StoreItemController {

	private static final int NUMBER_OF_ITEMS_PER_PAGE = 20;
	@Autowired
	private IStoreItemService storeItemService;
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IStoreService storeService;
	
	@RequestMapping(value = {"/items", "/items/"})
	public ModelAndView itemBrowser(
			@RequestParam(value = "pageNumber", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "query", required = false) final String query,
			@RequestParam(value = "minPrice", required = false) final BigDecimal minPrice,
			@RequestParam(value = "maxPrice", required = false) final BigDecimal maxPrice,
			@RequestParam(value = "categories", required = false) final List<Long> categories) {
		
		System.out.println(categories);
		
		final FilterBuilder filter = FilterBuilder
				.create()
				.price()
					.between(minPrice, maxPrice)
				.end()
				.page()
					.size(NUMBER_OF_ITEMS_PER_PAGE)
					.take(pageNumber)
				.end()
				.term()
					.whitelist(query)
				.end();
		if (categories != null) {
			filter
				.category()
					.in(categories.stream().map((Long id) -> {
						return categoryService.findById(id.longValue());
					}).collect(Collectors.toList()))
				.end();
		}
		
		final ModelAndView modelAndView = new ModelAndView("products");
		
		final List<StoreItem> items = storeService.findByTerm(filter);
		
		modelAndView.addObject("storeItems", items);
		modelAndView.addObject("query", query);
		modelAndView.addObject("pageNumber", pageNumber);
		modelAndView.addObject("numberOfResults", items.size());
		
		return modelAndView;
	}
	
	@RequestMapping("/items/category/{categoryId}")
	public ModelAndView itemBrowser(
			@PathVariable("categoryId") final int categoryId) {
		
		final ModelAndView modelAndView = new ModelAndView("products");
		
		modelAndView.addObject("storeItems", storeService.fetchItemsInCategory(categoryId));
		modelAndView.addObject("query", categoryService.findById(categoryId).getName());
		modelAndView.addObject("pageNumber", 0);
		
		return modelAndView;
	}
	
	@RequestMapping("/item/{itemId}")
	public ModelAndView individualItem(
			@PathVariable("itemId") final int id,
			@RequestParam(value = "s", defaultValue = "false") final boolean published) {
		
		final ModelAndView modelAndView = new ModelAndView("product");
		
		final StoreItem storeItem = storeItemService.fetchById(id);
		
		if (storeItem == null) {
			throw new StoreItemNotFoundException();
		}
		
		modelAndView.addObject("storeItem", storeItem);
		modelAndView.addObject("published", published);
		
		return modelAndView;
	}

}
