package edu.tp.paw.model;

public class StoreItem {
	
	private long id;
	
	private String description;
	private String name;
	public StoreItem(long id, String description, String name) {
		super();
		this.id = id;
		this.description = description;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StoreItem [id=" + id + ", description=" + description + ", name="
				+ name + "]";
	}

}
