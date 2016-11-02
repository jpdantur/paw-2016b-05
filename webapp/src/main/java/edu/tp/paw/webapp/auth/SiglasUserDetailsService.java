package edu.tp.paw.webapp.auth;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.User;

@Component
public class SiglasUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(SiglasUserDetailsService.class);
	
	@Autowired private IUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		
		logger.debug("username: {} is trying to login", username);
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username: " + username + " does not exist");
		}
		
		logger.debug("found user {} ({} {})", user.getUsername(), user.getFirstName(), user.getLastName());
		
		final Set<Role> roles = userService.getRoles(user);
		
		logger.debug("user {} has roles: {}", username, roles);
		
		final Collection<? extends GrantedAuthority> authorities = 
				roles.stream().map( (final Role v) -> {
					return new SimpleGrantedAuthority(String.format("ROLE_%s", v.getSlug()));
				}).collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}
}
