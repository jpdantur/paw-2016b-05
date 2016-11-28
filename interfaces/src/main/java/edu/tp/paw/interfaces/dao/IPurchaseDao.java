package edu.tp.paw.interfaces.dao;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.PurchaseReviewBuilder;
import edu.tp.paw.model.PurchaseStatus;

public interface IPurchaseDao {

	public Purchase findById(final long id);
	
	public boolean updatePurchaseStatus(final Purchase purchase, final PurchaseStatus status);
	
	public PurchaseReview createPurchaseReview(final PurchaseReviewBuilder builder);
	
	public boolean updatePurchaseBuyerReview(final Purchase purchase, final PurchaseReview review);
	public boolean updatePurchaseSellerReview(final Purchase purchase, final PurchaseReview review);
	
}
