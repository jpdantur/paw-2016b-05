package edu.tp.paw.interfaces.dao;

import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;

public interface IStoreDao {

	/**
	 * Finds items from given category
	 * @param category
	 * @return List with found items
	 */
	public List<StoreItem> findInCategory(final Category category);
	
}
