package edu.tp.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class StoreImage {

	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "images_image_id_seq" )
	@SequenceGenerator( sequenceName = "images_image_id_seq", name = "images_image_id_seq", allocationSize = 1 )
	@Column( name =  "image_id")
	@Id
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER)
	@JoinColumn( name = "item_id", foreignKey = @ForeignKey( name = "item_fk" ) )
	private StoreItem item;
	
	@Column( name = "content" )
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

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StoreImage)) {
			return false;
		}
		StoreImage other = (StoreImage) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "{\"id\":"+id+",\"type\":\""+mimeType+"\",\"size\":"+content.length+"}";
	}
	
	
}
