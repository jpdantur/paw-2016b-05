package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CategoryBuilder implements IBuilder<Category> {

	// Required parameters
	private final String name;
	private final Category parent;
	
	
	// Optional paramenters
	private Date created = new Date();
	private Date lastUpdated = new Date();
	private Set<Category> children = new HashSet<>();
	private Long id;
	
	
	/**
	 * Creates a new Category builder
	 * @param id The new Category id
	 * @param name the new Category name
	 * @param parent The new Category parent id
	 */
	public CategoryBuilder(final String name, final Category parent) {
		
		this.name = Objects.requireNonNull(name);
		this.parent = Objects.requireNonNull(parent);
		
	}
	
	/**
	 * Sets #{this} created time
	 * @param created Timestamp when #{this} was created
	 * @return #{this}
	 */
	public CategoryBuilder created(final Timestamp created) {
		this.created = Objects.requireNonNull(created);
		return this;
	}
	
	/**
	 * Sets #{this} last updated time
	 * @param lastUpdated Timestamp when #{this} was last updated
	 * @return #{this}
	 */
	public CategoryBuilder lastUpdated(final Timestamp lastUpdated) {
		this.lastUpdated = Objects.requireNonNull(lastUpdated);
		return this;
	}
	
	public CategoryBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.model.IBuilder#build()
	 */
	@Override
	public Category build() {
		
		Objects.requireNonNull(name);
		Objects.requireNonNull(parent);
		Objects.requireNonNull(created);
		Objects.requireNonNull(lastUpdated);
		
		
		return new Category(this);
	}


	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Category getParent() {
		return parent;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public Set<Category> getChildren() {
		return children;
	}
	
	
}
