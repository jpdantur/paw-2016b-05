package edu.tp.paw.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint( name = "user_email_uniq", columnNames = { "username", "email" } )
})
public class User {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq" )
	@SequenceGenerator( sequenceName = "users_user_id_seq", name = "users_user_id_seq", allocationSize = 1 )
	@Column( name =  "user_id")
	private Long id;
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "favourites",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn( name = "store_item_id"),
			foreignKey = @ForeignKey( name = "user_fk" ),
			inverseForeignKey = @ForeignKey( name = "store_item_fk" ),
			uniqueConstraints = {
				@UniqueConstraint( name = "favourite_uniq", columnNames = { "user_id", "store_item_id" })
			}
	)
	@Fetch(FetchMode.SELECT)
	private Set<StoreItem> favourites = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn( name = "role_id"),
			foreignKey = @ForeignKey( name = "user_fk" ),
			inverseForeignKey = @ForeignKey( name = "role_fk" ),
			uniqueConstraints = {
				@UniqueConstraint( name = "user_roles_uniq",  columnNames = { "user_id", "role_id" })
			}
	)
	@Fetch(FetchMode.SELECT)
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
	@Fetch(FetchMode.SELECT)
	private Set<StoreItem> publishedItems = new HashSet<>();
	
	/* package */ User() {
		// hibernate, duh!
	}
	
	public User(final UserBuilder builder) {
		this.id = builder.getId();
		this.firstName = builder.getFirstName();
		this.lastName = builder.getLastName();
		this.username = builder.getUsername();
		this.password = builder.getPassword();
		this.email = builder.getEmail();
		this.favourites = builder.getFavourites();
		this.roles = builder.getRoles();
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
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public Set<StoreItem> getPublishedItems() {
		return publishedItems;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", favourites=" + favourites + ", roles="
				+ roles + "]";
	}

	
	
	

}
