package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;

public interface IStoreItemService {
	
	/**
	 * Retrieve a StoreItem from the database
	 * @param id id of the StoreItem
	 * @return the StoreItem with id
	 */
	public StoreItem fetchById(final long id);	
	
	public StoreItem create(final String name, final String description, final float price, long categoryId, String email);
	
	public List<StoreItem> fetchMostSold(final int n);
	
	public List<StoreItem> findByTerm(final String term);

}
