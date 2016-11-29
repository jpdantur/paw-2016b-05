package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.User;

public interface IPasswordRecoveryService {

	public static enum TokenValidity {
		INVALID_LENGTH,
		INVALID_TIMESTAMP,
		EXPIRED,
		INVALID_USERNAME,
		VALID
	};
	
	public String generatePasswordRecoveryToken(final User user);
	
	public TokenValidity checkTokenValidity(final User user, final String token);
}
