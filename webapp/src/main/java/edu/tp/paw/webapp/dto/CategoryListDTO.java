package edu.tp.paw.webapp.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.Category;

@XmlRootElement
public class CategoryListDTO {

	private List<CategoryDTO> categories;
	
	public CategoryListDTO() {
		
	}
	
	public CategoryListDTO(Collection<Category> categories) {
		this.categories = categories.stream().map(v -> new CategoryDTO(v)).collect(Collectors.toList());
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "CategoryListDTO [categories=" + categories + "]";
	}
}
