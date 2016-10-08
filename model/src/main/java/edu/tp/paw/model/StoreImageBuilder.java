package edu.tp.paw.model;

public class StoreImageBuilder implements IBuilder<StoreImage> {

	private final byte[] content;
	private final String mimeType;
	private final String filename;
	
	private StoreItem item;
	private long id;
	
	public StoreImageBuilder(String filename, String mimeType, byte[] content) {
		this.filename = filename;
		this.mimeType = mimeType;
		this.content = content;
	}
	
	public StoreImageBuilder item(StoreItem item) {
		this.item = item;
		return this;
	}
	
	public StoreImageBuilder id(long id) {
		this.id = id;
		return this;
	}
	
	
	public byte[] getContent() {
		return content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getFilename() {
		return filename;
	}

	public StoreItem getItem() {
		return item;
	}

	public long getId() {
		return id;
	}

	@Override
	public StoreImage build() {
		return new StoreImage(this);
	}

}
