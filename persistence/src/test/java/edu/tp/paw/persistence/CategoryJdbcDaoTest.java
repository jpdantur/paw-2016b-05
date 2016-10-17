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
	
	@Autowired private DataSource ds;
	@Autowired private CategoryJdbcDao categoryDao;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTableWhere(jdbcTemplate, "store_categories", "category_id != 0");
	}
	
	@Test
	public void testCreate() {
		final CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		final Category category = categoryDao.create(categoryBuilder);
		
		assertNotNull(category);
		assertEquals(categoryBuilder.build(), category);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "store_categories") - 1); //substract root
	}
	
	@Test
	public void testFindById() {
		final CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		categoryDao.create(categoryBuilder);
		final Category result = categoryDao.findById(categoryBuilder.getId());
		assertEquals(categoryBuilder.build(),result);
	}
	
	@Test
	public void testCategoryExists() {
		final CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		categoryDao.create(categoryBuilder);
		assertTrue(categoryDao.categoryExists(categoryBuilder.getId()));
	}
	
	@Test
	public void testGetSiblings() {
		final CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		final Category category = categoryDao.create(categoryBuilder);
		final CategoryBuilder otherCategoryBuilder = new CategoryBuilder("BAR", categoryBuilder.getParent());
		final Category otherCategory = categoryDao.create(otherCategoryBuilder);
		final List<Category> siblings = categoryDao.getSiblings(category);
		assertEquals(2, siblings.size() - 1); //substract root
		assertTrue(siblings.contains(otherCategory));
	}
	
	@Test
	public void testGetChildren() {
		final CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		final Category category = categoryDao.create(categoryBuilder);
		final CategoryBuilder otherCategoryBuilder = new CategoryBuilder("BAR", categoryBuilder.getId());
		final Category otherCategory = categoryDao.create(otherCategoryBuilder);
		final List<Category> children = categoryDao.getChildren(category);
		assertEquals(1,children.size());
		assertTrue(children.contains(otherCategory));
	}
	
	@Test
	public void testGetDescendants() {
		final CategoryBuilder categoryBuilder = new CategoryBuilder("FOO",0);
		final Category category = categoryDao.create(categoryBuilder);
		final CategoryBuilder sonCategoryBuilder = new CategoryBuilder("BAR", categoryBuilder.getId());
		final Category sonCategory = categoryDao.create(sonCategoryBuilder);
		final CategoryBuilder grandsonCategoryBuilder = new CategoryBuilder("TEST", sonCategoryBuilder.getId());
		final Category grandsonCategory = categoryDao.create(grandsonCategoryBuilder);
		final List<Category> descendants = categoryDao.getDescendants(category);
		assertEquals(2,descendants.size());
		assertTrue(descendants.contains(sonCategory));
		assertTrue(descendants.contains(grandsonCategory));
	}
}
