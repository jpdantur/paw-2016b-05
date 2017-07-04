package edu.tp.paw.webapp.dto;

import java.util.Date;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

public class PurchaseDTO {
	private Long id;
	private UserDTO buyer;
	private StoreItemDTO item;
	private PurchaseStatus status;
	private PurchaseReviewDTO buyerReview;
	private PurchaseReviewDTO sellerReview;
	private Date created;
	
	public PurchaseDTO() {
		
	}
	
	public PurchaseDTO(final Purchase purchase) {
		this.id = purchase.getId();
		this.buyer = UserDTO.fromUser(purchase.getBuyer());
		this.item = new StoreItemDTO(purchase.getItem());
		this.status = purchase.getStatus();
		if (purchase.getBuyerReview() != null) {
			this.buyerReview = new PurchaseReviewDTO(purchase.getBuyerReview());
		}
		if (purchase.getSellerReview() != null) {
			this.sellerReview = new PurchaseReviewDTO(purchase.getSellerReview());
		}
		this.created = purchase.getCreated();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getBuyer() {
		return buyer;
	}

	public void setBuyer(UserDTO buyer) {
		this.buyer = buyer;
	}

	public StoreItemDTO getItem() {
		return item;
	}

	public void setItem(StoreItemDTO item) {
		this.item = item;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public void setStatus(PurchaseStatus status) {
		this.status = status;
	}

	public PurchaseReviewDTO getBuyerReview() {
		return buyerReview;
	}

	public void setBuyerReview(PurchaseReviewDTO buyerReview) {
		this.buyerReview = buyerReview;
	}

	public PurchaseReviewDTO getSellerReview() {
		return sellerReview;
	}

	public void setSellerReview(PurchaseReviewDTO sellerReview) {
		this.sellerReview = sellerReview;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "PurchaseDTO [id=" + id + ", buyer=" + buyer + ", item=" + item + ", status=" + status + ", buyerReview="
				+ buyerReview + ", sellerReview=" + sellerReview + ", created=" + created + "]";
	}
	
	
}
