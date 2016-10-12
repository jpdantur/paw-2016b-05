package edu.tp.paw.persistence;

import static org.junit.Assert.*;

import java.util.List;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CategoryJdbcDaoTest {
	
	@Autowired
	private DataSource ds;
	@Autowired
	private CategoryJdbcDao categoryDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTableWhere(jdbcTemplate, "store_categories", "category_id != 0");
	}
	
	@Test
	public void testCreate() {
		System.out.println("Create");
		CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		final Category category = categoryDao.create(categoryBuilder);
		
		assertNotNull(category);
		assertEquals(categoryBuilder.build(), category);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "store_categories") - 1); //substract root
	}
	
	@Test
	public void testFindById() {
		System.out.println("Find by id");
		CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		categoryDao.create(categoryBuilder);
		Category result = categoryDao.findById(categoryBuilder.getId());
		assertEquals(categoryBuilder.build(),result);
	}
	
	@Test
	public void testCategoryExists() {
		System.out.println("Category exists");
		CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		categoryDao.create(categoryBuilder);
		assertTrue(categoryDao.categoryExists(categoryBuilder.getId()));
	}
	
	@Test
	public void testGetSiblings() {
		System.out.println("get siblings");
		CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		Category category = categoryDao.create(categoryBuilder);
		CategoryBuilder otherCategoryBuilder = new CategoryBuilder("BAR", categoryBuilder.getParent());
		Category otherCategory = categoryDao.create(otherCategoryBuilder);
		List<Category> siblings = categoryDao.getSiblings(category);
		assertEquals(2, siblings.size() - 1); //substract root
		assertTrue(siblings.contains(otherCategory));
	}
	
	@Test
	public void testGetChildren() {
		System.out.println("get children");
		CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		Category category = categoryDao.create(categoryBuilder);
		CategoryBuilder otherCategoryBuilder = new CategoryBuilder("BAR", categoryBuilder.getId());
		Category otherCategory = categoryDao.create(otherCategoryBuilder);
		List<Category> children = categoryDao.getChildren(category);
		assertEquals(1,children.size());
		assertTrue(children.contains(otherCategory));
	}
	
	@Test
	public void testGetDescendants() {
		System.out.println("get descendants");
		CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		Category category = categoryDao.create(categoryBuilder);
		CategoryBuilder sonCategoryBuilder = new CategoryBuilder("BAR", categoryBuilder.getId());
		Category sonCategory = categoryDao.create(sonCategoryBuilder);
		CategoryBuilder grandsonCategoryBuilder = new CategoryBuilder("TEST", sonCategoryBuilder.getId());
		Category grandsonCategory = categoryDao.create(grandsonCategoryBuilder);
		List<Category> descendants = categoryDao.getDescendants(category);
		assertEquals(2,descendants.size());
		//System.out.println(descendants.get(0) + " || " + descendants.get(1));
		assertTrue(descendants.contains(sonCategory));
		assertTrue(descendants.contains(grandsonCategory));
	}
}
