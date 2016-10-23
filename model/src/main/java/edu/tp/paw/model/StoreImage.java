package edu.tp.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="store_images")
public class StoreImage {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "images_image_id_seq" )
	@SequenceGenerator( sequenceName = "images_image_id_seq", name = "images_image_id_seq", allocationSize = 1 )
	@Column( name =  "image_id")
	private long id;
	@ManyToOne( fetch = FetchType.EAGER )
	private StoreItem item;
	private byte[] content;
	@Column( name = "mime_type" )
	private String mimeType;
	
	/* package */ StoreImage() {
		// hibernate, duh!
	}
	
	/* package */ StoreImage(final StoreImageBuilder builder) {
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
