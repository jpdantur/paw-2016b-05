package edu.tp.paw.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserBuilder implements IBuilder<User> {

	private final String username;
	
	private String firstName;
	private String lastName;
	private String email;
	
	private long id;
	private String password;
	private Set<StoreItem> favourites = new HashSet<>();
	
	public UserBuilder(final String username) {
		this.username = username;
	}
	
	public UserBuilder firstName(final String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public UserBuilder lastName(final String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public UserBuilder email(final String email) {
		this.email= email;
		return this;
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
