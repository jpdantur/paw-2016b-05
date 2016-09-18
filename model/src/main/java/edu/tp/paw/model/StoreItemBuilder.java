package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;


public class StoreItemBuilder implements IBuilder<StoreItem> {
	
	// Required parameters
	private final long id;
	private final String name;
	private final String description;
	private final float price;
	private final Category category;
	
	// Optional parameters
	private Timestamp created = new Timestamp( (new Date()).getTime() );
	private Timestamp lastUpdated = new Timestamp( (new Date()).getTime() );
	private int sold = 0;
	
	public StoreItemBuilder(long id, String name, String description, float price, Category category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}
	
	public StoreItemBuilder created(Timestamp created) {
		this.created = created;
		return this;
	}
	
	public StoreItemBuilder lastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
	
	public StoreItemBuilder sold(int sold) {
		this.sold = sold;
		return this;
	}
	
	public StoreItem build() {
	
		return new StoreItem(this);
		
	}
	
	// getters

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

	public Timestamp getCreated() {
		return created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public int getSold() {
		return sold;
	}
	
	public Category getCategory() {
		return category;
	}
	
	
	
}
