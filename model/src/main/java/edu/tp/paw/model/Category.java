package edu.tp.paw.model;

public class Category {

	private long id;
	
	private String name;
	private Category parent;
	
	
	
	public Category(long id, String name, Category parent) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
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
	
	
	
}
