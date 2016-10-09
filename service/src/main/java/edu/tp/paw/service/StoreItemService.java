package edu.tp.paw.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;


@Service
public class StoreItemService implements IStoreItemService {

	@Autowired private IStoreItemDao storeItemDao;
	@Autowired private ICategoryDao categoryDao;
	@Autowired private ICategoryService categoryService;
	

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreItemService#fetchMostSold(int)
	 */
	@Override
	public List<StoreItem> fetchMostSold(int n) {
		return storeItemDao.findMostSold(n);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreItemService#fetchById(long)
	 */
	@Override
	public StoreItem fetchById(long id) {
		return storeItemDao.findById(id);
	}

	@Override
	public StoreItem create(final StoreItemBuilder builder) {
		
		if (builder.getCategory() == null) {
			return null;
		}
		
		
		return storeItemDao.create(builder);
	}

	@Override
	public PagedResult<StoreItem> findByFiltering(Filter filter) {
		
		final PagedResult<StoreItem> pagedResult = storeItemDao.findByTerm(filter);
		
		pagedResult.setNumberOfTotalResults(storeItemDao.getNumberOfItems());
		
		return pagedResult;
	}

	@Override
	public PagedResult<StoreItem> findByFiltering(FilterBuilder filterBuilder) {
		
		
		Set<Category> categories = new HashSet<>(filterBuilder.category().getCategories());
		
		for (Category category : categories) {
			filterBuilder.category().in(categoryDao.getDescendants(category));
		}
		
		return findByFiltering(filterBuilder.build());
	}

	@Override
	public List<StoreItem> getUserItems(final User user) {
		
		if (user == null) {
			throw new IllegalStateException("user cant be null");
		}
		
		return storeItemDao.getUserItems(user);
	}

	@Override
	public boolean updateItem(final StoreItem item) {
		
		return storeItemDao.updateItem(item);
	}

	@Override
	public List<StoreItem> getFavourites(final User user) {
		
		return storeItemDao.getFavourites(user);
	}
	
	

}
