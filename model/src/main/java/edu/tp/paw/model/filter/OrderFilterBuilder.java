package edu.tp.paw.model.filter;

import edu.tp.paw.model.IBuilder;
import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;

public class OrderFilterBuilder implements IBuilder<OrderFilter> {

	private FilterBuilder filterBuilder;
	
	private SortField field = SortField.PRICE;
	private SortOrder order = SortOrder.ASC;
	
	public OrderFilterBuilder(FilterBuilder filterBuilder) {
		this.filterBuilder = filterBuilder;
	}
	
	public OrderFilterBuilder by(SortField field) {
		this.field = field;
		return this;
	}
	
	public OrderFilterBuilder order(SortOrder order) {
		this.order = order;
		return this;
	}
	
	public FilterBuilder and() {
		return filterBuilder;
	}
	
	public FilterBuilder end() {
		return filterBuilder;
	}
	
	public SortOrder getOrder() {
		return order;
	}
	
	public SortField getField() {
		return field;
	}
	
	@Override
	public OrderFilter build() {
		return new OrderFilter(this);
	}

	
	
}
