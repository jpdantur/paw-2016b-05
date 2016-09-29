package edu.tp.paw.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;


@Service
public class StoreItemService implements IStoreItemService {

	@Autowired
	private IStoreItemDao storeItemDao;

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
		
		return null;
	}
	
	

}
