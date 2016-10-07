package edu.tp.paw.model;

import java.util.Set;

public class User {

	private final long id;
	private final String firstName;
	private final String lastName;
	private final String username;
	private final String password;
	private final String email;
	
	private final Set<StoreItem> favourites;
	
	public User(UserBuilder builder) {
		this.id = builder.getId();
		this.firstName = builder.getFirstName();
		this.lastName = builder.getLastName();
		this.username = builder.getUsername();
		this.password = builder.getPassword();
		this.email = builder.getEmail();
		this.favourites = builder.getFavourites();
	}

	public long getId() {
		return id;
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

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public Set<StoreItem> getFavourites() {
		return favourites;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", favourites=" + favourites + "]";
	}
	
	

}
