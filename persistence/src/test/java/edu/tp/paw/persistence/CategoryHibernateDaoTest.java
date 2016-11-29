package edu.tp.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CategoryHibernateDaoTest {

	
	private static final String CATEGORY_NAME = "Testing is fun :D";

	private static final String BROTHER_NAME = "Shut up brother!";

	private static final String SON_NAME = "Daddy, buy me Effective Java!";

	@Autowired
	private ICategoryDao categoryDao;
	
	@Autowired
	DataSource ds;
	
	private JdbcTemplate jdbcTemplate;
	private Category root;
	private Category category;
	private Category sonCategory;
	private Category brotherCategory;
	
	private CategoryBuilder rootBuilder;
	private CategoryBuilder categoryBuilder;
	private CategoryBuilder brotherBuilder;
	private CategoryBuilder sonBuilder;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		//rootBuilder = new CategoryBuilder("ROOT", root);
		root = categoryDao.findById(0);
		//System.out.println(categoryDao.categoryExists(0));
		//root = categoryDao.create(rootBuilder);
		//System.out.println(categoryDao.getNumberOfCategories());
		categoryBuilder = new CategoryBuilder(CATEGORY_NAME, root);
		brotherBuilder = new CategoryBuilder(BROTHER_NAME, root);
		//sonBuilder = new CategoryBuilder(SON_NAME,categoryBuilder.build());
	}
	
	
	@Test
	@Transactional
	public void createTest() {
		category = categoryDao.create(categoryBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "store_categories"));
	}
	
	@Test
	@Transactional
	public void findByIdTest() {
		category = categoryDao.create(categoryBuilder);
		assertEquals(category,categoryDao.findById(category.getId()));
	}
	
	@Test
	@Transactional
	public void categoryExistsTest() {
		category = categoryDao.create(categoryBuilder);
		assertTrue(categoryDao.categoryExists(category.getId()));
	}
	
	@Test
	@Transactional
	public void getSiblingsTest() {
		category = categoryDao.create(categoryBuilder);
		brotherCategory = categoryDao.create(brotherBuilder);
		assertEquals(3,categoryDao.getSiblings(category).size()); //Me, my brother and root
		
	}
	
	/*@Test
	@Transactional
	public void getDescendantsTest() {
		category = categoryDao.create(categoryBuilder);
		sonBuilder = new CategoryBuilder(SON_NAME, category);
		sonCategory = categoryDao.create(sonBuilder);
		assertEquals(1,categoryDao.getDescendants(category).size());
	}*/
	
	@Test
	@Transactional
	public void getChildrenTest() {
		category = categoryDao.create(categoryBuilder);
		sonBuilder = new CategoryBuilder(SON_NAME, category);
		sonCategory = categoryDao.create(sonBuilder);
		assertEquals(1,categoryDao.getChildren(category).size());
	}
	
}
