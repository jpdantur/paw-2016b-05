package edu.tp.paw.webapp.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import edu.tp.paw.model.PurchaseReview;

@XmlRootElement
public class PurchaseReviewWriteDTO {
	private String comment;
	private float rating;
	
	public PurchaseReviewWriteDTO() {
		
	}
	
	public PurchaseReviewWriteDTO(final PurchaseReview review) {
		this.comment = review.getComment();
		this.rating = review.getRating();
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

	@Override
	public String toString() {
		return "PurchaseReviewWriteDTO [comment=" + comment + ", rating=" + rating + "]";
	}
	
	
}
