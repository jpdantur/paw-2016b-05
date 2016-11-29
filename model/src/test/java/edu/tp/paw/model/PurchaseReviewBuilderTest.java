package edu.tp.paw.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PurchaseReviewBuilderTest {

	
	private static final String COMMENT = "Comment";

	@Test
	public void testBuild() {
		final PurchaseReview review = new PurchaseReviewBuilder(COMMENT).build();
		assertEquals(COMMENT,review.getComment());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullComment() {
		final PurchaseReview review = new PurchaseReviewBuilder(null).build();
		assertEquals(COMMENT,review.getComment());
	}
}
