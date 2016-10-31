package edu.tp.paw.model;

public class PurchaseBuilder  implements IBuilder<Purchase> {

	private final User buyer;
	private final StoreItem item;
	
	private PurchaseStatus status = PurchaseStatus.PENDING;
	private Long id;
	
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

	@Override
	public Purchase build() {
		return new Purchase(this);
	}

}
