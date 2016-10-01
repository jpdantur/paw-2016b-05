package edu.tp.paw.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.IBuilder;

public class CategoryFilterBuilder implements IBuilder<CategoryFilter> {

	private FilterBuilder filterBuilder;
	
	private final Set<Category> categories = new HashSet<>();
	
	public CategoryFilterBuilder(FilterBuilder filterBuilder) {
		this.filterBuilder = filterBuilder;
	}
	
	
	public CategoryFilterBuilder in(Category category) {
		categories.add(category);
		return this;
	}
	
	public CategoryFilterBuilder in(Category... categories) {
		for (Category category : categories) {
			this.categories.add(category);
		}
		return this;
	}
	
	public CategoryFilterBuilder in(final List<Category> categories) {
		for (Category category : categories) {
			this.categories.add(category);
		}
		return this;
	}
	
	public FilterBuilder and() {
		return filterBuilder;
	}
	
	public FilterBuilder end() {
		return filterBuilder;
	}
	
	public Set<Category> getCategories() {
		return categories;
	}
	
	public CategoryFilter build() {
		return new CategoryFilter(this);
	}
}
