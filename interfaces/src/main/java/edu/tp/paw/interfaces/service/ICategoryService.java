package edu.tp.paw.interfaces.service;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

public interface ICategoryService {

	/**
	 * Finds category with certain id
	 * @param id The id of the category to be found
	 * @return The found category
	 */
	public Category findById(final long id);
	/**
	 * Finds category with certain id and builds tree with descendants
	 * @param id The id of the category to be found
	 * @return The found category
	 */
	public Category findByIdWithTree(final long id);
	
	public boolean exists(final long id);
	public boolean exists(final Category category);
	
	/**
	 * Creates a new category
	 * @param name The name of the new category
	 * @param parent The parent of the new category. A null parent means the category is a child of the root node
	 * @return The created category
	 */
	public Category create(final CategoryBuilder builder);
	
	public List<Category> getChildren(final Category category);
	public List<Category> getChildren(final Set<Category> categories);
	
	
	
	/**
	 * @return List with Category Trees
	 */
	public Set<Category> getCategoryTree();
	
	/**
	 * @return List with all Categories
	 */
	public List<Category> getCategories();
	
	public List<Category> getMainCategories();
	
	public int getNumberOfCategories();
	
	public boolean updateCategory(final Category category);
	
}
