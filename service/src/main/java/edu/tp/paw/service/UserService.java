package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.interfaces.service.ICommentService;
import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;


@Service
public class UserService implements IUserService {
	
	@Autowired private IUserDao userDao;
	@Autowired private IStoreItemService itemService;
	@Autowired private ICommentService commentService;
	@Autowired private IRoleService roleService;
	
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

	@Override
	public int getNumberOfUsers() {
		return userDao.getNumberOfUsers();
	}

	@Override
	@Transactional
	public User createUser(final UserBuilder builder, final Role role) {
		
		final User user = userDao.create(builder);
		
		if (user == null) {
			// ups
		}
		
		if (userDao.addRole(user, role)) {
			return user;
		}
		
		return null;
	}

	@Override
	public List<Role> getRoles(final User user) {
		return roleService.getRolesForUser(user);
	}

	@Override
	public boolean addRole(final User user, final Role role) {
		return userDao.addRole(user, role);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userDao.getAll();
	}

	

}
