package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class CommentBuilder implements IBuilder<Comment> {
	
	private final User user;
	private final String content;
	private final float rating;
	
	private Long id;
	private StoreItem item;
	private Date created = new Date();

	public CommentBuilder(final User user, final String content, final float rating) {
		super();
		this.user = Objects.requireNonNull(user);
		this.content = Objects.requireNonNull(content);
		this.rating = rating;
	}

	public CommentBuilder created(final Timestamp created) {
		this.created = Objects.requireNonNull(created);
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

	public Date getCreated() {
		return created;
	}
	
	public float getRating() {
		return rating;
	}

	@Override
	public Comment build() {
		
		Objects.requireNonNull(created);
		Objects.requireNonNull(content);
		Objects.requireNonNull(user);
		
		if (content.length() == 0) {
			throw new IllegalStateException("content cant be empty");
		}
		
		if (rating < 0 || rating > 5) {
			throw new IllegalStateException("rating has to be between 0 and 5");
		}
		
		return new Comment(this);
	}
	
	
	
	
}
