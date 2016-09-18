package edu.tp.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SellForm {

	@NotNull
	@Size(min = 4, max = 100)
	private String name;
	private String description;
	@Min(100)
	private float price;
	
	private long categoryId;
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public SellForm() {
		
	}
	
	public SellForm(String name, String description, float price, long categoryId) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}
