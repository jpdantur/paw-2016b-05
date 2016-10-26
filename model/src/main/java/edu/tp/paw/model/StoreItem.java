package edu.tp.paw.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "store_items")
public class StoreItem {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "store_items_item_id_seq" )
	@SequenceGenerator( sequenceName = "store_items_item_id_seq", name = "store_items_item_id_seq", allocationSize = 1 )
	@Column( name =  "item_id")
	private long id;
	
	@Column( length = 100 )
	private String name;
	
	private String description;
	
	@Column( precision = 20, scale = 2, name = "price" )
	private BigDecimal price;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "owner", foreignKey = @ForeignKey( name = "owner_fk") )
	private User owner;
	
	@Column( name = "used" )
	private boolean used;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "category", foreignKey = @ForeignKey( name = "category_fk"))
	private Category category;
	
	@Column(name = "sold")
	private long sold;
	
	@Column(insertable = false, updatable = false)
	private Timestamp created;
	@Column(name = "last_updated", insertable = false, updatable = true)
	private Timestamp lastUpdated;
	
	@OneToMany( fetch = FetchType.EAGER,  mappedBy = "id")
	private List<StoreImage> images;
	
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "id" )
	private List<Comment> comments;
	
	/* package */ StoreItem() {
		// hibernate, duh!
	}
	
	/* package */ StoreItem(final StoreItemBuilder builder) {
		this.id = builder.getId();
		this.name = builder.getName();
		this.description = builder.getDescription();
		this.price = builder.getPrice();
		this.sold = builder.getSold();
		this.created = builder.getCreated();
		this.lastUpdated = builder.getLastUpdated();
		this.category = builder.getCategory();
		this.owner = builder.getOwner();
		this.used = builder.isUsed();
		this.images = builder.getImages();
	}
	
	

	public long getId() {
		return id;
	}



	public String getName() {
		return name;
	}



	public String getDescription() {
		return description;
	}



	public BigDecimal getPrice() {
		return price;
	}



	public long getSold() {
		return sold;
	}



	public Timestamp getCreated() {
		return created;
	}



	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	
	public Category getCategory() {
		return category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StoreItem [id=" + id + ", name=" + name + ", sold="
				+ sold + ", created=" + created + ", lastUpdated="
				+ lastUpdated + ", price=" + price + "]";
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreItem other = (StoreItem) obj;
		if (id != other.id)
			return false;
		return true;
	}



	public User getOwner() {
		return owner;
	}

	public boolean isUsed() {
		return used;
	}

	public List<StoreImage> getImages() {
		return images;
	}
	
}
