package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CategoryBuilder implements IBuilder<Category> {

	// Required parameters
	private final long id;
	private final String name;
	private final long parent;
	
	
	// Optional paramenters
	private Timestamp created = new Timestamp( (new Date()).getTime() );
	private Timestamp lastUpdated = new Timestamp( (new Date()).getTime() );
	private List<Category> children = new LinkedList<>();
	private String path = "";
	
	
	/**
	 * Creates a new Category builder
	 * @param id The new Category id
	 * @param name the new Category name
	 * @param parent The new Category parent id
	 */
	public CategoryBuilder(long id, String name, long parent) {
		
		this.id = id;
		this.name = name;
		this.parent = parent;
		
	}
	
	/**
	 * Sets #{this} created time
	 * @param created Timestamp when #{this} was created
	 * @return #{this}
	 */
	public CategoryBuilder created(Timestamp created) {
		this.created = created;
		return this;
	}
	
	/**
	 * Sets #{this} last updated time
	 * @param lastUpdated Timestamp when #{this} was last updated
	 * @return #{this}
	 */
	public CategoryBuilder lastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
	
	/**
	 * Sets #{this} path
	 * @param path #{this} new path
	 * @return #{this}
	 */
	public CategoryBuilder path(String path) {
		this.path = path;
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

	public long getParent() {
		return parent;
	}

	public Timestamp getCreated() {
		return created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public List<Category> getChildren() {
		return children;
	}

	public String getPath() {
		return path;
	}
	
	
}