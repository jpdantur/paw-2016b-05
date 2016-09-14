package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.StoreItem;


@Service
public class StoreItemService implements IStoreItemService {

	@Autowired
	private IStoreItemDao storeItemDao;

	@Override
	public StoreItem findById(long id) {
		return storeItemDao.findById(id);
	}

	@Override
	public List<StoreItem> findNMostSold(long n) {
		return storeItemDao.findNMostSold(n);
	}
	
	@Override
	public List<StoreItem> findByTerm(String term) {
		return storeItemDao.findByTerm(term);
	}

	@Override
	public StoreItem create(String name, String description, Float price) {
		return storeItemDao.create(name, description, price);
	}
	
	

}
