package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.Purchase;

public interface IPurchaseService {

	public Purchase findById(final long id);
	
	public boolean approvePurchase(final Purchase purchase);
	public boolean declinePurchase(final Purchase purchase);
	
}
