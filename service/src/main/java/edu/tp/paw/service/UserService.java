package edu.tp.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;


@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IUserService#findById(long)
	 */
	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IUserService#create(java.lang.String)
	 */
	@Override
	public User create(String username) {
		return userDao.create(username);
	}

}
