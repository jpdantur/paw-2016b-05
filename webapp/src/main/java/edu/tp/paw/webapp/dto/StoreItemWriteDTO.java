package edu.tp.paw.webapp.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;

@XmlRootElement
public class StoreItemWriteDTO {
	
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private long owner;
	private boolean used;
	private long category;
	private long sold;
	private float rating;
	private StoreItemStatus status;
//	private Collection<StoreImageDTO> images;
	
	public StoreItemWriteDTO() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public long getCategory() {
		return category;
	}
	public void setCategory(long category) {
		this.category = category;
	}
	public long getSold() {
		return sold;
	}
	public void setSold(long sold) {
		this.sold = sold;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public StoreItemStatus getStatus() {
		return status;
	}
	public void setStatus(StoreItemStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "StoreItemWriteDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", owner=" + owner + ", used=" + used + ", category=" + category + ", sold=" + sold + ", rating=" + rating
				+ ", status=" + status + "]";
	}
	
	
	
}
