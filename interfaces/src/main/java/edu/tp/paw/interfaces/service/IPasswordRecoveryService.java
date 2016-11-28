package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.User;

public interface IPasswordRecoveryService {

	public String generatePasswordRecoveryToken(final User user);
	
	public boolean checkTokenValidity(final User user, final String token);
}
