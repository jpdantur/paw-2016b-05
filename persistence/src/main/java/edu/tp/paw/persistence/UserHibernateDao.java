package edu.tp.paw.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
		final User user = entityManager.find(User.class, id);
		return user;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addFavourite(final User user, final StoreItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFavourite(final User user, final StoreItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumberOfUsers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addRole(final User user, final Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUser(final User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
