package edu.tp.paw.webapp.form;

import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;

public class ProfileItemSearchForm {

	private static final int NUMBER_OF_ITEMS_PER_PAGE = 20;
	
	private int pageNumber = 0;
	private int pageSize = NUMBER_OF_ITEMS_PER_PAGE;
	private String query;
	private String sortOrder = "ASC";
	private String orderBy = "PRICE";
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public SortField orderBy() {
		return SortField.valueOf(orderBy);
	}
	
	public SortOrder sortOrder() {
		return SortOrder.valueOf(sortOrder);
	}
	@Override
	public String toString() {
		return "ProfileItemSearchForm [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", query=" + query
				+ ", sortOrder=" + sortOrder + ", orderBy=" + orderBy + "]";
	}
	
}
