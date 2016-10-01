package edu.tp.paw.service;

import java.math.BigDecimal;
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
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;


@Service
public class StoreItemService implements IStoreItemService {

	@Autowired
	private IStoreItemDao storeItemDao;
	
	@Autowired
	private ICategoryDao categoryDao;

	@Autowired
	private ICategoryService categoryService;
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreItemService#findByTerm(java.lang.String)
	 */
	@Override
	public List<StoreItem> findByTerm(String term) {
		return storeItemDao.findByTerm(term);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreItemService#create(java.lang.String, java.lang.String, float, long, java.lang.String)
	 */
	@Override
	public StoreItem create(String name, String description, BigDecimal price, long categoryId, String email) {
		
		Category category = categoryService.findById(categoryId);
		
		System.out.println("cid: " + categoryId);
		
		if (category == null) {
			return null;
		}
		
		
		return storeItemDao.create(name, description, price, category, email);
	}

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
	public List<StoreItem> findByTerm(String term, Filter filter) {
		
		return storeItemDao.findByTerm(term, filter);
	}
	
	@Override
	public List<StoreItem> findByTerm(String term, FilterBuilder filterBuilder) {
		
		Set<Category> categories = new HashSet<>(filterBuilder.category().getCategories());
		
		for (Category category : categories) {
			filterBuilder.category().in(categoryDao.getDescendants(category));
		}
		
		return storeItemDao.findByTerm(term, filterBuilder.build());
	}

	@Override
	public PagedResult<StoreItem> findByTerm(Filter filter) {
		
		return storeItemDao.findByTerm(filter);
	}

	@Override
	public PagedResult<StoreItem> findByTerm(FilterBuilder filterBuilder) {
		
		
		Set<Category> categories = new HashSet<>(filterBuilder.category().getCategories());
		
		for (Category category : categories) {
			filterBuilder.category().in(categoryDao.getDescendants(category));
		}
		
		return storeItemDao.findByTerm(filterBuilder.build());
	}
	
	

}
