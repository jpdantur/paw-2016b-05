package edu.tp.paw.webapp.dto;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.StoreImage;

@XmlRootElement
public class StoreImageDTO {

	private Long id;
	private byte[] content;
	private String mimeType;
	
	public StoreImageDTO() {
		
	}
	
	public StoreImageDTO(final StoreImage image) {
		this.id = image.getId();
		this.content = image.getContent();
		this.mimeType = image.getMimeType();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public String toString() {
		return "StoreImageDTO [id=" + id + ", content=" + Arrays.toString(content) + ", mimeType=" + mimeType + "]";
	}
}
