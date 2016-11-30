package edu.tp.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserHibernateDaoTest {

	private final static Logger logger = LoggerFactory.getLogger(UserHibernateDaoTest.class);
	
	
	private static final String FIRST_NAME = "Chuck";
	private static final String LAST_NAME = "Norris";
	private static final String USER_NAME = "ChuckNorris";
	private static final String EMAIL = "PresidentTrump@WhiteHouse.gov";
	private static final String PASSWORD = "12345";
	private static final String ROLENAME = "Rol";
	private static final String SLUG = "slug";
	private static final String ITEM_NAME = "Paw TEST";
	private static final String DESCRIPTION = "Very Fun and Challenging!";
	private static final BigDecimal PRICE = new BigDecimal(100);

	@Autowired private IUserDao userDao;
	@Autowired private ICategoryDao categoryDao;
	
	private User user;
	private Purchase purchase;
	private PurchaseBuilder purchaseBuilder;
	private UserBuilder userBuilder;
	private Role role;
	private RoleBuilder roleBuilder;
	
	@Autowired
	DataSource ds;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		userBuilder = new UserBuilder(USER_NAME).email(EMAIL).password(PASSWORD).firstName(FIRST_NAME).lastName(LAST_NAME);
		roleBuilder = new RoleBuilder(ROLENAME, SLUG).id(6);
		role = roleBuilder.build();
	}
	
	@Test
	@Transactional
	public void createTest() {
		user=userDao.create(userBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"users")+1);
	}
	
	@Test
	@Transactional
	public void findByIdTest() {
		user=userDao.create(userBuilder);
		assertEquals(user,userDao.findById(user.getId()));
	}
	
	@Test
	@Transactional
	public void findByUsernameTest() {
		user=userDao.create(userBuilder);
		assertEquals(user,userDao.findByUsername(user.getUsername()));
	}
	
	@Test
	@Transactional
	public void findByEmailTest() {
		user=userDao.create(userBuilder);
		assertEquals(user,userDao.findByEmail(user.getEmail()));
	}
	
	@Test
	@Transactional
	public void idExistsTest() {
		user=userDao.create(userBuilder);
		assertTrue(userDao.idExists(user.getId()));
	}
	
	
	@Test
	@Transactional
	public void emailExistsTest() {
		user=userDao.create(userBuilder);
		assertTrue(userDao.emailExists(user.getEmail()));
	}
	
	
	@Test
	@Transactional
	public void usernameExistsTest() {
		user=userDao.create(userBuilder);
		assertTrue(userDao.usernameExists(user.getUsername()));
	}
	
	@Test
	@Transactional
	public void changePasswordTest() {
		user = userDao.create(userBuilder);
		assertTrue(userDao.changePassword(user, "54321"));
		assertEquals("passwords should match", "54321", user.getPassword());
	}
	
	@Test
	@Transactional
	public void getNumberOfUsersTest() {
		user = userDao.create(userBuilder);
		assertEquals(1, userDao.getNumberOfUsers());
	}
	
	@Test
	@Transactional
	public void addRoleTest()
	{
		user = userDao.create(userBuilder);
		userDao.addRole(user, role);
		assertTrue(user.getRoles().contains(role));
		
	}
	
	@Test
	@Transactional
	public void getAll()
	{
		user = userDao.create(userBuilder);
		assertTrue(userDao.getAll().contains(user));
	}
	
	@Test
	@Transactional
	public void updateUserTest()
	{
		user = userDao.create(userBuilder);
		userBuilder=userBuilder.email("PresidentHillary@WhiteHouse.gov");
		userDao.updateUser(userBuilder.build());
		assertNotNull(userDao.findByEmail("PresidentHillary@WhiteHouse.gov"));
	}
	
}
