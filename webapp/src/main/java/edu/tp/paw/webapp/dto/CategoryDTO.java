package edu.tp.paw.webapp.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.Hibernate;

import edu.tp.paw.model.Category;

@XmlRootElement
public class CategoryDTO {

	private Long id;
	private String name;
	private Long parent;
	private Collection<CategoryDTO> children;
	
	public CategoryDTO() {
		
	}
	
	public CategoryDTO(final Category category) {
		if (category != null) {
			this.id = category.getId();
			this.name = category.getName();
			if (!category.getParent().equals(category.getId())) {
				this.parent = category.getParent().getId(); //new CategoryDTO(category.getParent());
			}
			if (Hibernate.isInitialized(category.getChildren())) {
				this.children = category.getChildren().stream().map(v -> new CategoryDTO(v)).collect(Collectors.toList());
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Collection<CategoryDTO> getChildren() {
		return children;
	}

	public void setChildren(Collection<CategoryDTO> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", name=" + name + ", parent=" + parent + "]";
	}
	
	
	
	
}
