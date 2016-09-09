package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.Date;

public class StoreItem {
	
	private long id;
	
	private String description;
	private String name;
	
	private long sold;
	
	private Timestamp created;
	private Timestamp lastUpdated;
	
	public StoreItem(long id, String description, String name, Timestamp created,
			Timestamp lastUpdated) {
		this.id = id;
		this.description = description;
		this.name = name;
		this.created = created;
		this.lastUpdated = lastUpdated;
	}
	
	public StoreItem(long id, String description, String name) {
		this.id = id;
		this.description = description;
		this.name = name;
		this.created = new Timestamp((new Date()).getTime());
		this.lastUpdated = new Timestamp((new Date()).getTime());
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

	public long getSold() {
		return sold;
	}

	public void setSold(long sold) {
		this.sold = sold;
	}

	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	@Override
	public String toString() {
		return "StoreItem [id=" + id + ", description=" + description + ", name="
				+ name + "]";
	}

}
