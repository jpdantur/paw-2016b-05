package edu.tp.paw.interfaces.dao;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;

public interface IRoleDao {
	
	public Role createRole(final RoleBuilder buider);
	
	public Role findRoleById(final long id);
	public Role findRoleBySlug(final String slug);
	
	public boolean roleExists(final long id);
	public boolean roleExists(final Role role);
	
	public List<Role> getAll();
	public Set<Role> getForUser(final User user);
	
	public Role getDefaultRole();
	public boolean setDefault(final Role role, final boolean def);
	
}
