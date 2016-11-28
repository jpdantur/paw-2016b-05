package edu.tp.paw.model;

import java.util.Date;

public class PurchaseBuilder  implements IBuilder<Purchase> {

	private final User buyer;
	private final StoreItem item;
	
	private PurchaseStatus status = PurchaseStatus.PENDING;
	private Long id;
	
	private Date created = new Date();
	
	private PurchaseReview buyerReview;
	private PurchaseReview sellerReview;
	
	public PurchaseBuilder(final User user, final StoreItem item) {
		this.buyer = user;
		this.item = item;
	}
	
	public PurchaseBuilder approved(final PurchaseStatus status) {
		this.status = status;
		return this;
	}
	
	public PurchaseBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	public PurchaseBuilder created(final Date created) {
		this.created = created;
		return this;
	}
	
	public PurchaseBuilder buyerReview(final PurchaseReview buyerReview) {
		this.buyerReview = buyerReview;
		return this;
	}
	
	public PurchaseBuilder sellerReview(final PurchaseReview sellerReview) {
		this.sellerReview = sellerReview;
		return this;
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

	public Long getId() {
		return id;
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
	public Purchase build() {
		return new Purchase(this);
	}

}
