package edu.tp.paw.model.filter;

import java.math.BigDecimal;

public class Filter {

	public enum SortOrder {
		DESC,
		ASC;
	};
	
	private final PriceFilter priceFilter;
	private final PageFilter pageFilter;
	
	/* package */ Filter(FilterBuilder builder) {
		this.priceFilter = builder.getPriceFilter();
		this.pageFilter = builder.getPageFilter();
	}
	
	public PriceFilter getPriceFilter() {
		return priceFilter;
	}
	
	public PageFilter getPageFilter() {
		return pageFilter;
	}
	
}
