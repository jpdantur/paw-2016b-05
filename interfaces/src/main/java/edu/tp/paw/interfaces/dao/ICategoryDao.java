package edu.tp.paw.interfaces.dao;

import java.util.List;
import java.util.Map;

import com.sun.istack.internal.Nullable;

import edu.tp.paw.model.Category;

public interface ICategoryDao {

	/**
	 * Creates a new category
	 * @param name The name of the new category
	 * @param parent The parent of the new category. A null parent means a child of the root node
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
	 * Checks if certain category exists
	 * @param id The id of the category to check
	 * @return true if it exists, false if it doesnt
	 */
	public boolean categoryExists(long id);
	
	/**
	 * Updates given category's path
	 * @param category The category to be updated
	 * @param path The new path
	 * @return The updated category
	 */
	public Category updatePath(Category category, String path);
	
	/**
	 * Gets given category's siblings
	 * @param category
	 * @return List with category's siblings
	 */
	public List<Category> getSiblings(Category category);
	
	/**
	 * Finds category children by id
	 * @param categoryId id of the category
	 * @return List with category children
	 */
	public List<Category> getChildren(long categoryId);
	
	/**
	 * Finds category children
	 * @param category
	 * @return List with category children
	 */
	public List<Category> getChildren(Category category);
	
	/**
	 * Finds category descendants
	 * @param category
	 * @return List with category descendants
	 */
	public List<Category> getDescendants(Category category);
	
}
