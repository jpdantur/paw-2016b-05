package edu.tp.paw.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreService#fetchItemsInCategory(long)
	 */
	@Override
	public List<StoreItem> fetchItemsInCategory(final long categoryId) {
		
		Category category = categoryService.findById(categoryId);
		
		if (category == null) {
			return new ArrayList<>();
		}
		
		return fetchItemsInCategory(category);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreService#sell(java.lang.String, java.lang.String, float, long, java.lang.String)
	 */
	@Override
	public StoreItem sell(String name, String description, BigDecimal price, long categoryId, String email) {
		return storeItemService.create(name, description, price, categoryId, email);
	}

	@Override
	public StoreItem sell(StoreItemBuilder builder) {
		return storeItemService.create(builder);
	}

	@Override
	public List<StoreItem> findByTerm(String term) {
		return storeItemService.findByTerm(term);
	}

	@Override
	public List<StoreItem> findByTerm(String term, Filter filter) {
		return storeItemService.findByTerm(term, filter);
	}

}
