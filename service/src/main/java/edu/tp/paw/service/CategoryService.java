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
		
		Category parentCategory = null;
		
		if (parent != ROOT_CATEGORY_ID) {
			parentCategory = findById(parent);
			
			if (parentCategory == null) {
				// throw Exception?
				return null;
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
		
		return categoryDao.findById(id);
	}

	@Override
	public Category findByIdWithTree(long id) {
		
		Category category = categoryDao.findById(id);
		
		if (category == null) {
			return null;
		}
		
		List<Category> descendants = categoryDao.getDescendants(category);
		
		System.out.println("descendants");
		System.out.println(descendants);
		
		return assembleCategoryTree(category, descendants);
	}

//	private static boolean isSubcategoryOf(final Category assumedParent, final Category child) {
//		return child.getPath().startsWith(assumedParent.getPath());
//	}
	
	private static boolean isChildOf(final Category assumedParent, final Category child) {
		
		System.out.println("service:: parent path = " + assumedParent.getPath());
		System.out.println("service:: child path = " + child.getPath());
		
		String replaceResult = child.getPath().replace(assumedParent.getPath(), "");
		
		System.out.println("service:: replaceResult = " + replaceResult);
		
		int ocurrences = StringUtils.countOccurrencesOf(replaceResult, "#");
		
		System.out.println("service:: ocurrences = " + ocurrences);
		
		if ( ocurrences == 1) {
			System.out.println("service:: is child");
			return true;
		}
		System.out.println("service:: is not child");
		return false;
	}
	
		
	private boolean buildDescendantsTree(Category parentCategory, Category newCategory) {
//		if (category == null) {
//			return;
//		}
		
		System.out.println("service:: checking if " + newCategory.getId() + " is child of " + parentCategory.getId());
		
		if (isChildOf(parentCategory, newCategory)) {
			
			System.out.println("service:: " + newCategory.getId() + " is child of " + parentCategory.getId());
			
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
	
//		System.out.println("service:: assembling category tree");
		
		for (Category currentCategory : descendants) {
			
//			System.out.println("service:: assembling category for id = "
//					+ currentCategory.getId() + " name = " + currentCategory.getName());
			
			buildDescendantsTree(category, currentCategory);
			
		}
		
		
//		Category currentParent = category;
//		
//		for (Category currentCategory : descendants) {
//			
//			if ( isSubcategoryOf(currentParent, currentCategory) ) {
//				currentCategory.setParent(currentParent);
//				currentParent.addChild(currentCategory);
//				
//				currentParent = currentCategory;
//				
//			} else {
//				
//				Category cat = category.getParent();
//				
//				while (cat != null) {
//					
//					if ( isSubcategoryOf(cat, currentCategory) ) {
//						
//						cat.addChild(currentCategory);
//						currentCategory.setParent(cat);
//						
//						currentParent = cat;
//						
//						cat = null; // break;
//					}
//					
//					cat = cat.getParent();
//				}
//			}
//		}
		
		
		return category;
	}

	@Override
	public List<Category> getCategoryTree() {
		
		List<Category> mainCategories = categoryDao.getSiblings(ROOT_CATEGORY_ID);
		
		for (Category category : mainCategories) {
			
			List<Category> descendants = categoryDao.getDescendants(category);
			
			assembleCategoryTree(category, descendants);
			
		}
		
		return mainCategories;
	}
	
}
