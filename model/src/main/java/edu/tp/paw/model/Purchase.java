package edu.tp.paw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "sales" )
public class Purchase {

	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "sales_sale_id_seq" )
	@SequenceGenerator( sequenceName = "sales_sale_id_seq", name = "sales_sale_id_seq", allocationSize = 1 )
	@Column( name =  "sale_id")
	@Id
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name =  "user_id", foreignKey = @ForeignKey( name = "user_fk" ))
	private User buyer;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name =  "item_id", foreignKey = @ForeignKey( name = "item_fk" ))
	private StoreItem item;
	
	@Enumerated(EnumType.STRING)
	private PurchaseStatus status;

	@Column( name = "created", insertable = false, updatable = false, columnDefinition = "timestamp default now()" )
	private Date created;
	
	@JoinColumn(name = "buyer_review", foreignKey = @ForeignKey( name = "buyer_review_fk" ), nullable = true, updatable = false)
	@OneToOne( optional = true, fetch = FetchType.EAGER)
	private PurchaseReview buyerReview;
	
	@JoinColumn(name = "seller_review", foreignKey = @ForeignKey( name = "seller_review_fk" ), nullable = true, updatable = false)
	@OneToOne( optional = true, fetch = FetchType.EAGER )
	private PurchaseReview sellerReview;
	
	/* protected */ Purchase() {
		// hibernate, duh!
	}
	
	/* protected */ Purchase(final PurchaseBuilder builder) {
		this.id = builder.getId();
		this.buyer = builder.getBuyer();
		this.item = builder.getItem();
		this.status = builder.getStatus();
		this.created = builder.getCreated();
		this.buyerReview = builder.getBuyerReview();
		this.sellerReview = builder.getSellerReview();
	}
	
	public Long getId() {
		return id;
	}

	public User getBuyer() {
		return buyer;
	}

	public StoreItem getItem() {
		return item;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public Date getCreated() {
		return created;
	}
	
	public PurchaseReview getBuyerReview() {
		return buyerReview;
	}
	
	public PurchaseReview getSellerReview() {
		return sellerReview;
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
		if (!(obj instanceof Purchase)) {
			return false;
		}
		Purchase other = (Purchase) obj;
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
		return "Purchase [id=" + id + ", buyer=" + buyer + ", item=" + item
				+ ", status=" + status + ", created=" + created + ", buyerReview="
				+ buyerReview + ", sellerReview=" + sellerReview + "]";
	}

	
	
}
