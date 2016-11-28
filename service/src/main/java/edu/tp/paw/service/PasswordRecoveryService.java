package edu.tp.paw.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.service.IPasswordRecoveryService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;

@Service
public class PasswordRecoveryService implements IPasswordRecoveryService {
	
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
	public boolean checkTokenValidity(final User user, final String token) {
		
		if (!userService.userExists(user)) {
			throw new IllegalStateException("user does not exist");
		}
		
		final String generatedToken = generatePasswordRecoveryToken(user);
		
		if (token.length() != generatedToken.length()) {
			return false;
		}
		
		final BigInteger currentTimestamp = BigInteger.valueOf(System.currentTimeMillis());
		final String timestampPart = currentTimestamp.toString(16);
		final BigInteger tokenTimestamp = new BigInteger(token.substring(0, timestampPart.length()-1), 16);
		
		if ( tokenTimestamp.compareTo(currentTimestamp) > 0) {
			// token timestamp is greater than the current one
			return false;
		}
		
		if (currentTimestamp.subtract(tokenTimestamp).compareTo(BigInteger.valueOf(MAX_TIME_ALLOWED)) > 0) {
			// MAX_TIME_ALLOWED has passed
			return false;
		}
		
		final String usernamePart = generatedToken.substring(timestampPart.length(), generatedToken.length()-1);
		
		if (usernamePart.equals(token.substring(timestampPart.length(), token.length()-1))) {
			// username hashes are the same
			return true;
		}
		
		return false;
	}

}
