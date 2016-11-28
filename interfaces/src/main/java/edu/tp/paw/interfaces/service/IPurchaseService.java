package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.PurchaseReviewBuilder;
import edu.tp.paw.model.User;

public interface IPurchaseService {

	public Purchase findById(final long id);
	
	public boolean approvePurchase(final Purchase purchase);
	public boolean declinePurchase(final Purchase purchase);

	public boolean updateBuyerReview(final Purchase purchase, final PurchaseReview review);
	public boolean updateSellerReview(final Purchase purchase, final PurchaseReview review);
	
	public PurchaseReview createPurchaseReview(final PurchaseReviewBuilder builder);

	public float getAverageAsBuyer(final User user);

	public float getAverageAsSeller(final User user);
	
}
