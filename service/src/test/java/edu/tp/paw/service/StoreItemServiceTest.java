package edu.tp.paw.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.service.*;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StoreItemServiceTest {

	@Autowired DataSource ds;
	
	@Autowired private IUserService userService;
	@Autowired private IStoreItemService itemService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IRoleService roleService;
	
	private JdbcTemplate jdbcTemplate;

	private Category category;

	private Role role;

	private User user;

	private StoreItemBuilder itemBuilder;

	private StoreItem item;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);

		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		jdbcTemplate.execute("insert into roles (role_id, role_name, role_slug, default_role) values (1, 'Store User', 'USER', true);");
		
		category = categoryService.findById(0);
		role = roleService.findRoleById(1);
		user = userService.createUser(new UserBuilder("username").firstName("Name").lastName("lastName").email("aa@aa.aa").password("password"), role);
		itemBuilder = new StoreItemBuilder("Name", "Desc", new BigDecimal(100), false).owner(user).category(category);
	}
	
	@Test
	@Transactional
	public void testCreate() {
		item = itemService.create(itemBuilder);
		assertNotNull(item);
	}
	
	@Test
	@Transactional
	public void findByIdTest() {
		item = itemService.create(itemBuilder);
		assertEquals(item, itemService.findById(item.getId()));
	}
	
	@Test
	@Transactional
	public void getNumberOfItemsTest() {
		item = itemService.create(itemBuilder);
		assertEquals(1,itemService.getNumberOfItems());
	}
	
	@Test
	@Transactional
	public void increaseSellCountTest() {
		item = itemService.create(itemBuilder);
		long count = item.getSold();
		itemService.increaseSellCount(item);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "store_items",
				String.format("item_id = %d and sold = %d",item.getId(), item.getSold()+1))== 1);
//		assertEquals(count+1,item.getSold());
	}
	
	@Test
	@Transactional
	public void itemExistsTest() {
		item = itemService.create(itemBuilder);
		assertTrue(itemService.itemExists(item.getId()));
		assertTrue(itemService.itemExists(item));
	}
}
