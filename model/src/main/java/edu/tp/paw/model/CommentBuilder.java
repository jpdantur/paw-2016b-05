package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;

public class CommentBuilder implements IBuilder<Comment> {
	
	private final User user;
	private final String content;
	
	private long id;
	private StoreItem item;
	private Timestamp timestamp = new Timestamp( (new Date()).getTime() );

	public CommentBuilder(final User user, final String content) {
		super();
		this.user = user;
		this.content = content;
	}

	public CommentBuilder timestamp(final Timestamp timestamp) {
		this.timestamp = timestamp;
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
	
	public long getId() {
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	@Override
	public Comment build() {
		return new Comment(this);
	}
	
	
	
	
}
