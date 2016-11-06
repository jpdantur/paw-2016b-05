package edu.tp.paw.model.filter;

import edu.tp.paw.model.IBuilder;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;

public class StoreItemStatusFilterBuilder implements IBuilder<StoreItemStatusFilter> {

	private FilterBuilder filterBuilder;
	
	private ItemStatusFilter status = ItemStatusFilter.ANY;
	
	public StoreItemStatusFilterBuilder(final FilterBuilder filterBuilder) {
		this.filterBuilder = filterBuilder;
	}
	
	public StoreItemStatusFilterBuilder status(final ItemStatusFilter status) {
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
	public StoreItemStatusFilter build() {
		return new StoreItemStatusFilter(this);
	}

	public ItemStatusFilter getStatus() {
		return status;
	}
	
}
