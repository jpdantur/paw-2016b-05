package edu.tp.paw.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.ICommentService;
import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CommentServiceTest {

	@Autowired private ICommentService commentService;
	@Autowired private IUserService userService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IStoreItemService itemService;
	@Autowired private IRoleService roleService;
	
	@Autowired DataSource ds;
	private JdbcTemplate jdbcTemplate;
	
	private Comment comment;
	private StoreItem item;
	private Category root;
	private User user;
	
	@Transactional
	@Before
	public void setUp() throws Exception {
		
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		
		root = categoryService.findById(0);
		
		RoleBuilder roleBuilder = new RoleBuilder("Store User", "store-user").makeDefault(true);
		Role role = roleService.createRole(roleBuilder);
		
		UserBuilder userBuilder = new UserBuilder("testuser").firstName("test").lastName("user").password("pass");
		user = userService.createUser(userBuilder, role);
		
		StoreItemBuilder builder = new StoreItemBuilder("item", "desc", new BigDecimal(10), false).category(root).owner(user);
		item = itemService.create(builder);
		
		CommentBuilder commentBuilder = new CommentBuilder(user, "example content", 3).item(item);
		comment = commentService.createComment(commentBuilder);
		
	}

	@Test
	@Transactional
	public void testCreateComment() {
		assertNotNull(comment);
	}

	@Test
	@Transactional
	public void testCommentsForItem() {
		assertTrue(commentService.commentsForItem(item).contains(comment));
	}
	
	@Test( expected = IllegalArgumentException.class )
	@Transactional
	public void testCommentsForMissingItem() {
		StoreItem i = new StoreItemBuilder("item", "desc", new BigDecimal(10), false).category(root).owner(user).id(24).build();
		assertTrue(commentService.commentsForItem(i).contains(comment));
	}

}
