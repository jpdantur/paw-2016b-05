package edu.tp.paw.persistence;

import static org.junit.Assert.assertEquals;
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
import edu.tp.paw.interfaces.dao.IImageDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StoreImageHibernateDaoTest {
	
	private final static Logger logger = LoggerFactory.getLogger(StoreImageHibernateDaoTest.class);
	
	@Autowired
	DataSource ds;
	
	@Autowired
	private IImageDao imageDao;
	
	private StoreImageBuilder imageBuilder;
	private StoreImage image;
	@Autowired
	private IUserDao userDao;
	
	private UserBuilder	userBuilder;
	private User user;
	private JdbcTemplate jdbcTemplate;

	private CategoryBuilder categoryBuilder;

	@Autowired
	private ICategoryDao categoryDao;

	private Category category;

	private StoreItemBuilder itemBuilder;

	@Autowired
	private IStoreItemDao itemDao;

	private StoreItem item;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		logger.trace("setUp start");
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		categoryBuilder = new CategoryBuilder("Category", categoryDao.findById(0));
		category = categoryDao.create(categoryBuilder);
		userBuilder = new UserBuilder("Pepe");
		user = userDao.create(userBuilder);
		
		itemBuilder = new StoreItemBuilder("Nombre", "Desc", new BigDecimal(100), false).category(category).owner(user).status(StoreItemStatus.ACTIVE);
		item = itemDao.create(itemBuilder);
		imageBuilder = new StoreImageBuilder("Foto", new byte[200] ).item(item);
		logger.trace("setUp end");
	}
	
	@Test
	@Transactional
	public void testCreate() {
		image = imageDao.createImage(imageBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "images")+1);
	}
	
	@Test
	@Transactional
	public void testFindById() {
		image = imageDao.createImage(imageBuilder);
		assertEquals(image,imageDao.findById(image.getId()));
	}
	
	@Test
	@Transactional
	public void testImagesForItem() {
		logger.trace("testImagesForItem start");
		image = imageDao.createImage(imageBuilder);
		assertTrue(imageDao.imagesForItem(item).contains(image));
		logger.trace("testImagesForItem end");
	}
}