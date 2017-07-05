package edu.tp.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserBriefDTO {
	private float buyerRating;
	private float sellerRating;
	
	private int approvedTransactions;
	private int declinedTransactions;
	
	private int approvedPurchases;
	private int declinedPurchases;
	
	public UserBriefDTO() {
		
	}
	
	public UserBriefDTO(float buyerRating, float sellerRating, int approvedTransactions, int declinedTransactions,
			int approvedPurchases, int declinedPurchases) {
		super();
		this.buyerRating = buyerRating;
		this.sellerRating = sellerRating;
		this.approvedTransactions = approvedTransactions;
		this.declinedTransactions = declinedTransactions;
		this.approvedPurchases = approvedPurchases;
		this.declinedPurchases = declinedPurchases;
	}



	public float getBuyerRating() {
		return buyerRating;
	}
	public void setBuyerRating(float buyerRating) {
		this.buyerRating = buyerRating;
	}
	public float getSellerRating() {
		return sellerRating;
	}
	public void setSellerRating(float sellerRating) {
		this.sellerRating = sellerRating;
	}
	public int getApprovedTransactions() {
		return approvedTransactions;
	}
	public void setApprovedTransactions(int approvedTransactions) {
		this.approvedTransactions = approvedTransactions;
	}
	public int getDeclinedTransactions() {
		return declinedTransactions;
	}
	public void setDeclinedTransactions(int declinedTransactions) {
		this.declinedTransactions = declinedTransactions;
	}
	public int getApprovedPurchases() {
		return approvedPurchases;
	}
	public void setApprovedPurchases(int approvedPurchases) {
		this.approvedPurchases = approvedPurchases;
	}
	public int getDeclinedPurchases() {
		return declinedPurchases;
	}
	public void setDeclinedPurchases(int declinedPurchases) {
		this.declinedPurchases = declinedPurchases;
	}

	@Override
	public String toString() {
		return "UserBriefDTO [buyerRating=" + buyerRating + ", sellerRating=" + sellerRating + ", approvedTransactions="
				+ approvedTransactions + ", declinedTransactions=" + declinedTransactions + ", approvedPurchases="
				+ approvedPurchases + ", declinedPurchases=" + declinedPurchases + "]";
	}
	
	
	
	
}
