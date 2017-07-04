package edu.tp.paw.webapp.dto;

import java.util.Date;

import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

public class FavouriteDTO {

	private Long id;
	private StoreItemDTO item;
	private UserDTO user;
	private Date created;
	
	public FavouriteDTO() {
		
	}
	
	public FavouriteDTO(final Favourite fav) {
		this.id = fav.getId();
		this.item = new StoreItemDTO(fav.getItem());
		this.user = UserDTO.fromUser(fav.getUser());
		this.created = fav.getCreated();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StoreItemDTO getItem() {
		return item;
	}

	public void setItem(StoreItemDTO item) {
		this.item = item;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "FavouriteDTO [id=" + id + ", item=" + item + ", user=" + user + ", created=" + created + "]";
	}
	
	
	
	
}
