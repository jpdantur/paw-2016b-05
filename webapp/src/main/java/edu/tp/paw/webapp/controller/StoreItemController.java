package edu.tp.paw.webapp.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;
import edu.tp.paw.webapp.form.SearchForm;


@Controller
public class StoreItemController extends BaseController {

	@Autowired private IStoreItemService storeItemService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IStoreService storeService;
	
	@RequestMapping(value = {"/items", "/items/"})
	public String itemBrowser(
			@ModelAttribute("searchForm") SearchForm form,
			Model model) {
		
		final FilterBuilder filter = FilterBuilder
				.create()
				.price()
					.between(form.getMinPrice(), form.getMaxPrice())
				.end().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.end().query()
					.text(form.getQuery())
				.end().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end();
		
		List<Category> selectedCategories = new ArrayList<>();
		
		if (form.getCategories() != null) {
			selectedCategories = form.getCategories().stream().map((Long id) -> {
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
		model.addAttribute("query", form.getQuery());
		model.addAttribute("pageNumber", form.getPageNumber());
		model.addAttribute("numberOfResults", pagedResults.getNumberOfTotalResults());
		model.addAttribute("shownResults", pagedResults.getNumberOfAvailableResults());
		model.addAttribute("lastPage", pagedResults.getNumberOfTotalResults()/pagedResults.getPageSize());
		model.addAttribute("pageSize", pagedResults.getPageSize());
		model.addAttribute("sort", String.format("%s-%s", form.getOrderBy(), form.getSortOrder()));
		model.addAttribute("similarCategories", similarCategories);
		model.addAttribute("selectedCategories", selectedCategories);
		model.addAttribute("filter", filter);
		
		return "products";
	}
	
	@RequestMapping(value = {"/item/{itemId}", "/item/{itemId}/"})
	public ModelAndView individualItem(
			@PathVariable("itemId") final int id,
			@RequestParam(value = "s", defaultValue = "false") final boolean published,
			Locale locale) {
		
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
