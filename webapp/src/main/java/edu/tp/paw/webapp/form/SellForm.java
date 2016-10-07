package edu.tp.paw.webapp.form;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class SellForm {

	@Size(min = 2, max = 100, message = "{Size.SellForm.name}")
	private String name;
	
	
	private String description;
	
	@NotNull
	@Digits(integer=20, fraction=2)
	private BigDecimal price;
	
	@Min(0)
	private long categoryId;
	
	@Email( message = "{Email.SellForm.email}" )
	@NotEmpty( message = "{NotEmpty.SellForm.email}" )
	private String email;
	
	private boolean used;
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "SellForm [name=" + name + ", description=" + description
				+ ", price=" + price + ", categoryId=" + categoryId + ", email="
				+ email + "]";
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
	
}
