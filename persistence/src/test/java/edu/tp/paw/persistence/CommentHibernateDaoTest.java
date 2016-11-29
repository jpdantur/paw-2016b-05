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
import edu.tp.paw.interfaces.dao.ICommentDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CommentHibernateDaoTest {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	private ICommentDao commentDao;
	
	@Autowired
	private IUserDao userDao;
	
	private UserBuilder otherUserBuilder;
	private User otherUser;
	private UserBuilder	userBuilder;
	private User user;
	private CommentBuilder commentBuilder;
	private Comment comment;
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
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		categoryBuilder = new CategoryBuilder("Category", categoryDao.findById(0));
		category = categoryDao.create(categoryBuilder);
		userBuilder = new UserBuilder("Pepe");
		user = userDao.create(userBuilder);
		otherUserBuilder = new UserBuilder("Paco");
		otherUser = userDao.create(otherUserBuilder);
		
		itemBuilder = new StoreItemBuilder("Nombre", "Desc", new BigDecimal(100), false).category(category).owner(user).status(StoreItemStatus.ACTIVE);
		item = itemDao.create(itemBuilder);
		//System.out.println(item.getId());
		commentBuilder = new CommentBuilder(otherUser, "Comentario", 0).item(item);
	}
	
	@Test
	@Transactional
	public void testCreate() {
		comment = commentDao.createComment(commentBuilder);
		assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, "comments")+1);
	}
	
	@Test
	@Transactional
	public void commentsForItemTest(){
		comment = commentDao.createComment(commentBuilder);
		assertTrue(commentDao.commentsForItem(item).contains(comment));
	}
}
