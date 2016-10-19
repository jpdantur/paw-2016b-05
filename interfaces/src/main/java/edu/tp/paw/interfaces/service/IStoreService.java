package edu.tp.paw.interfaces.service;

import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;

public interface IStoreService {
	
	public StoreItem sell(final StoreItemBuilder builder);
	
	public PagedResult<StoreItem> search(final Filter filter);
	public PagedResult<StoreItem> search(final FilterBuilder filter);
	
	public boolean purchase(final StoreItem item);
}
