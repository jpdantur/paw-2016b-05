package edu.tp.paw.model.filter;

import edu.tp.paw.model.IBuilder;

public class PageFilterBuilder implements IBuilder<PageFilter> {

	private int pageNumber = 0;
	private int pageSize = 20;
	
	private FilterBuilder filterBuilder;
	
	public PageFilterBuilder() {
		
	}
	
	public PageFilterBuilder(final FilterBuilder filterBuilder) {
		this.filterBuilder = filterBuilder;
	}
	
	public FilterBuilder and() {
		if (filterBuilder != null) {
			return filterBuilder;
		}
		return null;
	}
	
	public FilterBuilder end() {
		if (filterBuilder != null) {
			return filterBuilder;
		}
		return null;
	}
	
	public PageFilterBuilder size(final int pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	public PageFilterBuilder take(final int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	public PageFilter build() {
		return new PageFilter(this);
	}
}
