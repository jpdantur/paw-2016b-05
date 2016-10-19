package edu.tp.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;

@Service
public class StoreService implements IStoreService {

	@Autowired
	private IStoreItemService storeItemService;

	@Override
	public StoreItem sell(final StoreItemBuilder builder) {
		return storeItemService.create(builder);
	}

	@Override
	public PagedResult<StoreItem> search(final Filter filter) {
		return storeItemService.findByFiltering(filter);
	}

	@Override
	public PagedResult<StoreItem> search(final FilterBuilder filter) {
		return storeItemService.findByFiltering(filter);
	}

	@Override
	public boolean purchase(final StoreItem item) {
		return storeItemService.increaseSellCount(item);
	}

}
