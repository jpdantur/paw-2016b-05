package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;

public interface IStoreService {
	
	public StoreItem sell(final StoreItemBuilder builder);
	
	/**
	 * Retrieves items from #{category}
	 * @param category The category of the items
	 * @return The found items
	 */
	public List<StoreItem> fetchItemsInCategory(final Category category);
	
	public PagedResult<StoreItem> findByTerm(final Filter filter);
	public PagedResult<StoreItem> findByTerm(final FilterBuilder filter);
}
