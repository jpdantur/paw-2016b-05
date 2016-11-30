package edu.tp.paw.model;

import java.util.Date;
import java.util.Objects;

public class FavouriteBuilder implements IBuilder<Favourite> {

	private Long id;
	
	private Date created = new Date();
	
	private final StoreItem item;
	private final User user;
	
	public FavouriteBuilder(final StoreItem item, final User user) {
		this.item = Objects.requireNonNull(item);
		this.user = Objects.requireNonNull(user);
	}
	
	public FavouriteBuilder created(final Date created) {
		this.created = Objects.requireNonNull(created);
		return this;
	}
	
	public FavouriteBuilder id(final long id) {
		this.id = id;
		return this;
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

	@Override
	public Favourite build() {
		
		Objects.requireNonNull(item);
		Objects.requireNonNull(created);
		Objects.requireNonNull(user);
		
		return new Favourite(this);
	}
	
}
