package edu.tp.paw.service;


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
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IPurchaseService;
import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.PurchaseReviewBuilder;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PurchaseServiceTest {

	@Autowired private IPurchaseService purchaseService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IStoreItemService storeItemService;
	@Autowired private IUserService userService;
	@Autowired private IRoleService roleService;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource ds;
	private Category category;
	private User user;
	private StoreItem item;
	private Purchase purchase;
	private PurchaseBuilder purchaseBuilder;
	private PurchaseReviewBuilder purchaseReviewBuilder;
	private PurchaseReview purchaseReview;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		jdbcTemplate.execute("insert into roles (role_id, role_name, role_slug, default_role) values (1, 'Store User', 'USER', true);");
		category = categoryService.findById(0);
		UserBuilder userBuilder = new UserBuilder("pepepe").firstName("pepe").lastName("gil").password("allala");
		user = userService.createUser(userBuilder, roleService.findRoleById(1));
		item = storeItemService.create(new StoreItemBuilder("name","desc",new BigDecimal(100),false).category(category).owner(user));
		
		purchaseBuilder = new PurchaseBuilder(user, item);
		
		purchase = userService.purchase(purchaseBuilder);
		
		purchaseReviewBuilder = new PurchaseReviewBuilder("comment");
	}

	@Test
	@Transactional
	public void testFindById() {
		assertEquals(purchase,purchaseService.findById(purchase.getId()));
//		assertEquals(purchaseReview,purchaseService.findById(purchaseReview.getId()));
	}

	@Test
	@Transactional
	public void testApprovePurchase() {
		
	}

	@Test
	@Transactional
	public void testDeclinePurchase() {
		
	}

	@Test
	@Transactional
	public void testUpdateBuyerReview() {
		
	}

	@Test
	@Transactional
	public void testUpdateSellerReview() {
		
	}

	@Test
	@Transactional
	public void testCreatePurchaseReview() {
		
	}

	@Test
	@Transactional
	public void testGetAverageAsBuyer() {
		
	}

	@Test
	@Transactional
	public void testGetAverageAsSeller() {
		
	}

}
