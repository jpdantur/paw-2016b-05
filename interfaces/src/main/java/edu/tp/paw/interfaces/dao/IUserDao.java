package edu.tp.paw.interfaces.dao;

import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

public interface IUserDao {

	/**
	 * Retrieve an user by id
	 * @param id The id of the user to retrieve
	 * @return The requested user, or null if not found
	 */
	User findById(final long id);
	
	User findByUsername(final String username);
	
	/**
	 * Create a new user
	 * 
	 * @param username The name of the user
	 * @return The created user
	 */
	User create(final UserBuilder builder);
	
}
