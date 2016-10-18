package edu.tp.paw.webapp.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentForm {

	@NotEmpty(message = "{NotEmpty.CommentForm.content}")
	@Size(max = 300, message = "{Size.CommentForm.content}")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
