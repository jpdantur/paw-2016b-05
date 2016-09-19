package edu.tp.paw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.model.Category;

@Service
public class CategoryService implements ICategoryService {

	private static final long ROOT_CATEGORY_ID = 0;
	private static final String CATEGORY_PATH_SEPARATOR = "#";
	@Autowired
	private ICategoryDao categoryDao; 
	
	@Override
	public Category create(String name, long parent) {
		
		if (name == null) {
			throw new IllegalArgumentException("name cant be null");
		}
		
		if (name.trim().length() == 0) {
			throw new IllegalArgumentException("name cant be empty");
		}
		
		if (parent < ROOT_CATEGORY_ID) {
			throw new IllegalArgumentException("invalid category");
		}
		
		Category parentCategory = null;
		
		if (parent != ROOT_CATEGORY_ID) {
			parentCategory = findById(parent);
			
			if (parentCategory == null) {
				throw new IllegalArgumentException("category does not exist");
			}
		}
		
		Category category = categoryDao.create(name, parent);
		
		String categoryPath = Long.toString(category.getId());
		
		if (parentCategory != null) {
			categoryPath = parentCategory.getPath()+CATEGORY_PATH_SEPARATOR+Long.toString(category.getId());
		}
		
		category = categoryDao.updatePath(category, categoryPath);
		
		return category;
	}

	@Override
	public Category findById(long id) {
		
		if (id < ROOT_CATEGORY_ID) {
			throw new IllegalArgumentException("id cant be negative");
		}
		
		return categoryDao.findById(id);
	}

	@Override
	public Category findByIdWithTree(long id) {
		
		Category category = categoryDao.findById(id);
		
		if (category == null) {
			return null;
		}
		
		List<Category> descendants = categoryDao.getDescendants(category);
		
		return assembleCategoryTree(category, descendants);
	}

//	private static boolean isSubcategoryOf(final Category assumedParent, final Category child) {
//		return child.getPath().startsWith(assumedParent.getPath());
//	}
	
	private static boolean isChildOf(final Category assumedParent, final Category child) {
		
		if ( StringUtils.countOccurrencesOf(child.getPath().replace(assumedParent.getPath(), ""), "#") == 1) {
			return true;
		}
		return false;
	}
	
		
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
	
	private Category assembleCategoryTree(final Category category, final List<Category> descendants) {
		
		for (Category currentCategory : descendants) {
			
			buildDescendantsTree(category, currentCategory);
			
		}
		
		return category;
	}

	@Override
	public List<Category> getCategoryTree() {
		
		List<Category> mainCategories = categoryDao.getChildren(ROOT_CATEGORY_ID);
		
		for (Category category : mainCategories) {
			
			List<Category> descendants = categoryDao.getDescendants(category);
			
			assembleCategoryTree(category, descendants);
			
		}
		
		return mainCategories;
	}

	@Override
	public List<Category> getCategories() {
		
		return categoryDao.getChildren(ROOT_CATEGORY_ID);
	}
	
}
