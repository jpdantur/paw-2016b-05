package edu.tp.paw.model.filter;

public class Filter {
	
	private final PriceFilter priceFilter;
	private final PageFilter pageFilter;
	private final CategoryFilter categoryFilter;
	private final TermFilter termFilter;
	private final OrderFilter orderFilter;
	private final StoreItemStatusFilter storeItemStatusFilter;
	private final PurchaseStatusFilter purchaseStatusFilter;
	
	/* package */ Filter(final FilterBuilder builder) {
		this.priceFilter = builder.getPriceFilter();
		this.pageFilter = builder.getPageFilter();
		this.categoryFilter = builder.getCategoryFilter();
		this.termFilter = builder.getTermFilter();
		this.orderFilter = builder.getOrderFilter();
		this.storeItemStatusFilter = builder.getStoreItemStatusFilter();
		this.purchaseStatusFilter = builder.getPurchaseStatusFilter();
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

	public StoreItemStatusFilter getStoreItemStatusFilter() {
		return storeItemStatusFilter;
	}
	
	public PurchaseStatusFilter getPurchaseStatusFilter() {
		return purchaseStatusFilter;
	}
	
	
	
}
