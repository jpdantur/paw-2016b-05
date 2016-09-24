package edu.tp.paw.interfaces.service;

import java.math.BigDecimal;
import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;

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
	 * @param name The name of the StoreItem
	 * @param description The description of the StoreItem
	 * @return A new StoreItem
	 */
	public StoreItem create(final String name, final String description, final BigDecimal price, long categoryId, String email);
	
	public StoreItem create(StoreItemBuilder builder);
	
	/**
	 * Retrieves a list of the n most sold items
	 * @param n The number of items to find
	 * @return A list of the most sold items
	 */
	public List<StoreItem> fetchMostSold(final int n);
	
	/**
	 * Retrieves a list of the store items that match #{term}
	 * @param term The term to match
	 * @return A list of the store items that match #{term}
	 */
	public List<StoreItem> findByTerm(final String term);

	

}
