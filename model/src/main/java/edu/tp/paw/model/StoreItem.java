package edu.tp.paw.model;

import java.sql.Timestamp;

public class StoreItem {
	
	private final long id;
	
	private final String name;
	private final String description;
	private final float price;
	private final String email;
	
	private Category category;
	
	private final long sold;
	
	private final Timestamp created;
	private final Timestamp lastUpdated;
	
	/* package */ StoreItem(final StoreItemBuilder builder) {
		this.id = builder.getId();
		this.name = builder.getName();
		this.description = builder.getDescription();
		this.price = builder.getPrice();
		this.sold = builder.getSold();
		this.created = builder.getCreated();
		this.lastUpdated = builder.getLastUpdated();
		this.category = builder.getCategory();
		this.email = builder.getEmail();
	}
	
	

	public long getId() {
		return id;
	}



	public String getName() {
		return name;
	}



	public String getDescription() {
		return description;
	}



	public float getPrice() {
		return price;
	}



	public long getSold() {
		return sold;
	}



	public Timestamp getCreated() {
		return created;
	}



	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Category getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "StoreItem [id=" + id + ", description=" + description
				+ ", name=" + name + ", sold=" + sold + ", created=" + created
				+ ", lastUpdated=" + lastUpdated + ", price=" + price + "]";
	}



	public String getEmail() {
		return email;
	}

	

}
