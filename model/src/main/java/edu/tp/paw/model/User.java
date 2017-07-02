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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	
	@Column
	private String refreshToken;
	
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "user" )
	private Set<Favourite> favourites = new HashSet<>();
	
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
	@Fetch(FetchMode.SELECT)
	private Set<Purchase> purchases = new HashSet<>();
	
//	@Formula("(select round(coalesce(avg(s.rating),0)::numeric,1) from sales s where s.user_id=user_id)")
	@Transient
	private float buyerRating = 2.5f;
	
//	@Formula("(select round(coalesce(avg(sr.rating),0)::numeric,1) from comments sr where sr.item_id=item_id)");
//	private float sellerRating;
	
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
		this.refreshToken = builder.getRefreshToken();
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

	public Set<Favourite> getFavourites() {
		return favourites;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public Set<StoreItem> getPublishedItems() {
		return publishedItems;
	}
	
	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public float getBuyerRating() {
		return buyerRating;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", username=" + username + ", password=" + password
				+ ", email=" + email + "]";
	}

	
	
	

}
