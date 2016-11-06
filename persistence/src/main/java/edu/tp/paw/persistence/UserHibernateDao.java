package edu.tp.paw.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.OrderFilter;
import edu.tp.paw.model.filter.PageFilter;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.PurchaseStatusFilter;
import edu.tp.paw.model.filter.TermFilter;

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
		final TypedQuery<User> query = entityManager.createQuery("from User u", User.class);
		
		return query.getResultList();
	}

	@Override
	public boolean updateUser(final User user) {
		
		entityManager.merge(user);
		
		return true;
	}

	@Override
	public boolean purchase(final PurchaseBuilder builder) {
		
		entityManager.persist(builder.build());
		
		return true;
	}

	@Override
	public PagedResult<Purchase> getTransactions(final User user, final Filter filter) {
		
		final StringBuilder stringBuilder = new StringBuilder("from Purchase as p where p.item.owner=:owner and p.status=:status");
		
		final TermFilter termFilter = filter.getTermFilter();
		if (termFilter.getTerm().isPresent()) {
			stringBuilder.append(" and lower(p.item.name) like concat('%', lower(:term), '%')");
		}
		
		stringBuilder.insert(0, "select count(*) ");
		final TypedQuery<Long> countQuery = entityManager.createQuery(stringBuilder.toString(), Long.class);
		stringBuilder.delete(0, 16);
		
		final OrderFilter orderFilter = filter.getOrderFilter();
		stringBuilder.append(String.format(" order by p.item.%s ", orderFilter.getField().toString().toLowerCase()));
		stringBuilder.append(String.format(" %s ", orderFilter.getOrder().toString()));
		final TypedQuery<Purchase> query = entityManager.createQuery(stringBuilder.toString(), Purchase.class);
		
		query.setParameter("owner", user);
		countQuery.setParameter("owner", user);
		
		final PurchaseStatusFilter statusFilter = filter.getPurchaseStatusFilter();
		query.setParameter("status", PurchaseStatus.valueOf(statusFilter.getStatus().toString()));
		countQuery.setParameter("status", PurchaseStatus.valueOf(statusFilter.getStatus().toString()));
		
		if (termFilter.getTerm().isPresent()) {
			query.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
			countQuery.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
		}
		
		final PageFilter pageFilter = filter.getPageFilter();
		
		query.setFirstResult(pageFilter.getPageNumber()*pageFilter.getPageSize());
		query.setMaxResults(pageFilter.getPageSize());
		
		final PagedResult<Purchase> pagedResult = new PagedResult<>();
		final List<Purchase> results = query.getResultList();
		
		pagedResult.setNumberOfAvailableResults(results.size());
		pagedResult.setNumberOfTotalResults(countQuery.getSingleResult().intValue());
		pagedResult.setCurrentPage(pageFilter.getPageNumber());
		pagedResult.setResults(results);
		pagedResult.setPageSize(pageFilter.getPageSize());
		
		return pagedResult;
	}
	
	@Override
	public List<Purchase> getTransactions(final User user) {
		
		final TypedQuery<Purchase> query = entityManager.createQuery("from Purchase p where p.item.owner=:owner", Purchase.class);
		
		query.setParameter("owner", user);
		
		return query.getResultList();
	}
	
	@Override
	public List<Purchase> getTransactions(final User user, final PurchaseStatus status) {
		
		final TypedQuery<Purchase> query = entityManager.createQuery("from Purchase p where p.status=:status and p.item.owner=:owner", Purchase.class);
		
		query.setParameter("owner", user);
		query.setParameter("status", status);
		
		return query.getResultList();
	}

	@Override
	public PagedResult<Purchase> getPurchases(final User user, final Filter filter) {
		
		final StringBuilder stringBuilder = new StringBuilder("from Purchase as p where p.buyer=:buyer and p.status=:status");
		
		final TermFilter termFilter = filter.getTermFilter();
		if (termFilter.getTerm().isPresent()) {
			stringBuilder.append(" and lower(p.item.name) like concat('%', lower(:term), '%')");
		}
		
		stringBuilder.insert(0, "select count(*) ");
		final TypedQuery<Long> countQuery = entityManager.createQuery(stringBuilder.toString(), Long.class);
		stringBuilder.delete(0, 16);
		
		final OrderFilter orderFilter = filter.getOrderFilter();
		stringBuilder.append(String.format(" order by p.item.%s ", orderFilter.getField().toString().toLowerCase()));
		stringBuilder.append(String.format(" %s ", orderFilter.getOrder().toString()));
		final TypedQuery<Purchase> query = entityManager.createQuery(stringBuilder.toString(), Purchase.class);
		
		query.setParameter("buyer", user);
		countQuery.setParameter("buyer", user);
		
		final PurchaseStatusFilter statusFilter = filter.getPurchaseStatusFilter();
		query.setParameter("status", PurchaseStatus.valueOf(statusFilter.getStatus().toString()));
		countQuery.setParameter("status", PurchaseStatus.valueOf(statusFilter.getStatus().toString()));
		
		if (termFilter.getTerm().isPresent()) {
			query.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
			countQuery.setParameter("term", termFilter.getTerm().get().toLowerCase().replace("%", "\\%"));
		}
		
		final PageFilter pageFilter = filter.getPageFilter();
		
		query.setFirstResult(pageFilter.getPageNumber()*pageFilter.getPageSize());
		query.setMaxResults(pageFilter.getPageSize());
		
		final PagedResult<Purchase> pagedResult = new PagedResult<>();
		final List<Purchase> results = query.getResultList();
		
		pagedResult.setNumberOfAvailableResults(results.size());
		pagedResult.setNumberOfTotalResults(countQuery.getSingleResult().intValue());
		pagedResult.setCurrentPage(pageFilter.getPageNumber());
		pagedResult.setResults(results);
		pagedResult.setPageSize(pageFilter.getPageSize());
		
		return pagedResult;
	}
	
	@Override
	public Set<Purchase> getPurchases(final User user) {
		
		final User u = entityManager.getReference(User.class, user.getId());
		
		u.getPurchases().iterator();
		
		return u.getPurchases();
	}
	
	@Override
	public List<Purchase> getPurchases(final User user, final PurchaseStatus status) {
		
		final TypedQuery<Purchase> query = entityManager.createQuery("from Purchase p where p.status=:status and p.buyer=:user", Purchase.class);
		
		query.setParameter("user", user);
		query.setParameter("status", status);
		
		return query.getResultList();
	}

}
