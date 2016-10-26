package edu.tp.paw.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import edu.tp.paw.model.filter.CategoryFilter;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.OrderFilter;
import edu.tp.paw.model.filter.PageFilter;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.PriceFilter;
import edu.tp.paw.model.filter.Range;
import edu.tp.paw.model.filter.TermFilter;

@Repository
public class StoreItemHibernateDao implements IStoreItemDao {

	private static final String ORDER_DESCENDING = "DESC";	
	private static final String ORDER_ASCENDING = "ASC";
	
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

	private String buildQueryFromFilter(final Filter filter) {
		
		final StringBuilder query = new StringBuilder();
		
		query.append("from StoreItem item where ");
		
		boolean logicalOperator = false;
		
		final TermFilter termFilter = filter.getTermFilter();
		
		if (termFilter.getTerm().isPresent()) {

			query.append(
				"("
					+ "lower(item.name) like concat('%', lower(:term), '%') "
					+ "or "
					+ "lower(item.description) like concat('%', lower(:term), '%') "
					+ "or "
					+ "category in ("
						+ "select c.id from Category c "
						+ "where lower(c.name) like concat('%', lower(:term), '%') "
					+ ")"
				+")");
			
			logicalOperator = true;
		}
		
		final PriceFilter priceFilter = filter.getPriceFilter();
		final Range<BigDecimal> priceRange = priceFilter.getPriceRange();
		
		if (priceRange.hasLowerBound() || priceRange.hasUpperBound()) {
			if (logicalOperator) {
				query.append(" and ");
			}
			
			if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
				query.append(" price <= :upperBound and :lowerBound <= price");
			} else if (priceRange.hasLowerBound()) {
				query.append(":lowerBound <= price");
			} else {
				query.append("price <= :upperBound");
			}
			
			logicalOperator = true;
		}
		
		final CategoryFilter categoryFilter = filter.getCategoryFilter();
		final Set<Category> categories = categoryFilter.getCategories();

		if (!categories.isEmpty()) {
			
			if (logicalOperator) {
				query.append(" and ");
			}
			
			query.append(" item.category in (:categories) ");

			logicalOperator = true;
			
		}
		
		final OrderFilter orderFilter = filter.getOrderFilter();
		
		switch (orderFilter.getField()) {
			case PRICE:
				query.append(String.format(" order by item.%s ", "price"));
				break;
			case NAME:
				query.append(String.format(" order by item.%s ", "name"));
				break;

			default:
				query.append(String.format(" order by item.%s ", "price"));
				break;
		}
		
		switch (orderFilter.getOrder()) {
			case ASC:
				query.append(String.format(" %s ", ORDER_ASCENDING));
				break;
			case DESC:
				query.append(String.format(" %s ", ORDER_DESCENDING));
				break;
				
			default:
				query.append(String.format(" %s ", ORDER_DESCENDING));
				break;
		}
		
		return query.toString();
	}
	
	@Override
	public PagedResult<StoreItem> findByTerm(final Filter filter) {
		
		final String query = buildQueryFromFilter(filter);
		
		System.out.println(query);
		
		final TypedQuery<StoreItem> typedQuery = entityManager.createQuery(query, StoreItem.class);
		
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
	public int getNumberOfItems(final Filter filter) {
		
	final TypedQuery<StoreItem> typedQuery = entityManager.createQuery(buildQueryFromFilter(filter), StoreItem.class);
		
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
