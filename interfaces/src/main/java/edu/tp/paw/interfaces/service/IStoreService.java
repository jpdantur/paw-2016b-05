package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;

public interface IStoreService {
	
	public StoreItem sell(final String name, final String description, final float price, final long categoryId);
	
	public List<StoreItem> fetchItemsInCategory(final Category category);
	public List<StoreItem> fetchItemsInCategory(final long categoryId);
}
