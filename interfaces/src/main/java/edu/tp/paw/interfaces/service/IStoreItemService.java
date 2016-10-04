package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;

public interface IStoreItemService {
	
	/**
	 * Retrieve a StoreItem from the database
	 * @param id id of the StoreItem
	 * @return the StoreItem with id
	 */
	public StoreItem fetchById(final long id);	
	
	/**
	 * Create a new StoreItem
	 * 
	 * 
	 * @param builder The StoreItembBuilder associated to the StoreItem
	 * @return A new StoreItem
	 */
	public StoreItem create(StoreItemBuilder builder);
	
	/**
	 * Retrieves a list of the n most sold items
	 * @param n The number of items to find
	 * @return A list of the most sold items
	 */
	public List<StoreItem> fetchMostSold(final int n);
	
	public PagedResult<StoreItem> findByTerm(final Filter filter);
	public PagedResult<StoreItem> findByTerm(final FilterBuilder filter);

	

}
