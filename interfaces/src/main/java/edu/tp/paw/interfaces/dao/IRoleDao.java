package edu.tp.paw.interfaces.dao;

import java.util.List;

import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;

public interface IRoleDao {
	
	public Role createRole(final RoleBuilder buider);
	
	public Role findRoleById(final long id);
	public Role findRoleBySlug(final String slug);
	
	public List<Role> getAll();
	public List<Role> getForUser(final User user);
	
}
