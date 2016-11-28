package edu.tp.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IPurchaseDao;
import edu.tp.paw.interfaces.service.IPurchaseService;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.PurchaseReviewBuilder;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.User;

@Service
public class PurchaseService implements IPurchaseService {

	@Autowired private IPurchaseDao purchaseDao;
	
	@Override
	public Purchase findById(final long id) {
		
		return purchaseDao.findById(id);
	}

	@Override
	public boolean approvePurchase(final Purchase purchase) {
		
		return purchaseDao.updatePurchaseStatus(purchase, PurchaseStatus.APPROVED);
	}

	@Override
	public boolean declinePurchase(final Purchase purchase) {
		
		return purchaseDao.updatePurchaseStatus(purchase, PurchaseStatus.DECLINED);
	}

	@Override
	public boolean updateBuyerReview(final Purchase purchase, final PurchaseReview review) {
		return purchaseDao.updatePurchaseBuyerReview(purchase, review);
	}

	@Override
	public boolean updateSellerReview(final Purchase purchase, final PurchaseReview review) {
		return purchaseDao.updatePurchaseSellerReview(purchase, review);
	}

	@Override
	public PurchaseReview createPurchaseReview(final PurchaseReviewBuilder builder) {
		return purchaseDao.createPurchaseReview(builder);
	}

	@Override
	public float getAverageAsBuyer(final User user) {
		return purchaseDao.getAverageAsBuyer(user);
	}

	@Override
	public float getAverageAsSeller(final User user) {
		
		return purchaseDao.getAverageAsSeller(user);
	}

}
