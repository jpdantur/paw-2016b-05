package edu.tp.paw.interfaces.dao;

import java.util.List;

import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PagedResult;

public interface IStoreItemDao {

	/**
	 * Retrieve a StoreItem from the database
	 * @param id id of the StoreItem
	 * @return the StoreItem with id
	 */
	public StoreItem findById(final long id);
	
	/**
	 * Retrieves a list of the n most sold items
	 * @param n The number of items to find
	 * @return A list of the most sold items
	 */
	public List<StoreItem> findMostSold(final int n);
	
	public PagedResult<StoreItem> findByTerm(final Filter filter);
	
	
	/**
	 * Create a new StoreItem
	 * 
	 * 
	 * @param name The name of the StoreItem
	 * @param description The description of the StoreItem
	 * @return A new StoreItem
	 */
	public StoreItem create(final StoreItemBuilder builder);
	
	public List<StoreItem> getUserItems(final User user);
	public List<StoreItem> getUserItems(final User user, final StoreItemStatus status);
	
	public boolean updateItem(final StoreItem item);
	
	public int getNumberOfItems();
	public int getNumberOfItems(final Filter filter);
	
	public boolean itemExists(final long id);
	public boolean itemExists(final StoreItem item);

	public boolean increaseSellCount(final StoreItem item); 
	
	public boolean updateItemStatus(final StoreItem item, final StoreItemStatus status);

	public PagedResult<StoreItem> getUserItems(final User user, final Filter filter);
	
}
