package edu.tp.paw.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

@Service
@Transactional
public class CategoryService implements ICategoryService {
	
	private final static Logger logger = LoggerFactory.getLogger(CategoryService.class);
	
	private static final long ROOT_CATEGORY_ID = 0;
	
	@Autowired private ICategoryDao categoryDao;
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#create(java.lang.String, long)
	 */
	@Override
	public Category create(final CategoryBuilder builder) {
		
		if (builder.getName() == null) {
			throw new IllegalArgumentException("name cant be null");
		}
		
		if (builder.getName().trim().length() == 0) {
			throw new IllegalArgumentException("name cant be empty");
		}
		
		if (!exists(builder.getParent())) {
			throw new IllegalArgumentException("invalid category, parent does not exist");
		}
		
		final Category category = categoryDao.create(builder);
		
		return category;
	}

	public boolean exists(final long id) {
		return categoryDao.categoryExists(id);
	}
	
	@Override
	public boolean exists(final Category category) {
		if (category == null) {
			throw new IllegalArgumentException("category cant be null");
		}
		return exists(category.getId());
	}
	
	/* (non-Javadoc)
	 * {@inheritDoc}
	 *
	 */
	@Override
	public Category findById(final long id) {
		
		return categoryDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#findByIdWithTree(long)
	 */
	@Override
	public Category findByIdWithTree(final long id) {
		
		if (!exists(id)) {
			throw new IllegalArgumentException("category must exist");
		}
		
		final Category parentCategory = categoryDao.findById(id);
		
		final List<Category> allDescendants = categoryDao.getDescendants(parentCategory);
		
		// hibernate magic
		allDescendants.stream().forEach(v -> v.getChildren().iterator());
		parentCategory.getChildren().iterator();
		
		logger.trace("parent category is {}", parentCategory);
		
		return parentCategory;
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#getCategoryTree()
	 */
	@Override
	public Set<Category> getCategoryTree() {
		
		final Category rootCategory = categoryDao.findById(ROOT_CATEGORY_ID);
		
		if (rootCategory == null) {
			throw new IllegalArgumentException("root category cant be null");
		}
		
		final List<Category> allDescendants = categoryDao.getDescendants(rootCategory);
		
		// hibernate magic
		allDescendants.stream().forEach(v -> v.getChildren().iterator());
		rootCategory.getChildren().iterator();
		
		rootCategory.getChildren().remove(rootCategory);
		
		logger.trace("root category is: {}", rootCategory);
		
		return rootCategory.getChildren();
		
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.ICategoryService#getCategories()
	 */
	@Override
	public Set<Category> getMainCategories() {
		
		final Category rootCategory = categoryDao.findById(ROOT_CATEGORY_ID);
//		final List<Category> children = categoryDao.getChildren(rootCategory); 
		
		// hibernate magic
		rootCategory.getChildren().iterator();
		rootCategory.getChildren().remove(rootCategory);
		
		return rootCategory.getChildren();
	}

	@Override
	public List<Category> getChildren(final Category category) {
		
		if (category == null) {
			throw new IllegalArgumentException("category cant be null");
		}
		
		if (!exists(category)) {
			throw new IllegalArgumentException("category must exists");
		}
		
		return categoryDao.getChildren(category);
		
	}
	
	@Override
	public Set<Category> getChildren(final Set<Category> categories) {
		
		if (categories == null) {
			throw new IllegalArgumentException("categories cant be null");
		}
		
		final Set<Category> _categories = new HashSet<>();
		
		for (final Category category : categories) {
			_categories.addAll(categoryDao.getChildren(category));
		}
		
		return _categories;
		
	}

	@Override
	public List<Category> getCategories() {
		
		return categoryDao.getAll();
	}

	@Override
	public int getNumberOfCategories() {
		return categoryDao.getNumberOfCategories();
	}

	@Override
	public boolean updateCategory(final Category category) {
		
		if (category == null) {
			throw new IllegalArgumentException("category cant be null");
		}
		
		if (!exists(category.getId())) {
			throw new IllegalArgumentException("category must exist");
		}
		
		return categoryDao.updateCategory(category);
	}
	
}
