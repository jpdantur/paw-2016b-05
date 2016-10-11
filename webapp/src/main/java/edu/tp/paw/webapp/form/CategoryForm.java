package edu.tp.paw.webapp.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CategoryForm {
	
	private int parent;
	
	@NotEmpty
	@Size(max=100)
	private String name;

	public int getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return "CreateCategoryForm [parent=" + parent + ", name=" + name + "]";
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}