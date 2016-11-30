package edu.tp.paw.service;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RoleServiceTest {
	
	@Autowired DataSource ds;
	private JdbcTemplate jdbcTemplate;
	@Autowired IRoleService roleService;
	private RoleBuilder roleBuilder;
	private Role role;
	private User user;
	@Autowired private IUserService userService;
	
	@Before
	@Transactional
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		roleBuilder = new RoleBuilder("name", "SLUG").makeDefault(true);
	}
	
	@Test
	@Transactional
	public void testCreate() {
		role = roleService.createRole(roleBuilder);
		assertNotNull(role);
	}
	
	@Test
	@Transactional
	public void findByIdTest() {
		role = roleService.createRole(roleBuilder);
		assertEquals(role, roleService.findRoleById(role.getId()));
	}
	
	@Test
	@Transactional
	public void testRoleExists() {
		role = roleService.createRole(roleBuilder);
		assertTrue(roleService.roleExists(role));
		assertTrue(roleService.roleExists(role.getId()));
	}
	
	@Test
	@Transactional
	public void testGetRoles() {
		role = roleService.createRole(roleBuilder);
		assertTrue(roleService.getRoles().contains(role));
	}
	
	@Test
	@Transactional
	public void getRolesByUserTest() {
		role = roleService.createRole(roleBuilder);
		user = userService.createUser(new UserBuilder("username").firstName("Name").lastName("lastName").email("aa@aa.aa").password("password"), role);
		assertTrue(roleService.getRolesForUser(user).contains(role));
	}
	
	@Test
	@Transactional
	public void makeDefaultTest() {
		role = roleService.createRole(roleBuilder);
		roleService.makeDefault(role);
		assertTrue(role.isDefault());
	}
	
	
	
}
