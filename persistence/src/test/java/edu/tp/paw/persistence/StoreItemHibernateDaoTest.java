package edu.tp.paw.persistence;

import static org.junit.Assert.*;

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
	
	private final static Logger logger = LoggerFactory.getLogger(StoreItemHibernateDaoTest.class);
	
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
		logger.trace("setUp start");
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		categoryBuilder = new CategoryBuilder("Category", categoryDao.findById(0));
		category = categoryDao.create(categoryBuilder);
		userBuilder = new UserBuilder("Username");
		user = userDao.create(userBuilder);
		itemBuilder = new StoreItemBuilder(NAME, DESCRIPTION, PRICE, false).category(category).owner(user).status(StoreItemStatus.ACTIVE);
		logger.trace("setUp end");
	}
	
	
	@Test
	@Transactional
	public void testCreate() {
		logger.trace("testCreate start");
		item = itemDao.create(itemBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "store_items")+1);
		logger.trace("testCreate end");
	}
	
	@Test
	@Transactional
	public void testFindById(){
		logger.trace("testFindById start");
		item = itemDao.create(itemBuilder);
		logger.debug("created in testing findById");
		assertEquals(item, itemDao.findById(item.getId()));
		logger.trace("testFindById end");
	}
	
	
	@Test
	@Transactional
	public void testGetNumberOfItems() {
		logger.trace("testGetNumberOfItems start");
		item = itemDao.create(itemBuilder);
		assertEquals(1, itemDao.getNumberOfItems());
		logger.trace("testGetNumberOfItems end");
	}
	
	@Test
	@Transactional
	public void testUpdateItem(){
		logger.trace("testUpdateItem start");
		item = itemDao.create(itemBuilder);
		itemBuilder = itemBuilder.status(StoreItemStatus.PAUSED);
		item = itemDao.updateItem(itemBuilder.build());
		assertEquals(StoreItemStatus.PAUSED,item.getStatus());
		logger.trace("testUpdateItem start");
	}
	
	@Test
	@Transactional
	public void testItemExists(){
		logger.trace("testItemExists start");
		item = itemDao.create(itemBuilder);
		assertTrue(itemDao.itemExists(item.getId()));
		logger.trace("testItemExists end");
	}
	
	@Test
	@Transactional
	public void increaseSellCountTest(){
		logger.trace("increaseSellCountTest start");
		item = itemDao.create(itemBuilder);
		itemDao.increaseSellCount(item);
		assertTrue(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "store_items",
				String.format("item_id = %d and sold = %d",item.getId(), item.getSold()+1))== 1);
		logger.trace("increaseSellCountTest end");
	}
	
//	@Test
//	@Transactional
//	public void getUserItemsTest() {
//		logger.trace("getUserItemsTest start");
//		item = itemDao.create(itemBuilder);
//		assertTrue(itemDao.getUserItems(user).contains(item));
//		logger.trace("getUserItemsTest end");
//	}
	
}
