package edu.tp.paw.webapp.form;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.tp.paw.model.StoreItemStatus;

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
	
	private String itemStatus = "ACTIVE";

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

	public String getItemStatus() {
		return itemStatus;
	}
	
	public StoreItemStatus getStatus() {
		return StoreItemStatus.valueOf(itemStatus);
	}

	public void setItemStatus(String status) {
		this.itemStatus = status;
	}

	@Override
	public String toString() {
		return "SellForm [name=" + name + ", description=" + description
				+ ", price=" + price + ", categoryId=" + categoryId + ", used=" + used
				+ "]";
	}
	
	
	
}
