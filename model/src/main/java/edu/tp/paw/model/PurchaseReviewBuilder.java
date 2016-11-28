package edu.tp.paw.model;

import java.util.Date;

public class PurchaseReviewBuilder implements IBuilder<PurchaseReview> {

	private final String comment;
	
	private final Date created = new Date();
	
	private Long id;
	private float rating = 2.5f;
	
	public PurchaseReviewBuilder(final String comment) {
		this.comment = comment;
	}
	
	public PurchaseReviewBuilder rating(final float rating) {
		this.rating = rating;
		return this;
	}
	
	public PurchaseReviewBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	
	public String getComment() {
		return comment;
	}

	public Long getId() {
		return id;
	}

	public float getRating() {
		return rating;
	}
	
	public Date getCreated() {
		return created;
	}

	@Override
	public PurchaseReview build() {
		return new PurchaseReview(this);
	}

}
