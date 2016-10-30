package edu.tp.paw.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class StoreItemBuilder implements IBuilder<StoreItem> {
	
	// Required parameters
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final boolean used;
	
	// Optional parameters
	private Timestamp created = new Timestamp( (new Date()).getTime() );
	private Timestamp lastUpdated = new Timestamp( (new Date()).getTime() );
	private int sold = 0;
	private Long id;
	private User owner;
	private Set<StoreImage> images = new HashSet<>();
	private Set<Comment> comments = new HashSet<>();
	private Category category;
	
	public StoreItemBuilder(String name, String description, BigDecimal price, boolean used) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.used = used;
	}
	
	public StoreItemBuilder category(final Category category) {
		this.category = category;
		return this;
	}
	
	public StoreItemBuilder owner(final User owner) {
		this.owner = owner;
		return this;
	}
	
	public StoreItemBuilder images(final Collection<StoreImage> images) {
		this.images.addAll(images);
		return this;
	}
	
	public StoreItemBuilder images(final StoreImage images) {
		this.images.add(images);
		return this;
	}
	
	public StoreItemBuilder comments(final Collection<Comment> comments) {
		this.comments.addAll(comments);
		return this;
	}
	
	public StoreItemBuilder comment(final Comment comment) {
		this.comments.add(comment);
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

	public Long getId() {
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
	
	public Set<StoreImage> getImages() {
		return images;
	}
	
	public Set<Comment> getComments() {
		return comments;
	}
	
}
