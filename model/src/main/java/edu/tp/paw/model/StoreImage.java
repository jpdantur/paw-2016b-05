package edu.tp.paw.model;

public class StoreImage {

	private final long id;
	private final StoreItem item;
	private final byte[] content;
	private final String mimeType;
	
	public StoreImage(StoreImageBuilder builder) {
		this.id = builder.getId();
		this.item = builder.getItem();
		this.content = builder.getContent();
		this.mimeType = builder.getMimeType();
	}
	
	
	public StoreItem getItem() {
		return item;
	}
	public byte[] getContent() {
		return content;
	}
	public String getMimeType() {
		return mimeType;
	}
	public long getId() {
		return id;
	}
	
	
	
	
}
