package edu.tp.paw.interfaces.dao;

import edu.tp.paw.model.User;

public interface IUserDao {

	/**
	 * Retrieve an user by id
	 * @param id The id of the user to retrieve
	 * @return The requested user, or null if not found
	 */
	User findById(long id);
	
	/**
	 * Create a new user
	 * 
	 * @param username The name of the user
	 * @param id The id of the user
	 * @return The created user
	 */
	User create(String username);
	
}
