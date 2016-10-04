package edu.tp.paw.webapp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.OrderFilter;
import edu.tp.paw.model.filter.PagedResult;
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
	
	
	
	//TODO move all parameters to SearchForm
	@RequestMapping(value = {"/items", "/items/"})
	public String itemBrowser(
			@RequestParam(value = "pageNumber", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = NUMBER_OF_ITEMS_PER_PAGE+"") int pageSize,
			@RequestParam(value = "query", required = false) final String query,
			@RequestParam(value = "orderBy", required = false, defaultValue = "price") final String orderBy,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "asc") final String sortOrder,
			@RequestParam(value = "minPrice", required = false) final BigDecimal minPrice,
			@RequestParam(value = "maxPrice", required = false) final BigDecimal maxPrice,
			@RequestParam(value = "categories[]", required = false) final List<Long> categories,
			Model model) {
		
		System.out.println(categories);
		
		final FilterBuilder filter = FilterBuilder
				.create()
				.price()
					.between(minPrice, maxPrice)
				.end().page()
					.size(pageSize)
					.take(pageNumber)
				.end().query()
					.text(query)
				.end().sort()
					.by(OrderFilter.orderByMapping.apply(orderBy))
					.order(OrderFilter.sortOrderMapping.apply(sortOrder))
				.end();
		
		List<Category> selectedCategories = new ArrayList<>();
		
		if (categories != null) {
			selectedCategories = categories.stream().map((Long id) -> {
				return categoryService.findById(id.longValue());
			}).collect(Collectors.toList());
			filter
				.category()
					.in(selectedCategories)
				.end();
		}
		
		final PagedResult<StoreItem> pagedResults = storeService.search(filter);
		
		// if there is just one item just show it
		if (pagedResults.getNumberOfAvailableResults() == 1) {
			return "redirect:/item/"+pagedResults.getResults().get(0).getId();
		}
		
		final List<Category> similarCategories;
		
		if (!filter.getCategoryFilter().getCategories().isEmpty()) {
			similarCategories = categoryService.getChildren(filter.getCategoryFilter().getCategories());
		} else {
			similarCategories = categoryService.getMainCategories();
		}
		
		model.addAttribute("storeItems", pagedResults.getResults());
		model.addAttribute("query", query);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("numberOfResults", pagedResults.getNumberOfTotalResults());
		model.addAttribute("shownResults", pagedResults.getNumberOfAvailableResults());
		model.addAttribute("lastPage", pagedResults.getNumberOfTotalResults()/pagedResults.getPageSize());
		model.addAttribute("pageSize", pagedResults.getPageSize());
		model.addAttribute("sort", String.format("%s-%s", orderBy, sortOrder));
		model.addAttribute("similarCategories", similarCategories);
		model.addAttribute("selectedCategories", selectedCategories);
		model.addAttribute("filter", filter);
		
		System.out.println("pageSize: " + pagedResults.getPageSize());
		System.out.println("numberOfResults: " + pagedResults.getNumberOfTotalResults());
		System.out.println("shownResults: " + pagedResults.getNumberOfAvailableResults());
		System.out.println("lastPage: " + (pagedResults.getNumberOfTotalResults()/pagedResults.getPageSize()));
		
		return "products";
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
