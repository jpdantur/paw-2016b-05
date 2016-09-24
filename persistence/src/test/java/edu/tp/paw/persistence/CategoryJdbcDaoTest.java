package edu.tp.paw.persistence;

import static org.junit.Assert.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CategoryJdbcDaoTest {
	private static final String NAME = "Categoria";
	private static final long PARENT = 3;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private CategoryJdbcDao categoryDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "store_categories");
	}
	
	@Test
	public void testCreate() {
		final Category category = categoryDao.create(NAME, PARENT);
		
		assertNotNull(category);
		assertEquals(NAME, category.getName());
		assertEquals(PARENT, category.getParent());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "store_categories"));
	}
}
