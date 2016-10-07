package edu.tp.paw.interfaces.service;

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
	
	boolean idExists(final long id);
	boolean usernameExists(final String username);
	boolean emailExists(final String email);
	
	/**
	 * Create a new user
	 * 
	 * @param username The name of the user
	 * @return The created user
	 */
	public User registerUser(final UserBuilder builder);
	
}
