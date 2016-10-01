package edu.tp.paw.model.filter;

import java.util.Set;

import edu.tp.paw.model.Category;;

public class CategoryFilter {

	private final Set<Category> categories;
	
	/* package */ CategoryFilter(CategoryFilterBuilder builder) {
		this.categories = builder.getCategories();
	}
	
	public Set<Category> getCategories() {
		return categories;
	}
}
