package edu.tp.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RoleForm {

	@Size(min = 2, max = 100)
	private String name;
	@Size(min = 2, max = 100)
	@Pattern(regexp = "[a-zA-Z0-9_-]+")
	private String slug;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
}
