package edu.tp.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IPurchaseDao;
import edu.tp.paw.interfaces.service.IPurchaseService;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseStatus;

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

}
