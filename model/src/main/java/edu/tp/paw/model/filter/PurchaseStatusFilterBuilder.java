package edu.tp.paw.model.filter;

import edu.tp.paw.model.IBuilder;
import edu.tp.paw.model.PurchaseStatus;

public class PurchaseStatusFilterBuilder implements IBuilder<PurchaseStatusFilter> {

	private FilterBuilder filterBuilder;
	
	private PurchaseStatus status = PurchaseStatus.PENDING;
	
	public PurchaseStatusFilterBuilder(final FilterBuilder filterBuilder) {
		this.filterBuilder = filterBuilder;
	}
	
	public PurchaseStatusFilterBuilder status(final PurchaseStatus status) {
		this.status = status;
		return this;
	}
	
	public FilterBuilder and() {
		return filterBuilder;
	}
	
	public FilterBuilder end() {
		return filterBuilder;
	}
	
	@Override
	public PurchaseStatusFilter build() {
		return new PurchaseStatusFilter(this);
	}

	public PurchaseStatus getStatus() {
		return status;
	}
	
}
