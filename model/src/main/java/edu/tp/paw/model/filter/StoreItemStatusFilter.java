package edu.tp.paw.model.filter;

public class StoreItemStatusFilter {
	
	public enum ItemStatusFilter {
		ACTIVE,
		PAUSED,
		ANY
	};
	
	private final ItemStatusFilter status;
	
	/* package */ StoreItemStatusFilter(final StoreItemStatusFilterBuilder builder) {
		this.status = builder.getStatus();
	}

	public ItemStatusFilter getStatus() {
		return status;
	}
	
	
}
