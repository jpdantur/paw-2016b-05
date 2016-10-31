package edu.tp.paw.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;

@Service
@Transactional
public class StoreService implements IStoreService {

	private final static Logger logger = LoggerFactory.getLogger(StoreService.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IUserService userService;

	@Override
	public StoreItem sell(final StoreItemBuilder builder) {
		return storeItemService.create(builder);
	}

	@Override
	public PagedResult<StoreItem> search(final Filter filter) {
		return storeItemService.findByFiltering(filter);
	}

	@Override
	public PagedResult<StoreItem> search(final FilterBuilder filter) {
		return storeItemService.findByFiltering(filter);
	}

	@Override
	public boolean purchase(final User user, final StoreItem item) {
		return userService.purchase(user, item);
	}

	@Override
	public Set<Category> getCategoriesForResultsInHigherDepthCategories(final Set<Category> categories, final List<StoreItem> items) {
		
		Set<Category> similarCategories;
		
		if (!categories.isEmpty()) {
			similarCategories = categoryService.getChildren(categories);
		} else {
			similarCategories = categoryService.getMainCategories();
		}
		
		logger.trace("similar categories {}", similarCategories);
		
		return similarCategories.stream().filter(c -> {
			logger.trace("checking if category {} has any items", c.getName());
			final boolean b = items.stream().anyMatch(item -> {
				logger.trace("checking if item {} has category {}", item.getName(), c.getName());
				if (item.getCategory().equals(c)) {
					logger.trace("it does");
					return true;
				}
				Category prev = item.getCategory();
				Category category = item.getCategory().getParent();
				while (category != prev) {
					if (category.equals(c)) {
						logger.trace("it does");
						return true;
					}
					prev = category;
					category = category.getParent();
				}
				logger.trace("it does not");
				return false;
			});
			return b;
		}).collect(Collectors.toSet());
	}

}
