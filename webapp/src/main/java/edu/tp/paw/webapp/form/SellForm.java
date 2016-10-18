package edu.tp.paw.webapp.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class SellForm {

	@Size(min = 4, max = 100, message = "{Size.SellForm.name}")
	private String name;
	
	private String description;
	
	@NotNull(message = "{NotNull.SellForm.price}")
	@Digits(integer=20, fraction=2, message = "{Digits.SellForm.price}")
	@Min(value = 1, message = "{Digits.SellForm.price}")
	@Max(value = 1000000000, message = "{Digits.SellForm.price}")
	private BigDecimal price;
	
	@Min(0)
	private long categoryId;
	
	private boolean used;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isUsed() {
		return used;
	}
	
	public boolean getUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		return "SellForm [name=" + name + ", description=" + description
				+ ", price=" + price + ", categoryId=" + categoryId + ", used=" + used
				+ "]";
	}
	
	
	
}
