package edu.tp.paw.webapp.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentForm {

	@NotEmpty
	@Size(max = 300)
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
