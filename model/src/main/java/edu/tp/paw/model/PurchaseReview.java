package edu.tp.paw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_reviews")
public class PurchaseReview {
	
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "purchase_reviews_purchase_review_id_seq" )
	@SequenceGenerator( sequenceName = "purchase_reviews_purchase_review_id_seq", name = "purchase_reviews_purchase_review_id_seq", allocationSize = 1 )
	@Column( name =  "purchase_review_id")
	@Id
	private Long id;
	
	@Column( length = 200 )
	private String comment;
	
	@Column(precision=20, scale=1, nullable = false)
	private float rating;
	
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp default now()")
	private Date created;
	
//	@OneToOne( optional = true, fetch = FetchType.LAZY )
//	@JoinColumn(name = "purchase", foreignKey = @ForeignKey( name = "purchase_fk" ))
//	private Purchase purchase;
	
	/* protected */ PurchaseReview() {
		/* Just for hibernate */
	}
	
	public PurchaseReview(final PurchaseReviewBuilder builder) {
		this.id = builder.getId();
		this.comment = builder.getComment();
		this.rating = builder.getRating();
		this.created = builder.getCreated();
	}

	public Long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public float getRating() {
		return rating;
	}
	
	public Date getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "{\"id\":" + id + ", \"comment\":\"" + comment + "\", \"rating\":"
				+ rating + ", \"created\":\"" + created + "\"}";
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
		if (!(obj instanceof PurchaseReview)) {
			return false;
		}
		PurchaseReview other = (PurchaseReview) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
	
//	public Purchase getPurchase() {
//		return purchase;
//	}

	
	
}
