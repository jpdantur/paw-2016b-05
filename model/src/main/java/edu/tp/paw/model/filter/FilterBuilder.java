package edu.tp.paw.model.filter;

import edu.tp.paw.model.IBuilder;

public class FilterBuilder implements IBuilder<Filter> {

	private final PriceFilterBuilder priceFilterBuilder = new PriceFilterBuilder(this);
	
	public FilterBuilder() {
		
	}
	
	public PriceFilterBuilder price() {
		return priceFilterBuilder;
	}
	
	public PriceFilter getPriceFilter() {
		return priceFilterBuilder.build();
	}
	
	@Override
	public Filter build() {
		return new Filter(this);
	}

}