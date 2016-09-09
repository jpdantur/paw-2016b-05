package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.service.StoreItemDao;
import edu.tp.paw.interfaces.service.StoreItemService;
import edu.tp.paw.interfaces.service.UserDao;
import edu.tp.paw.interfaces.service.UserService;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;


@Service
public class StoreItemServiceImpl implements StoreItemService {

	@Autowired
	private StoreItemDao storeItemDao;

	@Override
	public StoreItem findById(long id) {
		return storeItemDao.findById(id);
	}

	@Override
	public List<StoreItem> findNMostSold(long n) {
		return storeItemDao.findNMostSold(n);
	}

	@Override
	public StoreItem create(String name, String description) {
		return storeItemDao.create(name, description);
	}
	
	

}
