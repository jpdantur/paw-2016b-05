package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;

public interface IStoreItemService {
	
	/**
	 * Retrieve a StoreItem from the database
	 * @param id id of the StoreItem
	 * @return the StoreItem with id
	 */
	public StoreItem findById(final long id);	
	
	public boolean itemExists(final long id);
	public boolean itemExists(final StoreItem item);
	
	/**
	 * Create a new StoreItem
	 * 
	 * 
	 * @param builder The StoreItembBuilder associated to the StoreItem
	 * @return A new StoreItem
	 */
	public StoreItem create(final StoreItemBuilder builder);
	
	/**
	 * Retrieves a list of the n most sold items
	 * @param n The number of items to find
	 * @return A list of the most sold items
	 */
	public List<StoreItem> getMostSold(final int n);
	
	public PagedResult<StoreItem> findByFiltering(final Filter filter);
	public PagedResult<StoreItem> findByFiltering(final FilterBuilder filter);

	public List<StoreItem> getUserItems(final User user);
	
	public boolean updateItem(final StoreItem item);
	
	public List<StoreItem> getFavourites(final User user);

	public Comment addCommentBy(final User user, final StoreItem item, final String comment);
	
	public List<Comment> getComments(final StoreItem item);
	
	public int getNumberOfItems();
	
	public boolean increaseSellCount(final StoreItem item);
	
}
