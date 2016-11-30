package edu.tp.paw.service;

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

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.service.TestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CategoryServiceTest {

	
	private static final String CATEGORY_NAME = "Testing is fun :D";
	private static final String BROTHER_NAME = "Shut up brother!";
	private static final String SON_NAME = "Daddy, buy me Effective Java!";

	@Autowired private CategoryService categoryService;
	
	@Autowired DataSource ds;
	
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
//		categoryService = new CategoryService();
		//rootBuilder = new CategoryBuilder("ROOT", root);
		root = categoryService.findById(0);
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
		category = categoryService.create(categoryBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "store_categories"));
	}
	
	@Test
	@Transactional
	public void findByIdTest() {
		category = categoryService.create(categoryBuilder);
		assertEquals(category,categoryService.findById(category.getId()));
	}
	
	@Test
	@Transactional
	public void categoryExistsTest() {
		category = categoryService.create(categoryBuilder);
		assertTrue(categoryService.exists(category.getId()));
	}
	

	
	@Test
	@Transactional
	public void getChildrenTest() {
		category = categoryService.create(categoryBuilder);
		sonBuilder = new CategoryBuilder(SON_NAME, category);
		sonCategory = categoryService.create(sonBuilder);
		assertEquals(1,categoryService.getChildren(category).size());
	}
	
}
