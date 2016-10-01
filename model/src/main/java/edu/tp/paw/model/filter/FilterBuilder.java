package edu.tp.paw.model.filter;

import edu.tp.paw.model.IBuilder;

public class FilterBuilder implements IBuilder<Filter> {

	private final PriceFilterBuilder priceFilterBuilder = new PriceFilterBuilder(this);
	private final PageFilterBuilder pageFilterBuilder = new PageFilterBuilder(this);
	private final CategoryFilterBuilder categoryFilterBuilder = new CategoryFilterBuilder(this);
	private final TermFilterBuilder termFilterBuilder = new TermFilterBuilder(this);
	
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
	
	public CategoryFilterBuilder category() {
		return categoryFilterBuilder;
	}
	
	public TermFilterBuilder term() {
		return termFilterBuilder;
	}
	
	
	public PriceFilter getPriceFilter() {
		return priceFilterBuilder.build();
	}
	
	public PageFilter getPageFilter() {
		return pageFilterBuilder.build();
	}
	
	public CategoryFilter getCategoryFilter() {
		return categoryFilterBuilder.build();
	}
	
	public TermFilter getTermFilter() {
		return termFilterBuilder.build();
	}
	
	@Override
	public Filter build() {
		return new Filter(this);
	}

}
