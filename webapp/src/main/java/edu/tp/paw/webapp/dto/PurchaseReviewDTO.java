package edu.tp.paw.webapp.dto;

import java.util.Date;

import edu.tp.paw.model.PurchaseReview;

public class PurchaseReviewDTO {
	private Long id;
	private String comment;
	private float rating;
	private Date created;
	
	public PurchaseReviewDTO() {
		
	}
	
	public PurchaseReviewDTO(final PurchaseReview review) {
		this.id = review.getId();
		this.comment = review.getComment();
		this.rating = review.getRating();
		this.created = review.getCreated();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "PurchaseReviewDTO [id=" + id + ", comment=" + comment + ", rating=" + rating + ", created=" + created + "]";
	}
	
	
}
