package edu.tp.paw.interfaces.dao;

import java.math.BigDecimal;
import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;

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
	
	/**
	 * Retrieves a list of the store items that match #{term}
	 * @param term The term to match
	 * @return A list of the store items that match #{term}
	 */
	public List<StoreItem> findByTerm(String term);
	
	/**
	 * Retrieves a list of the store items with category in #{categories}
	 * @param categories The categories to find
	 * @return A list of the store items with category in #{categories}
	 */
	public List<StoreItem> findInCategories(List<Category> categories);
	
	/**
	 * Create a new StoreItem
	 * 
	 * 
	 * @param name The name of the StoreItem
	 * @param description The description of the StoreItem
	 * @return A new StoreItem
	 * @deprecated
	 */
	public StoreItem create(final String name, final String description, final BigDecimal price, final Category category, final String email);
	
	
	/**
	 * Create a new StoreItem
	 * 
	 * 
	 * @param name The name of the StoreItem
	 * @param description The description of the StoreItem
	 * @return A new StoreItem
	 */
	public StoreItem create(final StoreItemBuilder builder);
	
}
