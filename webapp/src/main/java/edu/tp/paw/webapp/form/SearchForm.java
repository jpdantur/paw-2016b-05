package edu.tp.paw.webapp.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;

public class SearchForm {
	
	private static final int NUMBER_OF_ITEMS_PER_PAGE = 20;
	
	private int pageNumber = 0;
	private int pageSize = NUMBER_OF_ITEMS_PER_PAGE;
	private String query;
	private String sortOrder = "ASC";	
	private String orderBy = "PRICE";
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private List<Long> categories = new ArrayList<>();
	
	public SearchForm() {
	}
	
	public SearchForm(int pageNumber, int pageSize, String query,
			String sortOrder, String orderBy, BigDecimal minPrice,
			BigDecimal maxPrice, List<Long> categories) {
		super();
		
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.query = query;
		if (sortOrder != null) {
			this.sortOrder = sortOrder;
		}
		if (orderBy != null) {
			this.orderBy = orderBy;
		}
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		if (categories != null) {
			this.categories = categories;
		}
	}

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
		if (sortOrder != null) {
			this.sortOrder = sortOrder.toUpperCase();
		}
	}

	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		if (orderBy != null) {
			this.orderBy = orderBy.toUpperCase();
		}
	}
	
	public SortField orderBy() {
		return SortField.valueOf(orderBy);
	}
	
	public SortOrder sortOrder() {
		return SortOrder.valueOf(sortOrder);
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories(List<Long> categories) {
		if (categories != null) {
			this.categories = categories;
		}
	}
	
	
	
}
