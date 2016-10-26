package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CategoryBuilder implements IBuilder<Category> {

	// Required parameters
	private final String name;
	private final Category parent;
	
	
	// Optional paramenters
	private Timestamp created = new Timestamp( (new Date()).getTime() );
	private Timestamp lastUpdated = new Timestamp( (new Date()).getTime() );
	private Set<Category> children = new HashSet<>();
	private String path = "";
	private long id = -1;
	
	
	/**
	 * Creates a new Category builder
	 * @param id The new Category id
	 * @param name the new Category name
	 * @param parent The new Category parent id
	 */
	public CategoryBuilder(final String name, final Category parent) {
		
		this.name = name;
		this.parent = parent;
		
	}
	
	/**
	 * Sets #{this} created time
	 * @param created Timestamp when #{this} was created
	 * @return #{this}
	 */
	public CategoryBuilder created(final Timestamp created) {
		this.created = created;
		return this;
	}
	
	/**
	 * Sets #{this} last updated time
	 * @param lastUpdated Timestamp when #{this} was last updated
	 * @return #{this}
	 */
	public CategoryBuilder lastUpdated(final Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
	
	/**
	 * Sets #{this} path
	 * @param path #{this} new path
	 * @return #{this}
	 */
	public CategoryBuilder path(final String path) {
		this.path = path;
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
		return new Category(this);
	}


	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Category getParent() {
		return parent;
	}

	public Timestamp getCreated() {
		return created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public String getPath() {
		return path;
	}
	
	
}
