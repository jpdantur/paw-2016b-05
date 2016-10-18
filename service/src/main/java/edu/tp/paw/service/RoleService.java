package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.dao.IRoleDao;
import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;

@Service
public class RoleService implements IRoleService {

	@Autowired private IRoleDao roleDao;
	@Autowired private IUserService userService;
	
	@Override
	public List<Role> getRoles() {
		return roleDao.getAll();
	}
	
	@Override
	public List<Role> getRolesForUser(final User user) {
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		if (!userService.userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		return roleDao.getForUser(user);
	}

	@Override
	public Role createRole(final RoleBuilder builder) {
		if (builder == null) {
			throw new IllegalArgumentException("role cant be null");
		}
		if (builder.getRoleName() == null) {
			throw new IllegalArgumentException("role name cant be null");
		}
		if (builder.getSlug() == null) {
			throw new IllegalArgumentException("role slug cant be null");
		}
		return roleDao.createRole(builder);
	}

	@Override
	public Role findRoleById(final long id) {
		return roleDao.findRoleById(id);
	}

	@Override
	public Role findRoleBySlug(final String slug) {
		if (slug == null) {
			throw new IllegalArgumentException("slug cant be null");
		}
		return roleDao.findRoleBySlug(slug);
	}

	@Override
	public boolean roleExists(final long id) {
		return roleDao.roleExists(id);
	}

	@Override
	public boolean roleExists(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("role cant be null");
		}
		return roleExists(role.getId());
	}

	@Override
	public Role getDefaultRole() {
		return roleDao.getDefaultRole();
	}

	@Transactional
	@Override
	public boolean makeDefault(final Role role) {
		
		if (role == null) {
			throw new IllegalArgumentException("role cant be null");
		}
		
		if (!roleExists(role)) {
			throw new IllegalArgumentException("role must exist");
		}
		
		final Role defaultRole = getDefaultRole();
		
		if (defaultRole.equals(role)) {
			return true;
		}
		
		roleDao.setDefault(defaultRole, false);
		roleDao.setDefault(role, true);
		
		return true;
	}

	

}
