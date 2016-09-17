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
	
	public Category findById(long id);
	
	public List<Category> getDescendants(Category category);
	
}
