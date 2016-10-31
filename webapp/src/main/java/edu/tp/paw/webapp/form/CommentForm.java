package edu.tp.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentForm {

	@NotEmpty(message = "{NotEmpty.CommentForm.content}")
	@Size(max = 300, message = "{Size.CommentForm.content}")
	private String content;
	
	@Min(0)
	@Max(10)
	private float rating;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	
	
	
	
}
