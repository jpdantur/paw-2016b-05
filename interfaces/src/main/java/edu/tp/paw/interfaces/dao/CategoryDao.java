package edu.tp.paw.interfaces.dao;

import com.sun.istack.internal.Nullable;

import edu.tp.paw.model.Category;

public interface CategoryDao {

	/**
	 * Creates a new category
	 * @param name The name of the new category
	 * @param parent The parent of the new category. A null parent means a child of the root node
	 * @return The created category
	 */
	public Category create(String name, @Nullable Category parent);
	
}
