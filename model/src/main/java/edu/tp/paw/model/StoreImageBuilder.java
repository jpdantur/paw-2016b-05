package edu.tp.paw.model;

import java.util.Objects;

public class StoreImageBuilder implements IBuilder<StoreImage> {

	private final byte[] content;
	private final String mimeType;
	
	private StoreItem item;
	private Long id;
	
	public StoreImageBuilder(final String mimeType, final byte[] content) {
		this.mimeType = Objects.requireNonNull(mimeType);
		this.content = content;
	}
	
	public StoreImageBuilder item(final StoreItem item) {
		this.item = Objects.requireNonNull(item);
		return this;
	}
	
	public StoreImageBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	
	public byte[] getContent() {
		return content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public StoreItem getItem() {
		return item;
	}

	public Long getId() {
		return id;
	}

	@Override
	public StoreImage build() {
		
		Objects.requireNonNull(mimeType);
		Objects.requireNonNull(item);
		
		return new StoreImage(this);
	}

}
