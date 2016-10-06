package edu.tp.paw.webapp.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;

@Component
public class SiglasUserDetailsService implements UserDetailsService {

	@Autowired
	IUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username: " + username + " does not exist");
		}
		
		final Collection<? extends GrantedAuthority> authorities = Arrays.asList(
				new SimpleGrantedAuthority("ROLE_USER"),
				new SimpleGrantedAuthority("ROLE_ADMIN")
		);
		
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}

	
	
}
