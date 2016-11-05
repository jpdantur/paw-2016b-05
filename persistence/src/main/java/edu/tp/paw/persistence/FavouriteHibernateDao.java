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
	public List<Favourite> getFavouritesForUser(final User user, final Filter filter) {
		
		final TypedQuery<Favourite> query = entityManager.createQuery("from Favourite f where f.user=:user offset :offset order by created", Favourite.class);
		
		query.setMaxResults(filter.getPageFilter().getPageSize());
		
		query.setParameter("user", user);
		query.setParameter("offset", filter.getPageFilter().getPageNumber()*filter.getPageFilter().getPageSize());
		
		return query.getResultList();
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
