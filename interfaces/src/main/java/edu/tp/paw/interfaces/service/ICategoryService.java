package edu.tp.paw.interfaces.service;

import com.sun.istack.internal.Nullable;

import edu.tp.paw.model.Category;

public interface ICategoryService {

	/**
	 * Creates a new category
	 * @param name The name of the new category
	 * @param parent The parent of the new category. A null parent means the category is a child of the root node
	 * @return The created category
	 */
	public Category create(String name, int parent);
	
}
