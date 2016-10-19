package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

public interface IUserService {

	/**
	 * Retrieve an user by id
	 * @param id The id of the user to retrieve
	 * @return The requested user, or null if not found
	 */
	public User findById(final long id);
	public User findByUsername(final String username);
	
	boolean userExists(final User user);
	boolean userExists(final long id);
	boolean usernameExists(final String username);
	boolean emailExists(final String email);
	
	/**
	 * Create a new user
	 * 
	 * @param username The name of the user
	 * @return The created user
	 */
	public User registerUser(final UserBuilder builder);
	
	public User createUser(final UserBuilder builder, final Role role);
	
	public boolean changePassword(final User user, final String password);
	
	public boolean isUsersPassword(final User user, final String password);
	
	public List<StoreItem> getFavourites(final User user);
	
	public boolean addFavourite(final User user, final StoreItem item);
	public boolean removeFavourite(final User user, final StoreItem item);
	
	public Comment commentOn(final User user, final StoreItem item, final String string);
	
	public int getNumberOfUsers();
	
	public List<Role> getRoles(final User user);
	public boolean addRole(final User user, final Role role);
	
	public List<User> getAllUsers();
	
	public boolean updateUser(final User user);
	
}
