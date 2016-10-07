package edu.tp.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;


@Service
public class UserService implements IUserService {
	
	@Autowired private IUserDao userDao;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IUserService#findById(long)
	 */
	@Override
	public User findById(final long id) {
		return userDao.findById(id);
	}
	
	@Override
	public User findByUsername(final String username) {
		return userDao.findByUsername(username);
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IUserService#create(java.lang.String)
	 */
	@Override
	public User create(final UserBuilder builder) {
		builder.password(passwordEncoder.encode(builder.getPassword()));
		return userDao.create(builder);
	}

	

}
