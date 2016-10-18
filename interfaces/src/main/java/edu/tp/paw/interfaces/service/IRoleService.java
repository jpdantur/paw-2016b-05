package edu.tp.paw.interfaces.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tp.paw.model.Role;
import edu.tp.paw.model.User;
import edu.tp.paw.model.RoleBuilder;

public interface IRoleService {

	public List<Role> getRoles();
	public List<Role> getRolesForUser(final User user);
	
	public boolean roleExists(final long id);
	public boolean roleExists(final Role role);
	
	public Role createRole(final RoleBuilder builder);
	
	public Role findRoleById(final long id);
	public Role findRoleBySlug(final String slug);
	
	public Role getDefaultRole();
	public boolean makeDefault(final Role role);
	
}
