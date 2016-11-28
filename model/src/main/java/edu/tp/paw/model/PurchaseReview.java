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
	
	@Column(nullable = false, insertable = false, updatable = false)
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

	@Override
	public String toString() {
		return "{\"id\":" + id + ", \"comment\":\"" + comment + "\", \"rating\":"
				+ rating + ", \"created\":\"" + created + "\"}";
	}
	
//	public Purchase getPurchase() {
//		return purchase;
//	}

	
	
}
