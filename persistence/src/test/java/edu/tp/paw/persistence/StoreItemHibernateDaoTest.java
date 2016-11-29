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
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StoreItemHibernateDaoTest {
	
	private static final String NAME = "Paw Test";

	private static final String DESCRIPTION = "Very Fun and Challenging :D";

	private static final BigDecimal PRICE = new BigDecimal(100);

	@Autowired
	private IStoreItemDao itemDao;
	
	@Autowired
	private ICategoryDao categoryDao;
	
	private StoreItemBuilder itemBuilder;
	private StoreItem item;
	private CategoryBuilder categoryBuilder;
	@Autowired
	private IUserDao userDao;

	private Category category;
	private UserBuilder userBuilder;
	private User user;
	
	@Autowired
	DataSource ds;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
	
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		categoryBuilder = new CategoryBuilder("Category", categoryDao.findById(0));
		category = categoryDao.create(categoryBuilder);
		userBuilder = new UserBuilder("Username");
		user = userDao.create(userBuilder);
		itemBuilder = new StoreItemBuilder(NAME, DESCRIPTION, PRICE, false).category(category).owner(user).status(StoreItemStatus.ACTIVE);
	}
	
	
	@Test
	@Transactional
	public void testCreate() {
		item = itemDao.create(itemBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "store_items")+1);
	}
	
	@Test
	@Transactional
	public void testFindById(){
		item = itemDao.create(itemBuilder);
		assertEquals(item, itemDao.findById(item.getId()));
	}
	
	
	@Test
	@Transactional
	public void testGetNumberOfItems() {
		item = itemDao.create(itemBuilder);
		assertEquals(1, itemDao.getNumberOfItems());
	}
	
	@Test
	@Transactional
	public void testUpdateItem(){
		item = itemDao.create(itemBuilder);
		itemBuilder = itemBuilder.status(StoreItemStatus.PAUSED);
		item = itemDao.updateItem(itemBuilder.build());
		assertEquals(StoreItemStatus.PAUSED,item.getStatus());
	}
	
	@Test
	@Transactional
	public void testItemExists(){
		item = itemDao.create(itemBuilder);
		assertTrue(itemDao.itemExists(item.getId()));
	}
	
	@Test
	@Transactional
	public void increaseSellCountTest(){
		item = itemDao.create(itemBuilder);
		long old = item.getSold();
		boolean b = itemDao.increaseSellCount(item);
		assertTrue(!b || item.getSold()==old+1);
		
	}
	
	@Test
	@Transactional
	public void getUserItemsTest() {
		item = itemDao.create(itemBuilder);
		assertTrue(itemDao.getUserItems(user).contains(item));
	}
	
}
