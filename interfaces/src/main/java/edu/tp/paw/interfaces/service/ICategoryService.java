package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Category;

public interface ICategoryService {

	/**
	 * Creates a new category
	 * @param name The name of the new category
	 * @param parent The parent of the new category. A null parent means the category is a child of the root node
	 * @return The created category
	 */
	public Category create(String name, long parent);
	
	/**
	 * Finds category with certain id
	 * @param id The id of the category to be found
	 * @return The found category
	 */
	public Category findById(long id);
	
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
