package edu.tp.paw.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class StoreItemBuilder implements IBuilder<StoreItem> {
	
	// Required parameters
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final boolean used;
	
	// Optional parameters
	private Date created = new Date();
	private Date lastUpdated = new Date();
	private long sold = 0;
	private Long id;
	private User owner;
	private Set<StoreImage> images = new HashSet<>();
	private Set<Comment> comments = new HashSet<>();
	private Category category;
	
	private StoreItemStatus status = StoreItemStatus.ACTIVE;
	
	public StoreItemBuilder(String name, String description, BigDecimal price, boolean used) {
		this.name = Objects.requireNonNull(name);
		this.description = Objects.requireNonNull(description);
		this.price = Objects.requireNonNull(price);
		this.used = used;
	}
	
	public StoreItemBuilder category(final Category category) {
		this.category = Objects.requireNonNull(category);
		return this;
	}
	
	public StoreItemBuilder owner(final User owner) {
		this.owner = Objects.requireNonNull(owner);
		return this;
	}
	
	public StoreItemBuilder images(final Collection<StoreImage> images) {
		for (final StoreImage i : images) {
			image(i);
		}
		return this;
	}
	
	public StoreItemBuilder image(final StoreImage images) {
		this.images.add(Objects.requireNonNull(images));
		return this;
	}
	
	public StoreItemBuilder comments(final Collection<Comment> comments) {
		for (final Comment c : comments) {
			comment(c);
		}
		return this;
	}
	
	public StoreItemBuilder comment(final Comment comment) {
		this.comments.add(Objects.requireNonNull(comment));
		return this;
	}
	
	public StoreItemBuilder status(final StoreItemStatus status) {
		this.status = status;
		return this;
	}
	
	/**
	 * Sets #{this} created time
	 * @param created Timestamp when #{this} was created
	 * @return #{this}
	 */
	public StoreItemBuilder created(final Date created) {
		this.created = Objects.requireNonNull(created);
		return this;
	}
	
	/**
	 * Sets #{this} last updated time
	 * @param lastUpdated Timestamp when #{this} was last updated
	 * @return #{this}
	 */
	public StoreItemBuilder lastUpdated(final Date lastUpdated) {
		this.lastUpdated = Objects.requireNonNull(lastUpdated);
		return this;
	}
	
	/**
	 * Set a value for StoreItemBuilder.sold
	 * @param sold the value to set
	 * @return #{this}
	 */
	public StoreItemBuilder sold(final long sold) {
		this.sold = sold;
		return this;
	}
	
	public StoreItemBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.model.IBuilder#build()
	 */
	public StoreItem build() {
	
		Objects.requireNonNull(name);
		Objects.requireNonNull(description);
		Objects.requireNonNull(price);
		Objects.requireNonNull(category);
		Objects.requireNonNull(owner);
		
		if (name.length() < 4 || name.length() > 100) {
			throw new IllegalStateException("name should be between 4 and 100 chars");
		}
		
		if (price.compareTo(new BigDecimal(1)) < 0 || price.compareTo(new BigDecimal(1000000000)) > 0) {
			throw new IllegalStateException("price should be between 1 and 1000000000");
		}
		
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

	public Date getCreated() {
		return created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public long getSold() {
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

	public StoreItemStatus getStatus() {
		return status;
	}
	
}
