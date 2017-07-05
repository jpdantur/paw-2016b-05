package edu.tp.paw.webapp.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

@XmlRootElement
public class CommentDTO {

	private Long id;
	private UserDTO user;
	private StoreItemDTO item;
	private String content;
	private Date created;
	private float rating;
	
	public CommentDTO() {
		
	}
	
	public CommentDTO(final Comment comment) {
		this.id = comment.getId();
		this.user = UserDTO.fromUser(comment.getUser());
		this.item = new StoreItemDTO(comment.getItem());
		this.content = comment.getContent();
		this.created = comment.getCreated();
		this.rating = comment.getRating();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public StoreItemDTO getItem() {
		return item;
	}
	public void setItem(StoreItemDTO item) {
		this.item = item;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	
	
}
