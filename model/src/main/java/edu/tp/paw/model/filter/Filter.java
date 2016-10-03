package edu.tp.paw.model.filter;

public class Filter {
	
	private final PriceFilter priceFilter;
	private final PageFilter pageFilter;
	private final CategoryFilter categoryFilter;
	private final TermFilter termFilter;
	private final OrderFilter orderFilter;
	
	/* package */ Filter(FilterBuilder builder) {
		this.priceFilter = builder.getPriceFilter();
		this.pageFilter = builder.getPageFilter();
		this.categoryFilter = builder.getCategoryFilter();
		this.termFilter = builder.getTermFilter();
		this.orderFilter = builder.getOrderFilter();
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
	
	public OrderFilter getOrderFilter() {
		return orderFilter;
	}
	
}
