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
import edu.tp.paw.interfaces.dao.IFavouriteDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FavouriteHibernateDaoTest {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	private IFavouriteDao favouriteDao;
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IStoreItemDao itemDao;
	
	private JdbcTemplate jdbcTemplate;
	
	private FavouriteBuilder favouriteBuilder;
	private Favourite favourite;
	private UserBuilder userBuilder;
	private User user;
	private StoreItemBuilder itemBuilder;
	private StoreItem item;
	private CategoryBuilder categoryBuilder;
	private Category category;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		userBuilder = new UserBuilder("pepego").firstName("pepe").lastName("gonzalez").password("shhhhh");
		user = userDao.create(userBuilder);
		categoryBuilder = new CategoryBuilder("Category", categoryDao.findById(0));
		category = categoryDao.create(categoryBuilder);
		itemBuilder = new StoreItemBuilder("Name", "Desc", new BigDecimal(100), false).category(category).owner(user);
		item = itemDao.create(itemBuilder);
		favouriteBuilder = new FavouriteBuilder(item, user);
	}
	
	@Test
	@Transactional
	public void testCreate() {
		favourite = favouriteDao.createFavourite(favouriteBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "favourites")+1);
	}
	
	@Test
	@Transactional
	public void testFindById() {
		favourite = favouriteDao.createFavourite(favouriteBuilder);
		assertEquals(favourite, favouriteDao.findById(favourite.getId()));
	}
	
	@Test
	@Transactional
	public void testExists() {
		favourite = favouriteDao.createFavourite(favouriteBuilder);
		assertTrue(favouriteDao.exists(favourite));
	}
	
	@Test
	@Transactional
	public void testRemove() {
		favourite = favouriteDao.createFavourite(favouriteBuilder);
		boolean b = favouriteDao.removeFavourite(favourite);
		assertTrue(!b || !favouriteDao.exists(favourite));
	}
	
	@Test
	@Transactional
	public void getFavouritesForUserTest() {
		favourite = favouriteDao.createFavourite(favouriteBuilder);
		assertTrue(favouriteDao.getFavouritesForUser(user).contains(favourite));
	}
}
