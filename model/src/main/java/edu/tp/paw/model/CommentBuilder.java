package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;

public class CommentBuilder implements IBuilder<Comment> {
	
	private final User user;
	private final String content;
	private final float rating;
	
	private Long id;
	private StoreItem item;
	private Timestamp created = new Timestamp( (new Date()).getTime() );

	public CommentBuilder(final User user, final String content, final float rating) {
		super();
		this.user = user;
		this.content = content;
		this.rating = rating;
	}

	public CommentBuilder created(final Timestamp created) {
		this.created = created;
		return this;
	}
	
	public CommentBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	public CommentBuilder item(final StoreItem item) {
		this.item = item;
		return this;
	}
	
	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public StoreItem getItem() {
		return item;
	}

	public String getContent() {
		return content;
	}

	public Timestamp getCreated() {
		return created;
	}
	
	public float getRating() {
		return rating;
	}

	@Override
	public Comment build() {
		return new Comment(this);
	}
	
	
	
	
}
