package edu.tp.paw.model.filter;

import java.math.BigDecimal;

public class PriceFilter {
	
	private final Range<BigDecimal> priceRange;
	
	/* package */ PriceFilter(PriceFilterBuilder builder) {
		this.priceRange = builder.getPriceRange();
	}

	public Range<BigDecimal> getPriceRange() {
		return priceRange;
	}
}
