package edu.tp.paw.model.filter;

public class PageFilter {
	
	private final int pageSize;
	private final int pageNumber;
	
	
	/* package */ PageFilter(PageFilterBuilder builder) {
		this.pageSize = builder.getPageSize();
		this.pageNumber = builder.getPageNumber();
	}


	public int getPageSize() {
		return pageSize;
	}


	public int getPageNumber() {
		return pageNumber;
	}
	
	
}
