package edu.tp.paw.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PurchaseBuilderTest {

	private StoreItem item = new StoreItem();
	private User user = new User();
	private PurchaseReview buyerReview = new PurchaseReview();

	private PurchaseReview sellerReview = new PurchaseReview();

	@Test
	public void testBuild() {
		final Purchase purchase = new PurchaseBuilder(user, item).buyerReview(buyerReview).sellerReview(sellerReview).build();
		assertEquals(item,purchase.getItem());
		assertEquals(user,purchase.getBuyer());
		assertEquals(buyerReview,purchase.getBuyerReview());
		assertEquals(sellerReview,purchase.getSellerReview());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullItem() {
		final Purchase purchase = new PurchaseBuilder(user, null).build();
		assertEquals(item,purchase.getItem());
		assertEquals(user,purchase.getBuyer());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullUser() {
		final Purchase purchase = new PurchaseBuilder(null, item).build();
		assertEquals(item,purchase.getItem());
		assertEquals(user,purchase.getBuyer());
	}
}
