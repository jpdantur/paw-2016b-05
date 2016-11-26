package edu.tp.paw.model;

import java.util.Date;

public class PurchaseBuilder  implements IBuilder<Purchase> {

	private final User buyer;
	private final StoreItem item;
	
	private PurchaseStatus status = PurchaseStatus.PENDING;
	private Long id;
	
	private Date created = new Date();
	
	private float rating;
	
	private String comment;
	
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
	
	public PurchaseBuilder rating(final float rating) {
		this.rating = rating;
		return this;
	}
	
	public PurchaseBuilder rating(final String comment) {
		this.comment = comment;
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

	public float getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public Purchase build() {
		return new Purchase(this);
	}

}
