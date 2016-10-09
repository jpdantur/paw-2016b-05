package edu.tp.paw.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StoreItemBuilder implements IBuilder<StoreItem> {
	
	// Required parameters
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final Category category;
	private final boolean used;
	
	// Optional parameters
	private Timestamp created = new Timestamp( (new Date()).getTime() );
	private Timestamp lastUpdated = new Timestamp( (new Date()).getTime() );
	private int sold = 0;
	private long id;
	private User owner;
	private List<StoreImage> images = new ArrayList<>();
	
	public StoreItemBuilder(String name, String description, BigDecimal price, Category category, boolean used) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.used = used;
	}
	
	
	public StoreItemBuilder owner(User owner) {
		this.owner = owner;
		return this;
	}
	
	public StoreItemBuilder images(List<StoreImage> images) {
		this.images = images;
		return this;
	}
	
	public StoreItemBuilder images(StoreImage images) {
		this.images.add(images);
		return this;
	}
	
	/**
	 * Sets #{this} created time
	 * @param created Timestamp when #{this} was created
	 * @return #{this}
	 */
	public StoreItemBuilder created(Timestamp created) {
		this.created = created;
		return this;
	}
	
	/**
	 * Sets #{this} last updated time
	 * @param lastUpdated Timestamp when #{this} was last updated
	 * @return #{this}
	 */
	public StoreItemBuilder lastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
	
	/**
	 * Set a value for StoreItemBuilder.sold
	 * @param sold the value to set
	 * @return #{this}
	 */
	public StoreItemBuilder sold(int sold) {
		this.sold = sold;
		return this;
	}
	
	public StoreItemBuilder id(long id) {
		this.id = id;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.model.IBuilder#build()
	 */
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

	public BigDecimal getPrice() {
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
	
	public User getOwner() {
		return owner;
	}

	public boolean isUsed() {
		return used;
	}
	
	public List<StoreImage> getImages() {
		return images;
	}
	
}
