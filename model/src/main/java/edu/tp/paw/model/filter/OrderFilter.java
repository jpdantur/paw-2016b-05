package edu.tp.paw.model.filter;

import java.util.function.Function;

public class OrderFilter {

	public enum SortField {
		PRICE, NAME, CREATED;
	};
	
	public enum SortOrder {
		ASC, DESC;
	};
	
	public static final Function<String, SortField> orderByMapping = orderBy -> {
		if (orderBy.equals("price")) {
			return SortField.PRICE;
		} else if (orderBy.equals("name")) {
			return SortField.NAME;
		}
		return SortField.PRICE;
	};
	
	public static final Function<String, SortOrder> sortOrderMapping = sortOrder -> {
		if (sortOrder.equals("asc")) {
			return SortOrder.ASC;
		} else if (sortOrder.equals("desc")) {
			return SortOrder.DESC;
		}
		return SortOrder.ASC;
	};
	
	private final SortField field;
	private final SortOrder order;
	
	/* package */ OrderFilter(OrderFilterBuilder builder) {
		this.field = builder.getField();
		this.order = builder.getOrder();
	}

	public SortField getField() {
		return field;
	}

	public SortOrder getOrder() {
		return order;
	}
	
	
}
