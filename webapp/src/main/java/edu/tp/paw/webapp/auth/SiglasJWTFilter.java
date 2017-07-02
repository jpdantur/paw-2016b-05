package edu.tp.paw.webapp.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.sun.research.ws.wadl.Request;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

//@Component
public class SiglasJWTFilter extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(SiglasJWTFilter.class);
	
//	@Autowired private TokenUtils tokenUtils;
	
	@Value("${siglas.token.secret}") private String secret;
	@Value("${siglas.token.audience}") private String audience;
	
	private final String[] whitelist;
	private final RequestMatcher requestMatcher;
	
	@Autowired UserDetailsService userDetailsService;
	
	public SiglasJWTFilter(String... whitelist) {
		super();
		this.whitelist = whitelist;
		
		final SiglasJWTFilter self = this;
		
		this.requestMatcher = new RequestMatcher() {
			
			private Collection<AntPathRequestMatcher> requestMatchers = Arrays.asList(self.whitelist).stream().map(v -> new AntPathRequestMatcher(v)).collect(Collectors.toList());
			
			@Override
			public boolean matches(HttpServletRequest request) {
				
				for (AntPathRequestMatcher rm : requestMatchers) {
		      if (rm.matches(request)) {
		      	return true;
		      }
		    }

		    return false;
			}
		};
	}
	
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		
		logger.trace("filtering header");
		
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse)res;
		
		if (this.requestMatcher.matches(request)) {
			logger.trace("matches request skipping");
			chain.doFilter(req, res);
			return;
		}
		
		logger.trace(request.getHeader("Authorization"));

    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    	logger.trace("missing auth header");
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	response.getWriter().write("Missing or invalid Authorization header.");
    	response.getWriter().flush();
    	response.getWriter().close();
    	return;
    }

    final String token = authHeader.substring(7).trim(); // The part after "Bearer "

    Claims claims;
    
    try {
        claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        request.setAttribute("claims", claims);
    }
    catch (final Exception e) {
    	logger.trace("invalid token");
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	response.getWriter().write("Invalid token.");
    	response.getWriter().flush();
    	response.getWriter().close();
    	return;
    }
    
    logger.trace("read credentials");
    
    if (claims.getAudience() == null || !claims.getAudience().equals(audience)) {
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	response.getWriter().write("Invalid audience.");
    	response.getWriter().flush();
    	response.getWriter().close();
    }
    
    String username = claims.getSubject();

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (isTokenExpired(claims)) {
      	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      	response.getWriter().write("Expired token.");
      	response.getWriter().flush();
      	response.getWriter().close();
      } else {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    chain.doFilter(req, res);
	}
	
	private boolean isTokenExpired(Claims claims) {
		return claims.getExpiration().before(new Date(System.currentTimeMillis()));
	}
}
