package edu.tp.paw.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class StoreItem {
	
	private final long id;
	
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final User owner;
	private final boolean used;
	
	private final Category category;
	
	private final long sold;
	
	private final Timestamp created;
	private final Timestamp lastUpdated;
	
	private final List<StoreImage> images;
	
	/* package */ StoreItem(final StoreItemBuilder builder) {
		this.id = builder.getId();
		this.name = builder.getName();
		this.description = builder.getDescription();
		this.price = builder.getPrice();
		this.sold = builder.getSold();
		this.created = builder.getCreated();
		this.lastUpdated = builder.getLastUpdated();
		this.category = builder.getCategory();
		this.owner = builder.getOwner();
		this.used = builder.isUsed();
		this.images = builder.getImages();
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



	public BigDecimal getPrice() {
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
	
	public Category getCategory() {
		return category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StoreItem [id=" + id + ", name=" + name + ", sold="
				+ sold + ", created=" + created + ", lastUpdated="
				+ lastUpdated + ", price=" + price + "]";
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreItem other = (StoreItem) obj;
		if (id != other.id)
			return false;
		return true;
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
