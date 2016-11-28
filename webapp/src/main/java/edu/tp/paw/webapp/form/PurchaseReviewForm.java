package edu.tp.paw.webapp.form;

public class PurchaseReviewForm {

	private String comment;
	private float rating;
	
	public String getComment() {
		return comment;
	}
	
	public float getRating() {
		return rating;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		return "PurchaseReviewForm [comment=" + comment + ", rating=" + rating + "]";
	}
	
}
