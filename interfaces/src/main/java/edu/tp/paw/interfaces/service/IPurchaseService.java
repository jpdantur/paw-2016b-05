package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.PurchaseReviewBuilder;

public interface IPurchaseService {

	public Purchase findById(final long id);
	
	public boolean approvePurchase(final Purchase purchase);
	public boolean declinePurchase(final Purchase purchase);

	public boolean updateBuyerReview(final Purchase purchase, final PurchaseReview review);
	public boolean updateSellerReview(final Purchase purchase, final PurchaseReview review);
	
	public PurchaseReview createPurchaseReview(final PurchaseReviewBuilder builder);
	
}
