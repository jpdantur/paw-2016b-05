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
	
	@Autowired
	private StoreItemJdbcDao storeItemDao;
	
	@Autowired
	private CategoryJdbcDao categoryDao;
	

	private JdbcTemplate jdbcTemplate;
	
	private static final String COMMENT = "Esto es un comentario";

	private static final String OTHER_COMMENT = "Esto es otro comentario";
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "comments");
	}
	
	@Test
	public void testCreate() {
		User user = createUser("Create", "create@c.com");
		Category category = createCategory("Creategory",0);
		StoreItem storeItem = createItem("ItemCreate","Very Nice",new BigDecimal(123),category,false,user);
		
		final Comment comment = commentDao.createComment(user, storeItem, COMMENT);
		
		assertNotNull(comment);
		assertEquals(new CommentBuilder(user,COMMENT).item(storeItem).id(comment.getId()).build(),comment);
		
	}
	
	@Test
	public void testCommentsForItem() {
		User user = createUser("List", "list@c.com");
		Category category = createCategory("Listegory",0);
		StoreItem storeItem = createItem("ItemList","Very Nice",new BigDecimal(123),category,false,user);
		final Comment comment = commentDao.createComment(user, storeItem, COMMENT);
		final Comment otherComment = commentDao.createComment(user, storeItem, OTHER_COMMENT);
		List<Comment> commentList = commentDao.commentsForItem(storeItem);
		assertEquals(2,commentList.size());
		assertTrue(commentList.contains(comment));
		assertTrue(commentList.contains(otherComment));
		
	}

	private StoreItem createItem(String name, String description, BigDecimal price, Category category, boolean used, User user) {
		return storeItemDao.create(new StoreItemBuilder("ItemName","Description",new BigDecimal(123),category,false).owner(user));
	}

	private User createUser(String username, String email) {
		return userDao.create(new UserBuilder(username).email(email));
	}

	private Category createCategory(String name, int parent) {
		return categoryDao.create(new CategoryBuilder(name,parent));
	}
}
