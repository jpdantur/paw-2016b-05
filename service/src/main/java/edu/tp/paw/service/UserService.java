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
		return createUser(builder, roleService.getDefaultRole());
	}

	@Override
	public boolean userExists(final long id) {
		return userDao.idExists(id);
	}
	
	@Override
	public boolean userExists(final User user) {
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		return userExists(user.getId());
	}

	@Override
	public boolean usernameExists(final String username) {
		if (username == null) {
			throw new IllegalArgumentException("username cant be null");
		}
		return userDao.usernameExists(username);
	}

	@Override
	public boolean emailExists(final String email) {
		if (email == null) {
			throw new IllegalArgumentException("email cant be null");
		}
		return userDao.emailExists(email);
	}

	@Override
	public boolean changePassword(final User user, final String password) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (password == null) {
			throw new IllegalArgumentException("password cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return userDao.changePassword(user, passwordEncoder.encode(password));
	}

	@Override
	public boolean isUsersPassword(final User user, final String password) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (password == null) {
			throw new IllegalArgumentException("password cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return passwordEncoder.matches(password, user.getPassword());
		
	}

	@Override
	public List<StoreItem> getFavourites(final User user) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return itemService.getFavourites(user);
		
	}

	@Override
	public boolean addFavourite(final User user, final StoreItem item) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (item == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		if (!itemService.itemExists(item)) {
			throw new IllegalArgumentException("item must exist");
		}
		
		return userDao.addFavourite(user, item);
	}
	
	@Override
	public boolean removeFavourite(final User user, final StoreItem item) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (item == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		if (!itemService.itemExists(item)) {
			throw new IllegalArgumentException("item must exist");
		}
		
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
		
		if (builder == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (role == null) {
			throw new IllegalArgumentException("role cant be null");
		}
		
		if (!userExists(builder.getId())) {
			throw new IllegalArgumentException("user must exist");
		}
		
		if (!roleService.roleExists(role)) {
			throw new IllegalArgumentException("role must exist");
		}
		
		final User user = userDao.create(builder);
		
		if (userDao.addRole(user, role)) {
			return user;
		}
		
		return null;
	}

	@Override
	public List<Role> getRoles(final User user) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return roleService.getRolesForUser(user);
	}

	@Override
	public boolean addRole(final User user, final Role role) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (role == null) {
			throw new IllegalArgumentException("role cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		if (!roleService.roleExists(role)) {
			throw new IllegalArgumentException("role must exist");
		}
		
		return userDao.addRole(user, role);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userDao.getAll();
	}

	@Override
	public boolean updateUser(final User user) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return userDao.updateUser(user);
	}

	

}
