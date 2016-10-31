package edu.tp.paw.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

@Entity
@Table( name = "store_items")
public class StoreItem {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "store_items_item_id_seq" )
	@SequenceGenerator( sequenceName = "store_items_item_id_seq", name = "store_items_item_id_seq", allocationSize = 1 )
	@Column( name =  "item_id")
	private Long id;
	
	@Column( length = 100 )
	private String name;
	
	private String description;
	
	@Column( precision = 20, scale = 2, name = "price" )
	private BigDecimal price;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "owner", foreignKey = @ForeignKey( name = "owner_fk"), nullable = false )
	private User owner;
	
	@Column( name = "used" )
	private boolean used;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@Fetch(FetchMode.JOIN)
	@JoinColumn( name = "category", foreignKey = @ForeignKey( name = "category_fk"), nullable = false)
	private Category category;
	
	@Column(name = "sold")
	private long sold;
	
	@Column(insertable = false, updatable = false)
	private Timestamp created;
	@Column(name = "last_updated", insertable = false, updatable = true)
	private Timestamp lastUpdated;
	
	@OneToMany( fetch = FetchType.EAGER,  mappedBy = "item")
	@Fetch(FetchMode.JOIN)
	private Set<StoreImage> images;
	
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "item" )
	private Set<Comment> comments;
	
	@Formula("(select round(coalesce(avg(sr.rating),0)::numeric,1) from comments sr where sr.item_id=item_id)")
	private float rating;
	
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
		this.comments = builder.getComments();
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

	
	
	
	
	@Override
	public String toString() {
		return "StoreItem [id=" + id + ", name=" + name + ", description="
				+ description + ", price=" + price + ", owner=" + owner.getUsername() + ", used="
				+ used + ", category=" + category.getId() + ", sold=" + sold + ", created="
				+ created + ", lastUpdated=" + lastUpdated + ", images=" + images.size()
				+ ", rating="+ rating;
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
		if (!(obj instanceof StoreItem)) {
			return false;
		}
		StoreItem other = (StoreItem) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public User getOwner() {
		return owner;
	}

	public boolean isUsed() {
		return used;
	}

	public Set<StoreImage> getImages() {
		return images;
	}
	
	public Set<Comment> getComments() {
		return comments;
	}
	
	public float getRating() {
		return rating;
	}
	
}
