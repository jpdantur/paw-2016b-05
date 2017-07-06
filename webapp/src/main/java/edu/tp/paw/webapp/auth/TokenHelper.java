package edu.tp.paw.webapp.auth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.tp.paw.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class TokenHelper {

	private static final Logger logger = LoggerFactory.getLogger(TokenHelper.class);
	
	private SecureRandom random = new SecureRandom();
	
	@Value("${siglas.token.audience}") private String audience;
	@Value("${siglas.token.issuer}") private String issuer;
	@Value("${siglas.token.secret}") private String secret;
	@Value("${siglas.token.expiration}") private Long expiration;
	
	public String buildToken(final User user) {
		final Claims claims = Jwts.claims();
		
		claims.setAudience(audience);
		claims.setSubject(user.getUsername());
		claims.setExpiration(new Date(System.currentTimeMillis() + expiration));
		claims.setIssuedAt(new Date());
		claims.setIssuer(issuer);
		
		return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(secret))
        .compact();
	}
	
	public String createRefreshToken() {
		return new BigInteger(130, random).toString(32);
	}
	
	public Claims decodeToken(String token) {
		
		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token).getBody();
	}
	
}
