package edu.tp.paw.model;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "comments")
public class Comment {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "comments_comment_id_seq" )
	@SequenceGenerator( sequenceName = "comments_comment_id_seq", name = "comments_comment_id_seq", allocationSize = 1 )
	@Column( name =  "comment_id")
	private long id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	@JoinColumn(name = "item_id")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private StoreItem item;
	@Column(length = 300, nullable = false, name = "comment_content")
	private String content;
	@Column(insertable = false, updatable = false, nullable = false)
	private Timestamp created;
	
	/* package */ Comment() {
		// hibernate, duh!
	}
	
	public Comment(final CommentBuilder builder) {
		this.id = builder.getId();
		this.user = builder.getUser();
		this.item = builder.getItem();
		this.content = builder.getContent();
		this.created = builder.getCreated();
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
	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + "]";
	}
	
	
	
}
