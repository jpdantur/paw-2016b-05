package edu.tp.paw.service;

import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;

public class StoreService implements IStoreService {

	@Override
	public @NotNull List<StoreItem> fetchItemsInCategory(final Category category) {
		
		final List<StoreItem> storeItems = new ArrayList<>();
		
		return storeItems;
	}

	@Override
	public @NotNull List<StoreItem> fetchItemsInCategory(final String categoryId) {
		
		final List<StoreItem> storeItems = new ArrayList<>();
		
		return storeItems;
	}

}
