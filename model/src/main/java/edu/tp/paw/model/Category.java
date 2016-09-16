package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.sun.istack.internal.NotNull;

public class Category {

	private long id;
	
	private String name;
	private int parent;
	private String path;
	private List<Category> children;
	
	private final Timestamp created;
	private final Timestamp lastUpdated;
	
	public Category(long id, String name, Category parent) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.children = new LinkedList<>();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void addChild(@NotNull Category category) {
		children.add(category);
	}
	
	
}
