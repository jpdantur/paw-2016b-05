package edu.tp.paw.model.filter;

import java.math.BigDecimal;

public class PriceFilter {
	
	private final Range<BigDecimal> priceRange;
	private final Filter.SortOrder sortOrder;
	
	/* package */ PriceFilter(PriceFilterBuilder builder) {
		this.priceRange = builder.getPriceRange();
		this.sortOrder = builder.getSortOrder();
	}

	public Range<BigDecimal> getPriceRange() {
		return priceRange;
	}

	public Filter.SortOrder getSortOrder() {
		return sortOrder;
	}
	
	
	
}
