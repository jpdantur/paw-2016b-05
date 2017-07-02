package edu.tp.paw.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class UserBuilder implements IBuilder<User> {

	private final String username;
	
	private String firstName;
	private String lastName;
	private String email;
	private String refreshToken;
	
	private Long id;
	private String password;
	private Set<Favourite> favourites = new HashSet<>();
	private Set<Role> roles = new HashSet<>();
	
	public UserBuilder(final User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.favourites = user.getFavourites();
		this.password = user.getPassword();
		this.id = user.getId();
		this.roles = user.getRoles();
		this.refreshToken = user.getRefreshToken();
	}
	
	public UserBuilder(final String username) {
		this.username = Objects.requireNonNull(username);
	}
	
	public UserBuilder firstName(final String firstName) {
		this.firstName = Objects.requireNonNull(firstName);
		return this;
	}
	
	public UserBuilder lastName(final String lastName) {
		this.lastName = Objects.requireNonNull(lastName);
		return this;
	}
	
	public UserBuilder email(final String email) {
		this.email= Objects.requireNonNull(email);
		return this;
	}
	
	public UserBuilder password(final String password) {
		this.password = Objects.requireNonNull(password);
		return this;
	}
	
	public UserBuilder refreshToken(final String refreshToken) {
		this.refreshToken = Objects.requireNonNull(refreshToken);
		return this;
	}
	
	public UserBuilder favourite(final Favourite favourite) {
		this.favourites.add(Objects.requireNonNull(favourite));
		return this;
	}
	
	public UserBuilder favourites(final Collection<Favourite> favourites) {
		for (final Favourite f : favourites) {
			favourite(f);
		}
		return this;
	}
	
	public UserBuilder role(final Role role) {
		this.roles.add(Objects.requireNonNull(role));
		return this;
	}
	
	public UserBuilder roles(final Collection<Role> roles) {
		for (final Role r : roles) {
			role(r);
		}
		return this;
	}
	
	public UserBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Set<Favourite> getFavourites() {
		return favourites;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	@Override
	public User build() {
		
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(lastName);
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		
		if (firstName.length() < 2 || firstName.length() > 100) {
			throw new IllegalStateException("first name should be between 2 and 100 chars");
		}
		
		if (lastName.length() < 2 || lastName.length() > 100) {
			throw new IllegalStateException("last name should be between 2 and 100 chars");
		}
		
		if (username.length() < 6 || username.length() > 100 || !Pattern.matches("[a-zA-Z0-9_-]+", username)) {
			throw new IllegalStateException("username should be between 6 and 100 chars and match [a-zA-Z0-9_-]+");
		}
		
		if (password.length() < 4 || password.length() > 100) {
			throw new IllegalStateException("password should be between 4 and 100 chars");
		}
		
		return new User(this);
	}

	
	
}
