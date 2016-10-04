package edu.tp.paw.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

@Service
public class CategoryService implements ICategoryService {
	
	private static final long ROOT_CATEGORY_ID = 0;
//	private static final String CATEGORY_PATH_SEPARATOR = "#";
	@Autowired
	private ICategoryDao categoryDao; 
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#create(java.lang.String, long)
	 */
	@Override
	public Category create(String name, long parent) {
		
		throw new UnsupportedOperationException("create is to be used with a builder");
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#create(java.lang.String, long)
	 */
	@Override
	public Category create(CategoryBuilder builder) {
		
		if (builder.getName() == null) {
			throw new IllegalArgumentException("name cant be null");
		}
		
		if (builder.getName().trim().length() == 0) {
			throw new IllegalArgumentException("name cant be empty");
		}
		
		if (!exists(builder.getParent())) {
			throw new IllegalArgumentException("invalid category");
		}
		
		Category category = categoryDao.create(builder);
		
		return category;
	}

	public boolean exists(long id) {
		return categoryDao.categoryExists(id);
	}
	
	/* (non-Javadoc)
	 * {@inheritDoc}
	 *
	 */
	@Override
	public Category findById(long id) {
		
		return categoryDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#findByIdWithTree(long)
	 */
	@Override
	public Category findByIdWithTree(long id) {
		
		Category category = categoryDao.findById(id);
		
		if (category == null) {
			return null;
		}
		
		List<Category> descendants = categoryDao.getDescendants(category);
		
		return assembleCategoryTree(category, descendants);
	}
	
	/**
	 * Checks if #{child} is a subcategory of #{assumedParent}
	 * @param assumedParent The assumed parent category
	 * @param child The assumed child category
	 * @return true if #{child} is a subcategory of #{assumedParent}, false otherwise
	 */
	private static boolean isChildOf(final Category assumedParent, final Category child) {
		
		if ( StringUtils.countOccurrencesOf(child.getPath().replace(assumedParent.getPath(), ""), "#") == 1) {
			return true;
		}
		return false;
	}
	
		
	/**
	 * Adds #{newCategory} to #{parentCategory} descendants
	 * @param parentCategory The parent category
	 * @param newCategory The descendant category
	 * @return true if #{newCategory} was added to #{parentCategory} descendants, false otherwise
	 */
	private boolean buildDescendantsTree(Category parentCategory, Category newCategory) {
		
		if (isChildOf(parentCategory, newCategory)) {
			
			parentCategory.addChild(newCategory);
			newCategory.setParent(parentCategory.getId());
			
			return true;
		}
		
		for (Category child : parentCategory.getChildren()) {
			
			if (buildDescendantsTree(child, newCategory)) {
				return true;
			}
		
		}
		
		return false;
		
	}
	
	/** Adds #{descendants} to {category} descendant tree
	 * @param category the parent category
	 * @param descendants the descendants categories
	 * @return the parent category
	 */
	private Category assembleCategoryTree(final Category category, final List<Category> descendants) {
		
		for (final Category currentCategory : descendants) {
			
			buildDescendantsTree(category, currentCategory);
			
		}
		
		return category;
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#getCategoryTree()
	 */
	@Override
	public List<Category> getCategoryTree() {
		
		final Category rootCategory = categoryDao.findById(ROOT_CATEGORY_ID);
		
		if (rootCategory == null) {
			throw new IllegalArgumentException("root category cant be null");
		}
		
		List<Category> descendants = categoryDao.getDescendants(rootCategory);
		
		assembleCategoryTree(rootCategory, descendants);
		
		return rootCategory.getChildren();
		
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#getCategories()
	 */
	@Override
	public List<Category> getMainCategories() {
		
		return categoryDao.getChildren(ROOT_CATEGORY_ID);
	}

	@Override
	public List<Category> getChildren(Category category) {
		
		return categoryDao.getChildren(category);
		
	}
	
	@Override
	public List<Category> getChildren(Set<Category> categories) {
		
		List<Category> _categories = new LinkedList<>();
		
		for (Category category : categories) {
			_categories.addAll(categoryDao.getChildren(category));
		}
		
		return _categories;
		
	}

	@Override
	public List<Category> getCategories() {
		
		return categoryDao.getAll();
	}
	
}
