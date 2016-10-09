package edu.tp.paw.model;

import java.sql.Timestamp;

public class Comment {

	private final long id;
	private final User user;
	private final StoreItem item;
	private final String content;
	private final Timestamp timestamp;
	
	public Comment(final CommentBuilder builder) {
		this.id = builder.getId();
		this.user = builder.getUser();
		this.item = builder.getItem();
		this.content = builder.getContent();
		this.timestamp = builder.getTimestamp();
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
	public long getId() {
		return id;
	}
	
	
	
}
