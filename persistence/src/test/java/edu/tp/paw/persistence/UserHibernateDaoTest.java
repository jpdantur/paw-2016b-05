package edu.tp.paw.persistence;

import static org.junit.Assert.*;

import java.math.BigDecimal;

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

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.dao.IRoleDao;
import edu.tp.paw.interfaces.dao.IUserDao;
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

	
	private static final String USER_NAME = "Chuck Norris";

	private static final String EMAIL = "PresidentTrump@WhiteHouse.gov";

	private static final String PASSWORD = "12345";

	private static final String ROLENAME = "Rol";

	private static final String SLUG = "Slug";

	private static final String ITEM_NAME = "Paw TEST";

	private static final String DESCRIPTION = "Very Fun and Challenging!";

	private static final BigDecimal PRICE = new BigDecimal(100);

	@Autowired
	private IUserDao userDao;
	
	
	private User user;
	private Purchase purchase;
	private StoreItem item;
	private StoreItemBuilder itemBuilder;
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
		userBuilder = new UserBuilder(USER_NAME).email(EMAIL).password(PASSWORD);
		roleBuilder = new RoleBuilder (ROLENAME, SLUG).id(6);
		role = roleBuilder.build();
		itemBuilder = new StoreItemBuilder(ITEM_NAME, DESCRIPTION, PRICE, false);
		item = itemBuilder.build();
		
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
		userDao.changePassword(user, "54321");
		assertEquals("54321",userDao.findById(user.getId()).getPassword());
		
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
