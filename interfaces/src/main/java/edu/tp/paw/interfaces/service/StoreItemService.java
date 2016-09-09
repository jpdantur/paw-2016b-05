package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.StoreItem;

public interface StoreItemService {
	
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
	public List<StoreItem> findNMostSold(final long n);
	
	/**
	 * Create a new StoreItem
	 * 
	 * 
	 * @param name The name of the StoreItem
	 * @param description The description of the StoreItem
	 * @return A new StoreItem
	 */
	public StoreItem create(final String name, final String description);

}
