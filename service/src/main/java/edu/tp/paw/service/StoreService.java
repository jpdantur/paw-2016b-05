package edu.tp.paw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;

import edu.tp.paw.interfaces.dao.IStoreDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;

@Service
public class StoreService implements IStoreService {

	@Autowired
	private IStoreItemService storeItemService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IStoreDao storeDao;
	
	
	@Override
	public @NotNull List<StoreItem> fetchItemsInCategory(final Category category) {
		
		return storeDao.findInCategory(category);
	}

	@Override
	public @NotNull List<StoreItem> fetchItemsInCategory(final long categoryId) {
		
		Category category = categoryService.findById(categoryId);
		
		if (category == null) {
			return new ArrayList<>();
		}
		
		return fetchItemsInCategory(category);
	}

	@Override
	public StoreItem sell(String name, String description, float price, long categoryId) {
		return storeItemService.create(name, description, price, categoryId);
	}

}
