package edu.tp.paw.interfaces.service;

import java.math.BigDecimal;
import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;

public interface IStoreService {
	
	/**
	 * Creates an item with given data to be sold
	 * @param name The name of the item
	 * @param description The description of the item
	 * @param price The price of the item
	 * @param categoryId The id of the category of the item
	 * @param email The email of the owner
	 * @return The created item
	 * @deprecated
	 */
	public StoreItem sell(final String name, final String description, final BigDecimal price, final long categoryId, String email);
	
	public StoreItem sell(final StoreItemBuilder builder);
	
	/**
	 * Retrieves items from #{category}
	 * @param category The category of the items
	 * @return The found items
	 */
	public List<StoreItem> fetchItemsInCategory(final Category category);
	
	/**
	 * Retrieves items from category with #{categoryId}
	 * @param categoryId The id of the category
	 * @return The found items
	 */
	public List<StoreItem> fetchItemsInCategory(final long categoryId);
	
	/**
	 * Retrieves a list of the store items that match #{term}
	 * @param term The term to match
	 * @return A list of the store items that match #{term}
	 * @deprecated
	 */
	public List<StoreItem> findByTerm(final String term);
	
	/**
	 * Retrieves a list of the store items that match #{term}
	 * @param term The term to match
	 * @return A list of the store items that match #{term}
	 */
	public List<StoreItem> findByTerm(final String term, final Filter filter);
	public List<StoreItem> findByTerm(final String term, final FilterBuilder filter);
	public List<StoreItem> findByTerm(final Filter filter);
	public List<StoreItem> findByTerm(final FilterBuilder filter);
}
