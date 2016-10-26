package edu.tp.paw.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.sun.xml.internal.stream.Entity;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PagedResult;

@Repository
public class StoreItemHibernateDao implements IStoreItemDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public StoreItem findById(final long id) {
		return entityManager.find(StoreItem.class, id);
	}

	@Override
	public List<StoreItem> findMostSold(final int n) {
		final TypedQuery<StoreItem> query = entityManager.createQuery("from StoreItem as o order by o.sold", StoreItem.class);
		query.setMaxResults(n);
		return query.getResultList();
	}

	@Override
	public PagedResult<StoreItem> findByTerm(Filter filter) {
		final TypedQuery<StoreItem> query = entityManager.createQuery("from StoreItem as o order by o.sold", StoreItem.class);
		final List<StoreItem> items = query.getResultList();
		final PagedResult<StoreItem> results = new PagedResult<>();
		results.setCurrentPage(0);
		results.setNumberOfAvailableResults(items.size());
		results.setNumberOfTotalResults(items.size());
		results.setPageSize(20);
		results.setResults(items);
		return results;
	}

	@Override
	public List<StoreItem> findInCategories(List<Category> categories) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreItem create(StoreItemBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreItem> getUserItems(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateItem(StoreItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumberOfItems() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<StoreItem> getFavourites(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean itemExists(final long id) {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from StoreItem item where item.id=:id", Long.class);
		query.setParameter("id", id);
		return query.getSingleResult() == 1;
	}

	@Override
	public boolean itemExists(final StoreItem item) {
		return itemExists(item.getId());
	}

	@Override
	public boolean increaseSellCount(StoreItem item) {
		// TODO Auto-generated method stub
		return false;
	}

}
