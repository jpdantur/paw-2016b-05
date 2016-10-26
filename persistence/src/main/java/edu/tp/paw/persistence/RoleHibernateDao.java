package edu.tp.paw.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IRoleDao;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;

@Repository
public class RoleHibernateDao implements IRoleDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Role createRole(final RoleBuilder buider) {
		final Role role = buider.build();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Role> getForUser(final User user) {
		
		final User userWithRoles = entityManager.find(User.class, user.getId());
		
		return userWithRoles.getRoles();
		
//		final TypedQuery<Role> query = entityManager.createQuery("from Role r where r.u=:", resultClass) 
//		return user.getRoles();
	}

	@Override
	public Role getDefaultRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setDefault(Role role, boolean def) {
		// TODO Auto-generated method stub
		return false;
	}

}
