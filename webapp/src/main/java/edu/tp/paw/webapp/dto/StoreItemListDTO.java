package edu.tp.paw.webapp.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.StoreItem;

@XmlRootElement
public class StoreItemListDTO {
	
	private List<StoreItemDTO> items;
	
	public StoreItemListDTO() {
		
	}
	
	public StoreItemListDTO(List<StoreItem> items) {
		this.items = items.stream().map(v -> new StoreItemDTO(v)).collect(Collectors.toList());
	}

	public List<StoreItemDTO> getItems() {
		return items;
	}

	public void setItems(List<StoreItemDTO> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "StoreItemListDTO [items=" + items + "]";
	}
}
