package edu.tp.paw.model;

import java.util.Date;
import java.util.Objects;

public class PurchaseReviewBuilder implements IBuilder<PurchaseReview> {

	private final String comment;
	
	private final Date created = new Date();
	
	private Long id;
	private float rating = 2.5f;
	
	public PurchaseReviewBuilder(final String comment) {
		this.comment = Objects.requireNonNull(comment);
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
		
		Objects.requireNonNull(comment);
		
		if (comment.length() == 0) {
			throw new IllegalStateException("comment cant be empty");
		}
		
		if (rating < 0 || rating > 5) {
			throw new IllegalStateException("rating has to be between 0 and 5");
		}
		
		return new PurchaseReview(this);
	}

}
