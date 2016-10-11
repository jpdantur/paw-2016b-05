package edu.tp.paw.interfaces.dao;

import java.util.List;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

public interface ICategoryDao {
	
	public Category create(final CategoryBuilder builder);
	
	/**
	 * Finds category with certain id
	 * @param id The id of the category to be found
	 * @return The found category
	 */
	public Category findById(final long id);
	
	/**
	 * Checks if certain category exists
	 * @param id The id of the category to check
	 * @return true if it exists, false if it doesnt
	 */
	public boolean categoryExists(final long id);
	
	/**
	 * Gets given category's siblings
	 * @param category
	 * @return List with category's siblings
	 */
	public List<Category> getSiblings(final Category category);
	
	/**
	 * Finds category children by id
	 * @param categoryId id of the category
	 * @return List with category children
	 */
	public List<Category> getChildren(final long categoryId);
	
	/**
	 * Finds category children
	 * @param category
	 * @return List with category children
	 */
	public List<Category> getChildren(final Category category);
	
	/**
	 * Finds category descendants
	 * @param category
	 * @return List with category descendants
	 */
	public List<Category> getDescendants(final Category category);
	
	public List<Category> getAll();
	
	public int getNumberOfCategories();
	
	public boolean updateCategory(final Category category);
	
}
