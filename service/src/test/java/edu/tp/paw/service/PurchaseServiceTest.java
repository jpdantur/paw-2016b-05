package edu.tp.paw.service;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.tp.paw.interfaces.service.IPurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PurchaseServiceTest {

	@Autowired private IPurchaseService service;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindById() {
		
	}

	@Test
	public void testApprovePurchase() {
		
	}

	@Test
	public void testDeclinePurchase() {
		
	}

	@Test
	public void testUpdateBuyerReview() {
		
	}

	@Test
	public void testUpdateSellerReview() {
		
	}

	@Test
	public void testCreatePurchaseReview() {
		
	}

	@Test
	public void testGetAverageAsBuyer() {
		
	}

	@Test
	public void testGetAverageAsSeller() {
		
	}

}
