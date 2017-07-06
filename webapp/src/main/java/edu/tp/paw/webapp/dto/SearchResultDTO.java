package edu.tp.paw.webapp.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.filter.PagedResult;

@XmlRootElement
public class SearchResultDTO<T> extends PagedResult<T> {
	
	private List<CategoryDTO> similarCategories;
	private List<CategoryDTO> selectedCategories;
	
	public SearchResultDTO() {
		super();
	}

	public SearchResultDTO(int numberOfTotalResults, int numberOfAvailableResults, int pageSize, int currentPage,
			List<T> results, List<CategoryDTO> similarCategories, List<CategoryDTO> selectedCategories) {
		super(numberOfTotalResults, numberOfAvailableResults, pageSize, currentPage, results);
		this.similarCategories = similarCategories;
		this.selectedCategories = selectedCategories;
	}

	public List<CategoryDTO> getSimilarCategories() {
		return similarCategories;
	}

	public void setSimilarCategories(List<CategoryDTO> similarCategories) {
		this.similarCategories = similarCategories;
	}

	public List<CategoryDTO> getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(List<CategoryDTO> selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	@Override
	public String toString() {
		return "SearchResultDTO [similarCategories=" + similarCategories + "]";
	}
	
	
}
