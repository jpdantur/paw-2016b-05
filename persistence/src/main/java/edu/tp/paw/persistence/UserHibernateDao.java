package edu.tp.paw.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@Repository
public class UserHibernateDao implements IUserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User findById(final long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User findByUsername(final String username) {
		final TypedQuery<User> query = entityManager.createQuery("from User u where u.username=:username", User.class);
		query.setParameter("username", username);
		final List<User> users = query.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public User findByEmail(final String email) {
		final TypedQuery<User> query = entityManager.createQuery("from User u where u.email=:email", User.class);
		query.setParameter("email", email);
		final List<User> users = query.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public boolean idExists(final long id) {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from User u where u.id=:id", Long.class);
		query.setParameter("id", id);
		return query.getSingleResult() == 1;
	}

	@Override
	public boolean usernameExists(final String username) {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from User u where u.username=:username", Long.class);
		query.setParameter("username", username);
		return query.getSingleResult() == 1;
	}

	@Override
	public boolean emailExists(final String email) {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from User u where u.email=:email", Long.class);
		query.setParameter("email", email);
		return query.getSingleResult() == 1;
	}

	@Override
	public User create(final UserBuilder builder) {
		final User user = builder.build();
		entityManager.persist(user);
		return user;
	}

	@Override
	public boolean changePassword(final User user, final String password) {
		final Query query = entityManager.createQuery("update User set password = :password where id = :id");
		query.setParameter("id", user.getId());
		query.setParameter("password", password);
		query.executeUpdate();
		return false;
	}

	@Override
	public boolean addFavourite(final User user, final StoreItem item) {
		
		final User u = entityManager.getReference(User.class, user.getId());
		
		// hibernate trick
		u.getFavourites().iterator();
		
		u.getFavourites().add(item);
		
		return true;
	}
	
	@Override
	public Set<StoreItem> getFavourites(final User user) {
		
		final User u = entityManager.getReference(User.class, user.getId());
		
		u.getFavourites().iterator();
		
		return u.getFavourites();
		
	}

	@Override
	public boolean removeFavourite(final User user, final StoreItem item) {
		
		final User u = entityManager.getReference(User.class, user.getId());
		
		// hibernate trick
		u.getFavourites().iterator();
		
		u.getFavourites().remove(item);
		
		return true;
	}

	@Override
	public int getNumberOfUsers() {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from User c", Long.class);
		return query.getSingleResult().intValue();
	}

	@Override
	public boolean addRole(final User user, final Role role) {
		
		final User u = entityManager.getReference(User.class, user.getId());
		
		// hibernate trick
		u.getRoles().iterator();
		
		u.getRoles().add(role);
		
		return true;
	}

	@Override
	public List<User> getAll() {
		return null;
	}

	@Override
	public boolean updateUser(final User user) {
		
		entityManager.merge(user);
		
		return true;
	}

}
