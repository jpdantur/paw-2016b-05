package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IStoreDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;

@Service
public class StoreService implements IStoreService {

	@Autowired
	private IStoreItemService storeItemService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IStoreDao storeDao;
	
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreService#fetchItemsInCategory(edu.tp.paw.model.Category)
	 */
	@Override
	public List<StoreItem> fetchItemsInCategory(final Category category) {
		
		return storeDao.findInCategory(category);
	}

	@Override
	public StoreItem sell(StoreItemBuilder builder) {
		return storeItemService.create(builder);
	}

	@Override
	public PagedResult<StoreItem> findByTerm(Filter filter) {
		return storeItemService.findByTerm(filter);
	}

	@Override
	public PagedResult<StoreItem> findByTerm(FilterBuilder filter) {
		return storeItemService.findByTerm(filter);
	}

}
