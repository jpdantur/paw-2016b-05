package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IRoleDao;
import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;

@Service
public class RoleService implements IRoleService {

	@Autowired private IRoleDao roleDao;
	
	@Override
	public List<Role> getRoles() {
		return roleDao.getAll();
	}
	
	@Override
	public List<Role> getRolesForUser(final User user) {
		return roleDao.getForUser(user);
	}

	@Override
	public Role createRole(final RoleBuilder builder) {
		return roleDao.createRole(builder);
	}

	@Override
	public Role findRoleById(final long id) {
		return roleDao.findRoleById(id);
	}

	@Override
	public Role findRoleBySlug(final String slug) {
		return roleDao.findRoleBySlug(slug);
	}

	

}
