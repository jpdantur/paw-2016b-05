package edu.tp.paw.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IRoleDao;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;

@Repository
public class RoleHibernateDao implements IRoleDao {

	private final static Logger logger = LoggerFactory.getLogger(RoleHibernateDao.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Role createRole(final RoleBuilder builder) {
		logger.trace("builder is {}", builder);
		final Role role = builder.build();
		logger.debug("persisting {}", role);
		entityManager.persist(role);
		return role;
	}

	@Override
	public Role findRoleById(final long id) {
		return entityManager.find(Role.class, id);
	}

	@Override
	public Role findRoleBySlug(final String slug) {
		final TypedQuery<Role> query = entityManager.createQuery("from Role r where r.slug=:slug", Role.class);
		query.setParameter("slug", slug);
		final List<Role> roles = query.getResultList();
		return roles.isEmpty() ? null : roles.get(0);
	}

	@Override
	public boolean roleExists(final long id) {
		final TypedQuery<Long> query = entityManager.createQuery("select count(*) from Role r where r.id=:id", Long.class);
		query.setParameter("id", id);
		return query.getSingleResult() == 1;
	}

	@Override
	public boolean roleExists(final Role role) {
		return roleExists(role.getId());
	}

	@Override
	public List<Role> getAll() {
		final TypedQuery<Role> query = entityManager.createQuery("from Role r", Role.class);
		return query.getResultList();
	}

	@Override
	public Set<Role> getForUser(final User user) {
		
		final User u = entityManager.getReference(User.class, user.getId());
		
		u.getRoles().iterator();
		
		return u.getRoles();
		
//		final TypedQuery<Role> query = entityManager.createQuery("from Role r where r.u=:", resultClass) 
//		return user.getRoles();
	}

	@Override
	public Role getDefaultRole() {
		final TypedQuery<Role> query = entityManager.createQuery("from Role r where r._default=true", Role.class);
		final List<Role> roles = query.getResultList(); 
		return roles.isEmpty() ? null : roles.get(0);
	}

	@Override
	public boolean setDefault(final Role role, final boolean def) {
		
		
		
		return false;
	}

}
