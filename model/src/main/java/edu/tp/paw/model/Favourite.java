package edu.tp.paw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "favourites" )
public class Favourite {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "favourites_favourite_id_seq" )
	@SequenceGenerator( sequenceName = "favourites_favourite_id_seq", name = "favourites_favourite_id_seq", allocationSize = 1 )
	@Column( name =  "favourite_id")
	private Long id;
	
	@Column( name = "created", insertable = false, updatable = false )
	private Date created;
	
	@ManyToOne( fetch = FetchType.EAGER, optional = false)
	@JoinColumn( name = "item_id", foreignKey = @ForeignKey( name = "item_fk" ) )
	private StoreItem item;
	
	@ManyToOne( fetch = FetchType.EAGER, optional = false)
	@JoinColumn( name = "user_id", foreignKey = @ForeignKey( name = "user_fk" ) )
	private User user;
	
	/* package */ Favourite() {
	}
	
	public Favourite(final FavouriteBuilder builder) {
		id = builder.getId();
		created = builder.getCreated();
		item = builder.getItem();
		user = builder.getUser();
	}

	public Long getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public StoreItem getItem() {
		return item;
	}

	public User getUser() {
		return user;
	}
	
}
