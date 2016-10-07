package edu.tp.paw.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserBuilder implements IBuilder<User> {

	private final String firstName;
	private final String lastName;
	private final String username;
	private final String email;
	
	private long id;
	private String password;
	private Set<StoreItem> favourites = new HashSet<>();
	
	public UserBuilder(final String firstName, final String lastName, final String username, final String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
	}
	
	public UserBuilder password(final String password) {
		this.password = password;
		return this;
	}
	
	public UserBuilder favourites(final Collection<StoreItem> collection) {
		this.favourites.addAll(collection);
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

	public Set<StoreItem> getFavourites() {
		return favourites;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public User build() {
		return new User(this);
	}

	
	
}
