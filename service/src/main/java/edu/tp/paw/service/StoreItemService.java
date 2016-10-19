package edu.tp.paw.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.ICommentService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;


@Service
public class StoreItemService implements IStoreItemService {

	@Autowired private IStoreItemDao storeItemDao;
	@Autowired private ICategoryDao categoryDao;
	@Autowired private ICommentService commentService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IUserService userService;
	

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreItemService#fetchMostSold(int)
	 */
	@Override
	public List<StoreItem> getMostSold(final int n) {
		
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}
		
		return storeItemDao.findMostSold(n);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IStoreItemService#fetchById(long)
	 */
	@Override
	public StoreItem findById(final long id) {
		return storeItemDao.findById(id);
	}

	@Override
	public StoreItem create(final StoreItemBuilder builder) {
		
		if (builder == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		
		if (builder.getDescription() == null) {
			throw new IllegalArgumentException("item description cant be null");
		}
		
		if (builder.getName() == null) {
			throw new IllegalArgumentException("item name cant be null");
		}
		
		if (builder.getOwner() == null) {
			throw new IllegalArgumentException("item owner cant be null");
		}
		
		if (builder.getCategory() == null) {
			throw new IllegalArgumentException("item category cant be null");
		}
		
		if (!userService.userExists(builder.getOwner())) {
			throw new IllegalArgumentException("item owner must exist");
		}
		
		if (!categoryService.exists(builder.getCategory())) {
			throw new IllegalArgumentException("item category must exist");
		}
		
		
		return storeItemDao.create(builder);
	}

	@Override
	public PagedResult<StoreItem> findByFiltering(final Filter filter) {
		
		if (filter == null) {
			throw new IllegalArgumentException("filter cant be null");
		}
		
		if (filter.getCategoryFilter() == null) {
			throw new IllegalArgumentException("category filter cant be null");
		}
		
		if (filter.getOrderFilter() == null) {
			throw new IllegalArgumentException("order filter cant be null");
		}
		
		if (filter.getPageFilter() == null) {
			throw new IllegalArgumentException("page filter cant be null");
		}
		
		if (filter.getPriceFilter() == null) {
			throw new IllegalArgumentException("price filter cant be null");
		}
		
		if (filter.getTermFilter() == null) {
			throw new IllegalArgumentException("term filter cant be null");
		}
		
		final PagedResult<StoreItem> pagedResult = storeItemDao.findByTerm(filter);
		
		pagedResult.setNumberOfTotalResults(storeItemDao.getNumberOfItems());
		
		return pagedResult;
	}

	@Override
	public PagedResult<StoreItem> findByFiltering(final FilterBuilder filterBuilder) {
		
		if (filterBuilder == null) {
			throw new IllegalArgumentException("filter cant be null");
		}
		
		if (filterBuilder.category() == null) {
			throw new IllegalArgumentException("category filter builder cant be null");
		}
		
		if (filterBuilder.category().getCategories() == null) {
			throw new IllegalArgumentException("category filter builder categories cant be null");
		}
		
		Set<Category> categories = new HashSet<>(filterBuilder.category().getCategories());
		
		for (Category category : categories) {
			filterBuilder.category().in(categoryDao.getDescendants(category));
		}
		
		return findByFiltering(filterBuilder.build());
	}

	@Override
	public List<StoreItem> getUserItems(final User user) {
		
		if (user == null) {
			throw new IllegalStateException("user cant be null");
		}
		
		if (!userService.userExists(user)) {
			throw new IllegalStateException("user must exist");
		}
		
		return storeItemDao.getUserItems(user);
	}

	@Override
	public boolean updateItem(final StoreItem item) {
		
		if (item == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		
		if (!itemExists(item)) {
			throw new IllegalArgumentException("item must exist");
		}
		
		return storeItemDao.updateItem(item);
	}

	@Override
	public List<StoreItem> getFavourites(final User user) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (!userService.userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return storeItemDao.getFavourites(user);
	}

	@Override
	public Comment addCommentBy(final User user, final StoreItem item, final String comment) {
		return commentService.createComment(user, item, comment);
	}

	@Override
	public List<Comment> getComments(final StoreItem item) {
		if (item == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		
		if (!itemExists(item)) {
			throw new IllegalArgumentException("item must exist");
		}
		return commentService.commentsForItem(item);
	}

	@Override
	public int getNumberOfItems() {
		return storeItemDao.getNumberOfItems();
	}

	@Override
	public boolean itemExists(final long id) {
		return storeItemDao.itemExists(id);
	}

	@Override
	public boolean itemExists(final StoreItem item) {
		if (item == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		return itemExists(item.getId());
	}

	@Override
	public boolean increaseSellCount(final StoreItem item) {
		if (item == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		if (!itemExists(item)) {
			throw new IllegalArgumentException("item must exist");
		}
		return storeItemDao.increaseSellCount(item);
	}
	
	

}
