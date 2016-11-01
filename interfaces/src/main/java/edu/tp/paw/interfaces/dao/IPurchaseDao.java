package edu.tp.paw.interfaces.dao;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseStatus;

public interface IPurchaseDao {

	public Purchase findById(final long id);
	
	public boolean updatePurchaseStatus(final Purchase purchase, final PurchaseStatus status);
	
}
