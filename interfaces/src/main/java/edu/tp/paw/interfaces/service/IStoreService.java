package edu.tp.paw.interfaces.service;

import java.math.BigDecimal;
import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;

public interface IStoreService {
	
	/**
	 * Creates an item with given data to be sold
	 * @param name The name of the item
	 * @param description The description of the item
	 * @param price The price of the item
	 * @param categoryId The id of the category of the item
	 * @param email The email of the owner
	 * @return The created item
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
}
