package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.sun.istack.internal.NotNull;

public class Category {

	private final long id;
	private final String name;
	private long parent;
	
	private final String path;
	private final List<Category> children;
	
	private final Timestamp created;
	private final Timestamp lastUpdated;
	
	/* package */ Category(CategoryBuilder builder) {
		this.id = builder.getId();
		this.name = builder.getName();
		this.parent = builder.getParent();
		this.path = builder.getPath();
		this.children = builder.getChildren();
		this.created = builder.getCreated();
		this.lastUpdated = builder.getLastUpdated();
	}
	
	public void setParent(long parent) {
		this.parent = parent;
	}
	
	// getters
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public long getParent() {
		return parent;
	}
	
	public List<Category> getChildren() {
		return children;
	}

	public Timestamp getCreated() {
		return created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public String getPath() {
		return path;
	}
	public void addChild(@NotNull Category category) {
		children.add(category);
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", parent=" + parent
				+ ", path=" + path + ", children=" + children + ", created=" + created
				+ ", lastUpdated=" + lastUpdated + "]";
	}
	
	
	
}
