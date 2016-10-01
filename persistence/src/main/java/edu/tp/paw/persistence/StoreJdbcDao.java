package edu.tp.paw.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IStoreDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;


@Repository
public class StoreJdbcDao implements IStoreDao {

	@Autowired
	private CategoryJdbcDao categoryDao;
	
	@Autowired
	private StoreItemJdbcDao storeItemDao;

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreDao#findInCategory(edu.tp.paw.model.Category)
	 */
	@Override
	public List<StoreItem> findInCategory(Category category) {
		
		List<Category> categories = categoryDao.getDescendants(category);
		
		categories.add(category);
		
		return storeItemDao.findInCategories(categories);
	}
	
}
