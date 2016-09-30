package edu.tp.paw.model.filter;

import edu.tp.paw.model.IBuilder;

public class FilterBuilder implements IBuilder<Filter> {

	private final PriceFilterBuilder priceFilterBuilder = new PriceFilterBuilder(this);
	private final PageFilterBuilder pageFilterBuilder = new PageFilterBuilder(this);
	
	private FilterBuilder() {
		
	}
	
	public static FilterBuilder create() {
		return new FilterBuilder();
	}
	
	public PriceFilterBuilder price() {
		return priceFilterBuilder;
	}
	
	public PageFilterBuilder page() {
		return pageFilterBuilder;
	}
	
	public PriceFilter getPriceFilter() {
		return priceFilterBuilder.build();
	}
	
	public PageFilter getPageFilter() {
		return pageFilterBuilder.build();
	}
	
	@Override
	public Filter build() {
		return new Filter(this);
	}

}
