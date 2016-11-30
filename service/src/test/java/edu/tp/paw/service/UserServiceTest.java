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
public class UserServiceTest {
	
	@Autowired DataSource ds;
	@Autowired private IUserService userService;
	@Autowired private IRoleService roleService;
	private UserBuilder userBuilder;
	private JdbcTemplate jdbcTemplate;
	private User user;
	private Role role;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		userBuilder = new UserBuilder("Nombre").firstName("Nombredepila").lastName("apellido").email("user@mail.com").password("password");
		role = roleService.createRole(new RoleBuilder("NAME","SLUG"));
	}
	
	@Test
	@Transactional
	public void testCreate() {
		user = userService.createUser(userBuilder, role);
		assertNotNull(user);
	}
	
	@Test
	@Transactional
	public void testFindById() {
		user = userService.createUser(userBuilder, role);
		assertEquals(user,userService.findById(user.getId()));
	}
	
	@Test
	@Transactional
	public void testFindByEmail() {
		user = userService.createUser(userBuilder, role);
		assertEquals(user,userService.findByEmail(user.getEmail()));
	}
	
	@Test
	@Transactional
	public void testGetNumberOfUsers() {
		user = userService.createUser(userBuilder, role);
		assertEquals(1,userService.getNumberOfUsers());
	}
	
	@Test
	@Transactional
	public void testGetAllUsers() {
		user = userService.createUser(userBuilder, role);
		assertTrue(userService.getAllUsers().contains(user));
	}
	
	@Test
	@Transactional
	public void testEmailExists() {
		user = userService.createUser(userBuilder, role);
		assertTrue(userService.emailExists(user.getEmail()));
	}
	
	@Test
	@Transactional
	public void testChangePassword() {
		user = userService.createUser(userBuilder, role);
		userService.changePassword(user, "newpass");
		assertEquals("newpass",user.getPassword());
	}
}
