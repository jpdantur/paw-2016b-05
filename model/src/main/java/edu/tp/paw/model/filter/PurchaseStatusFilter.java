package edu.tp.paw.model.filter;

import edu.tp.paw.model.PurchaseStatus;

public class PurchaseStatusFilter {
	
	private final PurchaseStatus status;
	
	/* package */ PurchaseStatusFilter(final PurchaseStatusFilterBuilder builder) {
		this.status = builder.getStatus();
	}

	public PurchaseStatus getStatus() {
		return status;
	}
	
	
}
