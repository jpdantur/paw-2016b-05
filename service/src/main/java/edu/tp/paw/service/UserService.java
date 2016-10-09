package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.interfaces.service.ICommentService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;


@Service
public class UserService implements IUserService {
	
	@Autowired private IUserDao userDao;
	@Autowired private IStoreItemService itemService;
	@Autowired private ICommentService commentService;
	
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
	public User registerUser(final UserBuilder builder) {
		
		if (builder == null) {
			throw new IllegalArgumentException("builder cannot be null");
		}
		
		
		if (userDao.emailExists(builder.getEmail())) {
			throw new IllegalStateException("a user with username " + builder.getUsername() + " already exists");
		}
		
		if (userDao.usernameExists(builder.getUsername())) {
			throw new IllegalStateException("a user with username " + builder.getUsername() + " already exists");
		}
		
		builder.password(passwordEncoder.encode(builder.getPassword()));
		return userDao.create(builder);
	}

	@Override
	public boolean idExists(final long id) {
		return userDao.idExists(id);
	}

	@Override
	public boolean usernameExists(final String username) {
		return userDao.usernameExists(username);
	}

	@Override
	public boolean emailExists(final String email) {
		return userDao.emailExists(email);
	}

	@Override
	public boolean changePassword(User user, String password) {
		
		return userDao.changePassword(user, password);
	}

	@Override
	public boolean isUsersPassword(User user, String password) {
		
		return passwordEncoder.matches(password, user.getPassword());
		
	}

	@Override
	public List<StoreItem> getFavourites(User user) {
		
		return itemService.getFavourites(user);
		
	}

	@Override
	public boolean addFavourite(final User user, final StoreItem item) {
		return userDao.addFavourite(user, item);
	}
	
	@Override
	public boolean removeFavourite(final User user, final StoreItem item) {
		return userDao.removeFavourite(user, item);
	}

	@Override
	public Comment commentOn(final User user, final StoreItem item, final String string) {
		return commentService.createComment(user, item, string);
	}

	

}
