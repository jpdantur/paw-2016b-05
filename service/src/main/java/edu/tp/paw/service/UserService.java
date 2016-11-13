package edu.tp.paw.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.interfaces.service.ICommentService;
import edu.tp.paw.interfaces.service.IFavouriteService;
import edu.tp.paw.interfaces.service.IPurchaseService;
import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PagedResult;


@Service
@Transactional
public class UserService implements IUserService {
	
	@Autowired private IUserDao userDao;
	@Autowired private IStoreItemService itemService;
	@Autowired private ICommentService commentService;
	@Autowired private IRoleService roleService;
	@Autowired private IPurchaseService purchaseService;
	@Autowired private IFavouriteService favouriteService;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IUserService#findById(long)
	 */
	@Override
	public User findById(final long id) {
		return userDao.findById(id);
	}
	
	@Override
	public User findByUsername(final String username) {
	
		return userDao.findByUsername(username); 
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.service.IUserService#create(java.lang.String)
	 */
	@Override
	public User registerUser(final UserBuilder builder) {
		
		if (builder == null) {
			throw new IllegalArgumentException("builder cannot be null");
		}
		
		
		if (userDao.emailExists(builder.getEmail())) {
			throw new IllegalStateException("a user with username " + builder.getUsername() + " already exists");
		}
		
		if (userDao.usernameExists(builder.getUsername())) {
			throw new IllegalStateException("a user with username " + builder.getUsername() + " already exists");
		}
		
		builder.password(passwordEncoder.encode(builder.getPassword()));
		return createUser(builder, roleService.getDefaultRole());
	}

	@Override
	public boolean userExists(final long id) {
		return userDao.idExists(id);
	}
	
	@Override
	public boolean userExists(final User user) {
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		return userExists(user.getId());
	}

	@Override
	public boolean usernameExists(final String username) {
		if (username == null) {
			throw new IllegalArgumentException("username cant be null");
		}
		return userDao.usernameExists(username);
	}

	@Override
	public boolean emailExists(final String email) {
		if (email == null) {
			throw new IllegalArgumentException("email cant be null");
		}
		return userDao.emailExists(email);
	}

	@Override
	public boolean changePassword(final User user, final String password) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (password == null) {
			throw new IllegalArgumentException("password cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return userDao.changePassword(user, passwordEncoder.encode(password));
	}

	@Override
	public boolean isUsersPassword(final User user, final String password) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (password == null) {
			throw new IllegalArgumentException("password cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return passwordEncoder.matches(password, user.getPassword());
		
	}

	@Override
	public Set<Favourite> getAllFavourites(final User user) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return favouriteService.getFavouritesForUser(user);
		
	}
	
	@Override
	public PagedResult<Favourite> getFavourites(final User user, final Filter filter) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (filter == null) {
			throw new IllegalArgumentException("filter cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return favouriteService.getFavouritesForUser(user, filter);
	}

	@Override
	public Favourite addFavourite(final FavouriteBuilder builder) {
		
		if (builder == null) {
			throw new IllegalArgumentException("builder cant be null");
		}
		
		if (builder.getUser() == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (builder.getItem() == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		
		if (!userExists(builder.getUser())) {
			throw new IllegalArgumentException("user must exist");
		}
		
		if (!itemService.itemExists(builder.getItem())) {
			throw new IllegalArgumentException("item must exist");
		}
		
		return favouriteService.addFavourite(builder);
	}
	
	@Override
	public boolean removeFavourite(final Favourite favourite) {
		
		if (favourite == null) {
			throw new IllegalArgumentException("favourite cant be null");
		}
		
		if (!favouriteService.exists(favourite)) {
			throw new IllegalArgumentException("favourite must exist");
		}
		
		return favouriteService.removeFavourite(favourite);
	}

	@Override
	public Comment issueComment(final CommentBuilder builder) {
		
		return commentService.createComment(builder);
	}

	@Override
	public int getNumberOfUsers() {
		return userDao.getNumberOfUsers();
	}

	@Override
	public User createUser(final UserBuilder builder, final Role role) {
		
		if (builder == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (role == null) {
			throw new IllegalArgumentException("role cant be null");
		}
		
		if (builder.getId() != null) {
			throw new IllegalArgumentException("user must not exist");
		}
		
		if (usernameExists(builder.getUsername())) {
			throw new IllegalArgumentException("username must not exist");
		}
		
		if (emailExists(builder.getUsername())) {
			throw new IllegalArgumentException("email must not exist");
		}
		
		if (!roleService.roleExists(role)) {
			throw new IllegalArgumentException("role must exist");
		}
		
		final User user = userDao.create(builder);
		
		if (userDao.addRole(user, role)) {
			return user;
		}
		
		return null;
	}

	@Override
	public Set<Role> getRoles(final User user) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
//		final User u = userDao.findById(user.getId());
		
		// little hibernate trick
//		u.getRoles().iterator();
		
//		return u.getRoles();
		
		return roleService.getRolesForUser(user);
		
//		return roleService.getRolesForUser(user);
	}

	@Override
	public boolean addRole(final User user, final Role role) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (role == null) {
			throw new IllegalArgumentException("role cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		if (!roleService.roleExists(role)) {
			throw new IllegalArgumentException("role must exist");
		}
		
		return userDao.addRole(user, role);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userDao.getAll();
	}

	@Override
	public boolean updateUser(final User user) {
		
		if (user == null) {
			throw new IllegalArgumentException("user cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return userDao.updateUser(user);
	}

	@Override
	public boolean purchase(final PurchaseBuilder builder) {
		return userDao.purchase(builder);
	}
	
	@Override
	public PagedResult<Purchase> getTransactions(final User user, final Filter filter) {
		return userDao.getTransactions(user, filter);
	}
	
	@Override
	public List<Purchase> getAllTransactions(final User user) {
		return userDao.getTransactions(user);
	}
	
	@Override
	public Map<PurchaseStatus, Set<Purchase>> getGroupedTransactions(final User user) {
		
		final Map<PurchaseStatus, Set<Purchase>> result = new HashMap<>();
		
		for (final PurchaseStatus status : PurchaseStatus.values()) {
			result.put(status, new HashSet<>());
		}
		
		final List<Purchase> purchases = getAllTransactions(user);
		
		for (final Purchase purchase : purchases) {
			result
			.get(purchase.getStatus()).add(purchase);
		}
		
		return result;
	}

	@Override
	public List<Purchase> getPendingTransactions(final User user) {
		
		return userDao.getTransactions(user, PurchaseStatus.PENDING);
	}
	
	@Override
	public List<Purchase> getDeclinedTransactions(final User user) {
		
		return userDao.getTransactions(user, PurchaseStatus.DECLINED);
	}
	
	@Override
	public List<Purchase> getApprovedTransactions(final User user) {
		
		return userDao.getTransactions(user, PurchaseStatus.APPROVED);
	}

	@Override
	public PagedResult<Purchase> getPurchases(final User user, final Filter filter) {
		
		return userDao.getPurchases(user, filter);
	}
	
	@Override
	public Set<Purchase> getAllPurchases(final User user) {
		
		return userDao.getPurchases(user);
	}
	
	@Override
	public Map<PurchaseStatus, Set<Purchase>> getGroupedPurchases(final User user) {
		
		final Map<PurchaseStatus, Set<Purchase>> result = new HashMap<>();
		
		for (final PurchaseStatus status : PurchaseStatus.values()) {
			result.put(status, new HashSet<>());
		}
		
		final Set<Purchase> purchases = getAllPurchases(user);
		
		for (final Purchase purchase : purchases) {
			result.get(purchase.getStatus()).add(purchase);
		}
		
		return result;
	}

	@Override
	public List<Purchase> getPendingPurchases(final User user) {
		
		return userDao.getPurchases(user, PurchaseStatus.PENDING);
	}
	
	@Override
	public List<Purchase> getDeclinedPurchases(final User user) {
		
		return userDao.getPurchases(user, PurchaseStatus.DECLINED);
	}
	
	@Override
	public List<Purchase> getApprovedPurchases(final User user) {
		
		return userDao.getPurchases(user, PurchaseStatus.APPROVED);
	}

	@Override
	public PagedResult<StoreItem> getPublishedItems(final User user, final Filter filter) {
		return itemService.getUserItems(user, filter);
	}
	
	@Override
	public List<StoreItem> getAllPublishedItems(final User user) {
		return itemService.getUserItems(user);
	}

	@Override
	public List<StoreItem> getActivePublishedItems(final User user) {
		
		return itemService.getUserItems(user, StoreItemStatus.ACTIVE);
	}
	
	@Override
	public List<StoreItem> getPausedPublishedItems(final User user) {
		
		return itemService.getUserItems(user, StoreItemStatus.PAUSED);
	}
	
	@Override
	public Map<StoreItemStatus, Set<StoreItem>> getPublishedItemsGroupedByStatus(final User user) {
		
		final Map<StoreItemStatus, Set<StoreItem>> result = new HashMap<>();
		
		for (final StoreItemStatus status : StoreItemStatus.values()) {
			result.put(status, new HashSet<>());
		}
		
		final List<StoreItem> publishedItems = getAllPublishedItems(user);
		
		for (final StoreItem item : publishedItems) {
			result.get(item.getStatus()).add(item);
		}
		
		return result;
		
	}

	@Override
	public boolean approvePurchase(final User user, final Purchase purchase) {
		
		if (user == null) {
			throw new IllegalStateException("user cant be null");
		}
		
		if (purchase == null) {
			throw new IllegalStateException("purchase cant be null");
		}
		
		if (!userExists(user)) {
			throw new IllegalStateException("user must exist");
		}
		
		// purchase exist
		
		// item owner is user
		
		return purchaseService.approvePurchase(purchase);
	}

	@Override
	public boolean declinePurchase(final User user, final Purchase purchase) {
		
		return purchaseService.declinePurchase(purchase);
	}
}
