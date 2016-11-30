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
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
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
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource ds;
	private Category category;
	private User user;
	private StoreItem item;
	private PurchaseBuilder purchaseBuilder;
	private PurchaseReviewBuilder purchaseReviewBuilder;
	private PurchaseReview purchaseReview;
	
	@Before
	@Transactional
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("insert into store_categories(category_id, category_name, parent) values (0, 'root', 0);");
		//category = categoryService.findById(0);
		//user = userService.createUser(new UserBuilder("pepe"), new RoleBuilder("name","slug").id(0).build());
		//item = storeItemService.create(new StoreItemBuilder("name","desc",new BigDecimal(100),false).category(category).owner(user));
		//purchase = 
		
		purchaseReviewBuilder = new PurchaseReviewBuilder("comment");
		purchaseReview = purchaseService.createPurchaseReview(purchaseReviewBuilder);
		
		
	}

	@Test
	@Transactional
	public void testFindById() {
		assertEquals(purchaseReview,purchaseService.findById(purchaseReview.getId()));
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
