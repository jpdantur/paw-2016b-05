package edu.tp.paw.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IFavouriteDao;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.OrderFilter;
import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.PageFilter;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.TermFilter;

@Repository
public class FavouriteHibernateDao implements IFavouriteDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Set<Favourite> getFavouritesForUser(final User user) {
		
		final User u = entityManager.getReference(User.class, user.getId());
		
		u.getFavourites().iterator();
		
		return u.getFavourites();
		
	}

	@Override
	public PagedResult<Favourite> getFavouritesForUser(final User user, final Filter filter) {
		
		final StringBuilder stringBuilder = new StringBuilder("from Favourite as f where f.user=:user");
		
		final TermFilter termFilter = filter.getTermFilter();
		if (termFilter.getTerm().isPresent()) {
			stringBuilder.append(" and lower(f.item.name) like concat('%', lower(:term), '%')");
		}
		
		stringBuilder.insert(0, "select count(*) ");
		final TypedQuery<Long> countQuery = entityManager.createQuery(stringBuilder.toString(), Long.class);
		stringBuilder.delete(0, 16);
		
		final OrderFilter orderFilter = filter.getOrderFilter();
		if (orderFilter.getField() == SortField.CREATED) {
			stringBuilder.append(String.format(" order by f.%s ", orderFilter.getField().toString().toLowerCase()));
		} else {
			stringBuilder.append(String.format(" order by f.item.%s ", orderFilter.getField().toString().toLowerCase()));
		}
		stringBuilder.append(String.format(" %s ", orderFilter.getOrder().toString()));
		final TypedQuery<Favourite> query = entityManager.createQuery(stringBuilder.toString(), Favourite.class);
		
		query.setParameter("user", user);
		countQuery.setParameter("user", user);
		
		if (termFilter.getTerm().isPresent()) {
			query.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
			countQuery.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
		}
		
		final PageFilter pageFilter = filter.getPageFilter();
		
		query.setFirstResult(pageFilter.getPageNumber()*pageFilter.getPageSize());
		query.setMaxResults(pageFilter.getPageSize());
		
		final PagedResult<Favourite> pagedResult = new PagedResult<>();
		final List<Favourite> results = query.getResultList();
		
		pagedResult.setNumberOfAvailableResults(results.size());
		pagedResult.setNumberOfTotalResults(countQuery.getSingleResult().intValue());
		pagedResult.setCurrentPage(pageFilter.getPageNumber());
		pagedResult.setResults(results);
		pagedResult.setPageSize(pageFilter.getPageSize());
		
		return pagedResult;
	}
	
	@Override
	public Favourite createFavourite(final FavouriteBuilder builder) {
		
		final Favourite favourite = builder.build();
		
		entityManager.persist(favourite);
		
		return favourite;
	}

	@Override
	public boolean removeFavourite(final Favourite favourite) {
		
		final Query q = entityManager.createQuery("delete Favourite f where f.id = :id");
		q.setParameter("id", favourite.getId());
		return q.executeUpdate() == 1;
	}

	@Override
	public boolean exists(final Favourite favourite) {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from Favourite f where f.id=:id", Long.class);
		query.setParameter("id", favourite.getId());
		return query.getSingleResult() == 1;
	}

	@Override
	public Favourite findById(final long id) {
		return entityManager.find(Favourite.class, id);
	}

}
