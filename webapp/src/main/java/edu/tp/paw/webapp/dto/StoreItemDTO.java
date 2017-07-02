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
public class StoreItemDTO {
	
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private UserDTO owner;
	private boolean used;
	private CategoryDTO category;
	private long sold;
	private float rating;
	private StoreItemStatus status;
	private Collection<StoreImageDTO> images;
	
	public StoreItemDTO() {
		
	}
	
	public StoreItemDTO(final StoreItem item) {
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();
		this.owner = UserDTO.fromUser(item.getOwner());
		this.used = item.isUsed();
		this.category = new CategoryDTO(item.getCategory());
		this.sold = item.getSold();
		this.rating = item.getRating();
		this.status = item.getStatus();
		this.images = item.getImages().stream().map(v -> new StoreImageDTO(v)).collect(Collectors.toList());
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
	public UserDTO getOwner() {
		return owner;
	}
	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public CategoryDTO getCategory() {
		return category;
	}
	public void setCategory(CategoryDTO category) {
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
	
	public Collection<StoreImageDTO> getImages() {
		return images;
	}

	public void setImages(Collection<StoreImageDTO> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "StoreItemDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", owner=" + owner + ", used=" + used + ", category=" + category + ", sold=" + sold + ", rating=" + rating
				+ ", status=" + status + "]";
	}
	
	
	
}
