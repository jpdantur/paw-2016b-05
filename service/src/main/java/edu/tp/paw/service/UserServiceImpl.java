package edu.tp.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.UserDao;
import edu.tp.paw.interfaces.service.UserService;
import edu.tp.paw.model.User;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}

	@Override
	public User create(String username) {
		return userDao.create(username);
	}

}
