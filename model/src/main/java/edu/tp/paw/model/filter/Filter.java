package edu.tp.paw.model.filter;

import java.math.BigDecimal;

public class Filter {

	public enum SortOrder {
		DESC,
		ASC;
	};
	
	private final PriceFilter priceFilter;
	private final PageFilter pageFilter;
	private final CategoryFilter categoryFilter;
	private final TermFilter termFilter;
	
	/* package */ Filter(FilterBuilder builder) {
		this.priceFilter = builder.getPriceFilter();
		this.pageFilter = builder.getPageFilter();
		this.categoryFilter = builder.getCategoryFilter();
		this.termFilter = builder.getTermFilter();
	}
	
	public PriceFilter getPriceFilter() {
		return priceFilter;
	}
	
	public PageFilter getPageFilter() {
		return pageFilter;
	}
	
	public CategoryFilter getCategoryFilter() {
		return categoryFilter;
	}
	
	public TermFilter getTermFilter() {
		return termFilter;
	}
	
}
