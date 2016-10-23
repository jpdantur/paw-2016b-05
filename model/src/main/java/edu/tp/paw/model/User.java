package edu.tp.paw.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq" )
	@SequenceGenerator( sequenceName = "users_user_id_seq", name = "users_user_id_seq", allocationSize = 1 )
	@Column( name =  "user_id")
	private long id;
	@Column( name = "first_name", length = 100)
	private String firstName;
	@Column( name = "last_name", length = 100)
	private String lastName;
	@Column(length = 100)
	private String username;
	@Column(length = 100)
	private String password;
	@Column(length = 100)
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
	private Set<StoreItem> favourites;
	
	/* package */ User() {
		// hibernate, duh!
	}
	
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
