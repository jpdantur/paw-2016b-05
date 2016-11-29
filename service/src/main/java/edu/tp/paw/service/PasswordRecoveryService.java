package edu.tp.paw.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.service.IPasswordRecoveryService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;

@Service
public class PasswordRecoveryService implements IPasswordRecoveryService {
	
	private final static Logger logger = LoggerFactory.getLogger(PasswordRecoveryService.class);
	
	private final static long MAX_TIME_ALLOWED = TimeUnit.HOURS.toMillis(3);
	
	@Autowired IUserService userService;
	
	private String md5Hash(String s) throws NoSuchAlgorithmException {
    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.update(s.getBytes(),0,s.length());
    return new BigInteger(1, messageDigest.digest()).toString(16);
	}
	
	@Override
	public String generatePasswordRecoveryToken(final User user) {
		
		if (!userService.userExists(user)) {
			throw new IllegalStateException("user does not exist");
		}
		
		final BigInteger currentTimestamp = BigInteger.valueOf(System.currentTimeMillis()); 
		String md5Hash = null;
		try {
			md5Hash = md5Hash(user.getUsername());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return String.format("%s%s", currentTimestamp.toString(16), md5Hash);
	}
	
	@Override
	public TokenValidity checkTokenValidity(final User user, final String token) {
		
		if (!userService.userExists(user)) {
			return TokenValidity.INVALID_USERNAME;
		}
		
		logger.trace("input token is {}", token);
		
		final String generatedToken = generatePasswordRecoveryToken(user);
		
		logger.trace("current token is: {}", generatedToken);
		
		if (token.length() != generatedToken.length()) {
			logger.debug("token length is different");
			return TokenValidity.INVALID_LENGTH;
		}
		
		final BigInteger currentTimestamp = BigInteger.valueOf(System.currentTimeMillis());
		final String timestampPart = currentTimestamp.toString(16);
		
		logger.trace("timestampPart is {}", timestampPart);
		logger.trace("timestamp part of token is {}", token.substring(0, timestampPart.length()-1));
		
		final BigInteger tokenTimestamp = new BigInteger(token.substring(0, timestampPart.length()), 16);
		
		logger.trace("current timestamp is: {}", currentTimestamp);
		logger.trace("token timestamp is: {}", tokenTimestamp);
		
		if ( tokenTimestamp.compareTo(currentTimestamp) > 0) {
			// token timestamp is greater than the current one
			logger.debug("token timestamp is greater than the current one");
			return TokenValidity.INVALID_TIMESTAMP;
		}
		
		if (currentTimestamp.subtract(tokenTimestamp).compareTo(BigInteger.valueOf(MAX_TIME_ALLOWED)) > 0) {
			// MAX_TIME_ALLOWED has passed
			logger.debug("MAX_TIME_ALLOWED has passed");
			return TokenValidity.EXPIRED;
		}
		
		final String usernamePart = generatedToken.substring(timestampPart.length(), generatedToken.length()-1);
		
		logger.trace("username part of generatedToken is {}", usernamePart);
		logger.trace("username part of token is {}", token.substring(timestampPart.length(), token.length()-1));
		
		if (usernamePart.equals(token.substring(timestampPart.length(), token.length()-1))) {
			// username hashes are the same
			return TokenValidity.VALID;
		}
		
		logger.debug("username hashes do not match");
		
		return TokenValidity.INVALID_USERNAME;
	}

}
