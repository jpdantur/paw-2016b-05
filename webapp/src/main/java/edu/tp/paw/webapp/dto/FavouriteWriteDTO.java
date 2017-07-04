package edu.tp.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FavouriteWriteDTO {

	private long item;
	
	public FavouriteWriteDTO() {
		
	}

	public long getItem() {
		return item;
	}

	public void setItem(long item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "FavouriteWriteDTO [item=" + item + "]";
	}
	
}
