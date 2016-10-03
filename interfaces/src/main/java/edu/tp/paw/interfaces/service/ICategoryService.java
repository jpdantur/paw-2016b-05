package edu.tp.paw.interfaces.service;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

public interface ICategoryService {

	/**
	 * Creates a new category
	 * @param name The name of the new category
	 * @param parent The parent of the new category. A null parent means the category is a child of the root node
	 * @return The created category
	 * @deprecated
	 */
	public Category create(String name, long parent);
	
	public Category create(CategoryBuilder builder);
	
	/**
	 * Finds category with certain id
	 * @param id The id of the category to be found
	 * @return The found category
	 */
	public Category findById(long id);
	
	public boolean exists(long id);
	
	public List<Category> getChildren(Category category);
	public List<Category> getChildren(Set<Category> categories);
	
	/**
	 * Finds category with certain id and builds tree with descendants
	 * @param id The id of the category to be found
	 * @return The found category
	 */
	public Category findByIdWithTree(long id);
	
	/**
	 * @return List with Category Trees
	 */
	public List<Category> getCategoryTree();
	
	/**
	 * @return List with all Categories
	 */
	public List<Category> getCategories();
	
}
