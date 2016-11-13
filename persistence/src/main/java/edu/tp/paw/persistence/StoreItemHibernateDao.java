package edu.tp.paw.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.CategoryFilter;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.OrderFilter;
import edu.tp.paw.model.filter.PageFilter;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.PriceFilter;
import edu.tp.paw.model.filter.Range;
import edu.tp.paw.model.filter.StoreItemStatusFilter;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;
import edu.tp.paw.model.filter.TermFilter;

@Repository
public class StoreItemHibernateDao implements IStoreItemDao {

	private final static Logger logger = LoggerFactory.getLogger(StoreItemHibernateDao.class);
	
//	private static final String ORDER_DESCENDING = "DESC";	
//	private static final String ORDER_ASCENDING = "ASC";
	
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

	private static String buildQueryFromFilter(final Filter filter) {
		
		final StringBuilder query = new StringBuilder();
		
		query.append("from StoreItem item where status=:status ");
		
		final TermFilter termFilter = filter.getTermFilter();
		
		if (termFilter.getTerm().isPresent()) {

			query.append(
				"and ("
					+ "lower(item.name) like concat('%', lower(:term), '%') "
					+ "or "
					+ "lower(item.description) like concat('%', lower(:term), '%') "
					+ "or "
					+ "category in ("
						+ "select c.id from Category c "
						+ "where lower(c.name) like concat('%', lower(:term), '%') "
					+ ")"
				+") ");
		}
		
		final PriceFilter priceFilter = filter.getPriceFilter();
		final Range<BigDecimal> priceRange = priceFilter.getPriceRange();
		
		if (priceRange.hasLowerBound() || priceRange.hasUpperBound()) {
			
			if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
				query.append("and price <= :upperBound and :lowerBound <= price");
			} else if (priceRange.hasLowerBound()) {
				query.append("and :lowerBound <= price");
			} else {
				query.append("and price <= :upperBound");
			}
		}
		
		final CategoryFilter categoryFilter = filter.getCategoryFilter();
		final Set<Category> categories = categoryFilter.getCategories();

		if (!categories.isEmpty()) {
			query.append(" and item.category in (:categories) ");			
		}
		
		final OrderFilter orderFilter = filter.getOrderFilter();
		
		query.append(String.format(" order by item.%s ", orderFilter.getField().toString().toLowerCase()));
		query.append(String.format(" %s ", orderFilter.getOrder().toString()));
		
		return query.toString();
	}
	
	@Override
	public PagedResult<StoreItem> findByTerm(final Filter filter) {
		
		final String query = buildQueryFromFilter(filter);
		
		final TypedQuery<StoreItem> typedQuery = entityManager.createQuery(query, StoreItem.class);
		
		typedQuery.setParameter("status", StoreItemStatus.ACTIVE);
		
		final PriceFilter priceFilter = filter.getPriceFilter();
		final Range<BigDecimal> priceRange = priceFilter.getPriceRange();
		if (filter.getTermFilter().getTerm().isPresent()) {
			typedQuery.setParameter("term", filter.getTermFilter().getTerm().get().replace("%", "\\%"));
		}
		if (priceRange.hasLowerBound() || priceRange.hasUpperBound()) {
			
			if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
				typedQuery.setParameter("lowerBound",
						priceRange.lowerBoundType() == Range.BoundType.CLOSED
						? priceRange.lowerBound().get()
						: priceRange.lowerBound().get().add(new BigDecimal(1)));
				typedQuery.setParameter("upperBound",
						priceRange.upperBoundType() == Range.BoundType.CLOSED
						? priceRange.upperBound().get()
						: priceRange.upperBound().get().subtract(new BigDecimal(1)));
			} else if (priceRange.hasLowerBound()) {
				typedQuery.setParameter("lowerBound",
						priceRange.lowerBoundType() == Range.BoundType.CLOSED
						? priceRange.lowerBound().get()
						: priceRange.lowerBound().get().add(new BigDecimal(1)));
			} else {
				typedQuery.setParameter("upperBound",
						priceRange.upperBoundType() == Range.BoundType.CLOSED
						? priceRange.upperBound().get()
						: priceRange.upperBound().get().subtract(new BigDecimal(1)));
			}
		}
		final CategoryFilter categoryFilter = filter.getCategoryFilter();
		final Set<Category> categories = categoryFilter.getCategories();
		if (!categories.isEmpty()) {
			typedQuery.setParameter("categories", categories);
		}
		final PageFilter pageFilter = filter.getPageFilter();
		typedQuery.setMaxResults(pageFilter.getPageSize());
		typedQuery.setFirstResult(pageFilter.getPageNumber()*pageFilter.getPageSize());
		
		final List<StoreItem> items = typedQuery.getResultList();
		final PagedResult<StoreItem> results = new PagedResult<>();
		results.setCurrentPage(pageFilter.getPageNumber());
		results.setNumberOfAvailableResults(items.size());
		results.setPageSize(pageFilter.getPageSize());
		results.setResults(items);
		
		results.setNumberOfTotalResults(getNumberOfItems(filter));
		
		return results;
	}

	@Override
	public StoreItem create(final StoreItemBuilder builder) {
		
		final StoreItem item = builder.build();
		
		logger.debug("persisting {}", item);
		
		entityManager.persist(item);
		
		return item;
	}

	@Override
	public PagedResult<StoreItem> getUserItems(final User user, final Filter filter) {
		
		final StringBuilder stringBuilder = new StringBuilder("from StoreItem as si where si.owner=:owner");
		
		final StoreItemStatusFilter statusFilter = filter.getStoreItemStatusFilter();
		if (statusFilter.getStatus() != ItemStatusFilter.ANY) {
			stringBuilder.append(" and si.status=:status");
		}
		
		final TermFilter termFilter = filter.getTermFilter();
		
		if (termFilter.getTerm().isPresent()) {
			stringBuilder.append(" and lower(name) like concat('%', lower(:term), '%')");
		}
		
		final OrderFilter orderFilter = filter.getOrderFilter();
		
		stringBuilder.insert(0, "select count(*) ");
		final TypedQuery<Long> countQuery = entityManager.createQuery(stringBuilder.toString(), Long.class);
		stringBuilder.delete(0, 16);
		
		stringBuilder.append(String.format(" order by si.%s ", orderFilter.getField().toString().toLowerCase()));
		stringBuilder.append(String.format(" %s ", orderFilter.getOrder().toString()));
		final TypedQuery<StoreItem> query = entityManager.createQuery(stringBuilder.toString(), StoreItem.class);
		
		query.setParameter("owner", user);
		countQuery.setParameter("owner", user);
		if (statusFilter.getStatus() != ItemStatusFilter.ANY) {
			query.setParameter("status", StoreItemStatus.valueOf(statusFilter.getStatus().toString()));
			countQuery.setParameter("status", StoreItemStatus.valueOf(statusFilter.getStatus().toString()));
		}
		if (termFilter.getTerm().isPresent()) {
			query.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
			countQuery.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
		}
		
		final PageFilter pageFilter = filter.getPageFilter();
		
		query.setFirstResult(pageFilter.getPageNumber()*pageFilter.getPageSize());
		query.setMaxResults(pageFilter.getPageSize());
		
		final PagedResult<StoreItem> pagedResult = new PagedResult<>();
		final List<StoreItem> results = query.getResultList();
		
		pagedResult.setNumberOfAvailableResults(results.size());
		pagedResult.setNumberOfTotalResults(countQuery.getSingleResult().intValue());
		pagedResult.setCurrentPage(pageFilter.getPageNumber());
		pagedResult.setResults(results);
		pagedResult.setPageSize(pageFilter.getPageSize());
		
		return pagedResult;
	}
	
	@Override
	public List<StoreItem> getUserItems(final User user) {
		
		final TypedQuery<StoreItem> query = entityManager.createQuery("from StoreItem as si where si.owner=:owner", StoreItem.class);
		query.setParameter("owner", user);
		return query.getResultList();
	}
	
	@Override
	public List<StoreItem> getUserItems(final User user, final StoreItemStatus status) {
		
		final TypedQuery<StoreItem> query = entityManager.createQuery("from StoreItem as si where si.owner=:owner and si.status=:status", StoreItem.class);
		query.setParameter("owner", user);
		query.setParameter("status", status);
		return query.getResultList();
		
	}

	@Override
	public StoreItem updateItem(final StoreItem item) {
		
		logger.debug("merging {}", item);
		
		entityManager.merge(item);
		
		return item;
		
	}

	@Override
	public int getNumberOfItems() {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from StoreItem i", Long.class);
		return query.getSingleResult().intValue();
	}
	
	@Override
	public int getNumberOfItems(final Filter filter) {
		
		final TypedQuery<StoreItem> typedQuery = entityManager.createQuery(buildQueryFromFilter(filter), StoreItem.class);
		typedQuery.setParameter("status", StoreItemStatus.ACTIVE);
		final PriceFilter priceFilter = filter.getPriceFilter();
		final Range<BigDecimal> priceRange = priceFilter.getPriceRange();
		if (filter.getTermFilter().getTerm().isPresent()) {
			typedQuery.setParameter("term", filter.getTermFilter().getTerm().get().replace("%", "\\%"));
		}
		if (priceRange.hasLowerBound() || priceRange.hasUpperBound()) {
			
			if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
				typedQuery.setParameter("lowerBound",
						priceRange.lowerBoundType() == Range.BoundType.CLOSED
						? priceRange.lowerBound().get()
						: priceRange.lowerBound().get().add(new BigDecimal(1)));
				typedQuery.setParameter("upperBound",
						priceRange.upperBoundType() == Range.BoundType.CLOSED
						? priceRange.upperBound().get()
						: priceRange.upperBound().get().subtract(new BigDecimal(1)));
			} else if (priceRange.hasLowerBound()) {
				typedQuery.setParameter("lowerBound",
						priceRange.lowerBoundType() == Range.BoundType.CLOSED
						? priceRange.lowerBound().get()
						: priceRange.lowerBound().get().add(new BigDecimal(1)));
			} else {
				typedQuery.setParameter("upperBound",
						priceRange.upperBoundType() == Range.BoundType.CLOSED
						? priceRange.upperBound().get()
						: priceRange.upperBound().get().subtract(new BigDecimal(1)));
			}
		}
		final CategoryFilter categoryFilter = filter.getCategoryFilter();
		final Set<Category> categories = categoryFilter.getCategories();
		if (!categories.isEmpty()) {
			typedQuery.setParameter("categories", categories);
		}
		return typedQuery.getResultList().size();
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
	public boolean increaseSellCount(final StoreItem item) {
		final Query query = entityManager.createQuery("update StoreItem set sold=:sold where item_id=:id");
		query.setParameter("sold", item.getSold()+1);
		query.setParameter("id", item.getId());
		return query.executeUpdate() == 1;
	}

	@Override
	public boolean updateItemStatus(final StoreItem item, final StoreItemStatus status) {
		
		final Query query = entityManager.createQuery("update StoreItem set status=:status where item_id=:id");
		query.setParameter("status", status);
		query.setParameter("id", item.getId());
		return query.executeUpdate() == 1;
	}

}
