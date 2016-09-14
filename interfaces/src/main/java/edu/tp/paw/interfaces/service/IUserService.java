package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.User;

public interface IUserService {

	public User findById(long id);
	
	public User create(String username);
	
}
