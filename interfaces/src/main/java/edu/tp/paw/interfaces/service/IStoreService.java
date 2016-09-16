package edu.tp.paw.interfaces.service;

import java.util.List;

import com.sun.istack.internal.NotNull;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;

public interface IStoreService {
	
	public StoreItem sell(final String name, final String description, final float price);
	
	public @NotNull List<StoreItem> fetchItemsInCategory(final Category category);
	public @NotNull List<StoreItem> fetchItemsInCategory(final String categoryId);
}
