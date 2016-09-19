package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;


@Service
public class StoreItemService implements IStoreItemService {

	@Autowired
	private IStoreItemDao storeItemDao;

	@Autowired
	private ICategoryService categoryService;
	
	@Override
	public List<StoreItem> findByTerm(String term) {
		return storeItemDao.findByTerm(term);
	}

	@Override
	public StoreItem create(String name, String description, float price, long categoryId, String email) {
		
		Category category = categoryService.findById(categoryId);
		
		System.out.println("cid: " + categoryId);
		
		if (category == null) {
			return null;
		}
		
		
		return storeItemDao.create(name, description, price, category, email);
	}

	@Override
	public List<StoreItem> fetchMostSold(int n) {
		return storeItemDao.findMostSold(n);
	}

	@Override
	public StoreItem fetchById(long id) {
		return storeItemDao.findById(id);
	}
	
	

}
