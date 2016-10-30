package edu.tp.paw.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

@Repository
public class CategoryHibernateDao implements ICategoryDao {

	private static final Logger logger = LoggerFactory.getLogger(CategoryHibernateDao.class);
	
	private static final String SQL_DESCENDANTS_OF =
			"with recursive tree as "
			+ "( "
			+ "select category_id, category_name, created, last_updated, parent, (0 || '#' || cast (category_id as text)) as category_path "
			+ "from store_categories "
			+ "where category_id <> :id1 and parent = :id2 "
			+ "union all "
				+ "select c.category_id, c.category_name, c.created, c.last_updated, c.parent, (c2.category_path || '#' || cast (c.category_id as text)) as category_path "
				+ "from store_categories as c "
				+ "inner join tree as c2 on (c.parent=c2.category_id) where c.category_id <> 0 "
			+ ") select category_id from tree order by category_name";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Category create(final CategoryBuilder builder) {
		final Category category = builder.build();
		logger.debug("persisting {}", category);
		entityManager.persist(category);
		return category;
	}

	@Override
	public Category findById(final long id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	public boolean categoryExists(final long id) {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from Category c where c.id=:id", Long.class);
		query.setParameter("id", id);
		return query.getSingleResult() == 1;
	}

	@Override
	public List<Category> getSiblings(final Category category) {
		final TypedQuery<Category> query = entityManager.createQuery("from Category c where c.parent=:parent order by c.name", Category.class);
		query.setParameter("parent", category.getParent());
		return query.getResultList();
	}

	@Override
	public List<Category> getChildren(final long categoryId) {
		final TypedQuery<Category> query = entityManager.createQuery("from Category c where c.parent.id=:id order by c.name", Category.class);
		query.setParameter("id", categoryId);
		return query.getResultList();
	}

	@Override
	public List<Category> getChildren(final Category category) {
		return getChildren(category.getId());
	}

	@Override
	public List<Category> getDescendants(final Category category) {
		
		logger.trace("fetching descendants");
		
		final Query query = entityManager.createNativeQuery(SQL_DESCENDANTS_OF);
		
		logger.trace("running {}", SQL_DESCENDANTS_OF);
		
		query.setParameter("id1", category.getId());
		query.setParameter("id2", category.getId());
		
		@SuppressWarnings("unchecked")
		final List<Integer> categoriesIds = query.getResultList();
		
		logger.trace("native query returned {}", categoriesIds);
		
		if (categoriesIds.isEmpty()) {
			return new ArrayList<Category>();
		}
		
		final TypedQuery<Category> typedQuery = entityManager.createQuery("from Category c where c.id in (:ids) order by c.name", Category.class);
		
		typedQuery.setParameter("ids", categoriesIds.stream().map(Integer::longValue).collect(Collectors.toList()));
		
		return typedQuery.getResultList();
	}

	@Override
	public List<Category> getAll() {
		final TypedQuery<Category> query = entityManager.createQuery("from Category c where c.id<>0 order by c.name", Category.class);
		return query.getResultList();
	}

	@Override
	public int getNumberOfCategories() {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from Category c", Long.class);
		return query.getSingleResult().intValue();
	}

	@Override
	public boolean updateCategory(final Category category) {
		
		entityManager.merge(category);
		
		return true;
	}

}
