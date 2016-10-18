package edu.tp.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RoleForm {

	@Size(min = 2, max = 100, message = "{Size.RoleForm.name}")
	private String name;
	@Size(min = 2, max = 100, message = "{Size.RoleForm.slug}")
	@Pattern(regexp = "[A-Z0-9_-]+", message = "{Pattern.RoleForm.slug}")
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
