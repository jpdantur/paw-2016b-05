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

import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StoreItemJdbcDaoTest {
	
	
	private static final String CATEGORY = "Categoria";

	private static final String USERNAME = "Usuario";

	private static final String NAME = "Item";

	private static final String DESCRIPTION = "Descripcion";

	private static final BigDecimal PRICE = new BigDecimal(123);

	private static final boolean USED = false;

	@Autowired
	private DataSource ds;
	
	@Autowired
	private CategoryJdbcDao categoryDao;
	
	@Autowired
	private UserJdbcDao userDao;
	
	@Autowired
	private StoreItemJdbcDao storeItemDao;

	private Category category;

	private User user;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "store_items");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		JdbcTestUtils.deleteFromTableWhere(jdbcTemplate, "store_categories", "category_id != 0");

		category = categoryDao.create(new CategoryBuilder(CATEGORY,0));
		user = userDao.create(new UserBuilder(USERNAME));
	}
	
	@Test
	public void testCreate() {
		StoreItemBuilder storeItemBuilder = new StoreItemBuilder(NAME, DESCRIPTION, PRICE, category, USED ).owner(user);
		StoreItem created = storeItemDao.create(storeItemBuilder);
		assertEquals(storeItemBuilder.build(),created);
	}
	
	@Test
	public void testFindById() {
		StoreItem created = storeItemDao.create(new StoreItemBuilder(NAME,DESCRIPTION,PRICE,category,USED).owner(user));
		StoreItem found = storeItemDao.findById(created.getId());
		assertEquals(created,found);
	}

}
