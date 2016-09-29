package edu.tp.paw.model.filter;

import java.math.BigDecimal;

public class Filter {

	public enum SortOrder {
		DESC,
		ASC;
	};
	
	private final PriceFilter priceFilter;
	
	/* package */ Filter(FilterBuilder builder) {
		this.priceFilter = builder.getPriceFilter();
	}
	
	public PriceFilter getPriceFilter() {
		return priceFilter;
	}
	
}
