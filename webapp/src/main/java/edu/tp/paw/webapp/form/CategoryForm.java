package edu.tp.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CategoryForm {
	
	@Min(0)
	private int parent;
	
	//@NotEmpty(message = "NotEmpty.CategoryForm.name")
	@Size(min=4, max=100, message = "{Size.CategoryForm.name}")
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
