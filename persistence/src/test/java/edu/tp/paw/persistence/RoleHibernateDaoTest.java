package edu.tp.paw.persistence;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.dao.IRoleDao;
import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RoleHibernateDaoTest {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IUserDao userDao;
	
	private JdbcTemplate jdbcTemplate;
	private RoleBuilder roleBuilder;
	private RoleBuilder otherRoleBuilder;
	private Role otherRole;
	private Role role;
	private UserBuilder userBuilder;
	private User user;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		userBuilder = new UserBuilder("pepego").firstName("pepe").lastName("gonzalez").password("secret");
		user = userDao.create(userBuilder);
		roleBuilder = new RoleBuilder("name","slug").id(0).makeDefault(true);
		otherRoleBuilder = new RoleBuilder("otherN","otherS").id(0);
	}
	
	@Test
	@Transactional
	public void testCreate() {
		role = roleDao.createRole(roleBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "roles")+1);
	}
	
	@Test
	@Transactional
	public void testFindById() {
		role = roleDao.createRole(roleBuilder);
		assertEquals(role,roleDao.findRoleById(role.getId()));
	}
	
	@Test
	@Transactional
	public void testFindBySlug() {
		role = roleDao.createRole(roleBuilder);
		assertEquals(role,roleDao.findRoleBySlug(role.getSlug()));
	}
	
	@Test
	@Transactional
	public void testRoleExists() {
		role = roleDao.createRole(roleBuilder);
		assertTrue(roleDao.roleExists(role.getId()));
		assertTrue(roleDao.roleExists(role));
	}
	
	@Test
	@Transactional
	public void testGetAll() {
		role = roleDao.createRole(roleBuilder);
		assertEquals(1,roleDao.getAll().size());
		assertTrue(roleDao.getAll().contains(role));
	}
	
	@Test
	@Transactional
	public void getDefaultTest() {
		role = roleDao.createRole(roleBuilder);
		assertEquals(role,roleDao.getDefaultRole());
	}
	
	@Test
	@Transactional
	public void getForUserTest() {
		role = roleDao.createRole(roleBuilder);
		userDao.addRole(user, role);
		assertTrue(roleDao.getForUser(user).contains(role));
	}

}
