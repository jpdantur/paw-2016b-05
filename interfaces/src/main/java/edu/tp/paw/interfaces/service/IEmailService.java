package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.User;

public interface IEmailService {
	
	public boolean greet(final User user);
	
	public boolean notifySale(final User user, final Purchase sale);
	
	public boolean notifyPurchaseApproval(final User user, final Purchase purchase);
	public boolean notifyPurchaseDeclined(final User user, final Purchase purchase);
	
	public boolean notifySellerAboutReview(final User user, final Purchase purchase, final PurchaseReview review);
	public boolean notifyBuyerAboutReview(final User user, final Purchase purchase, final PurchaseReview review);
	
	public boolean sendRawEmail(final User user, final String subject, final String body);

	
}
