package edu.tp.paw.model.filter;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedResult<T> {

	private int numberOfTotalResults;
	private int numberOfAvailableResults;
	private int pageSize;
	private int currentPage; 
	private List<T> results;
	
	public PagedResult() {
		
	}
	
	public PagedResult(
			int numberOfTotalResults,
			int numberOfAvailableResults,
			int pageSize,
			int currentPage,
			List<T> results) {
		super();
		this.numberOfTotalResults = numberOfTotalResults;
		this.numberOfAvailableResults = numberOfAvailableResults;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.results = results;
	}


	public int getNumberOfTotalResults() {
		return numberOfTotalResults;
	}


	public int getNumberOfAvailableResults() {
		return numberOfAvailableResults;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getLastPage() {
		return numberOfTotalResults / pageSize;
	}
	
	public int getFirstPage() {
		return 0;
	}

	public List<T> getResults() {
		return results;
	}


	public void setNumberOfTotalResults(int numberOfTotalResults) {
		this.numberOfTotalResults = numberOfTotalResults;
	}


	public void setNumberOfAvailableResults(int numberOfAvailableResults) {
		this.numberOfAvailableResults = numberOfAvailableResults;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public void setResults(List<T> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "PagedResult [numberOfTotalResults=" + numberOfTotalResults + ", numberOfAvailableResults="
				+ numberOfAvailableResults + ", pageSize=" + pageSize + ", currentPage=" + currentPage + ", results=" + results
				+ "]";
	}
	
	
	
}
