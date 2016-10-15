package edu.tp.paw.persistence;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class CommentJdbcDaoTest {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private CommentJdbcDao commentDao;
	
	@Autowired
	private UserJdbcDao userDao;
	private User user;
	
	@Autowired
	private StoreItemJdbcDao storeItemDao;
	private StoreItem storeItem;
	
	@Autowired
	private CategoryJdbcDao categoryDao;
	private Category category;
	

	private JdbcTemplate jdbcTemplate;
	
	private static final String COMMENT = "Esto es un comentario";

	private static final String OTHER_COMMENT = "Esto es otro comentario";

	private static final String CATEGORY = "root";

	private static final String USERNAME = "Nombre de usuario";

	private static final String NAME = "Nombre Item";

	private static final String DESCRIPTION = "Descripcion";

	private static final BigDecimal PRICE = new BigDecimal(123);

	private static final boolean USED = false;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "comments");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "store_items");
		JdbcTestUtils.deleteFromTableWhere(jdbcTemplate, "store_categories", "category_id != 0");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		category = categoryDao.create(new CategoryBuilder(CATEGORY,0));
		user = userDao.create(new UserBuilder(USERNAME));
		storeItem = storeItemDao.create(new StoreItemBuilder(NAME, DESCRIPTION, PRICE, category, USED).owner(user));
	}
	
	@Test
	public void testCreate() {
		CommentBuilder commentBuilder = new CommentBuilder(user, COMMENT).item(storeItem);
		final Comment comment = commentDao.createComment(commentBuilder);
		
		assertNotNull(comment);
		assertEquals(commentBuilder.build(),comment);
		
	}
	
	@Test
	public void testCommentsForItem() {
		CommentBuilder commentBuilder = new CommentBuilder(user,COMMENT).item(storeItem);
		CommentBuilder otherBuilder = new CommentBuilder(user,OTHER_COMMENT).item(storeItem);
		final Comment comment = commentDao.createComment(commentBuilder);
		final Comment otherComment = commentDao.createComment(otherBuilder);
		List<Comment> commentList = commentDao.commentsForItem(storeItem);
		assertEquals(2,commentList.size());
		assertTrue(commentList.contains(comment));
		assertTrue(commentList.contains(otherComment));
		
	}
}
