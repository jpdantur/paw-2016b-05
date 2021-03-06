package edu.tp.paw.interfaces.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.PurchaseReviewBuilder;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PagedResult;

public interface IUserService {

	/**
	 * Retrieve an user by id
	 * @param id The id of the user to retrieve
	 * @return The requested user, or null if not found
	 */
	public User findById(final long id);
	public User findByUsername(final String username);
	public User findByEmail(final String email);
	
	boolean userExists(final User user);
	boolean userExists(final long id);
	boolean usernameExists(final String username);
	boolean emailExists(final String email);
	
	/**
	 * Create a new user
	 * 
	 * @param username The name of the user
	 * @return The created user
	 */
	public User registerUser(final UserBuilder builder);
	
	public User createUser(final UserBuilder builder, final Role role);
	
	public boolean changePassword(final User user, final String password);
	
	public boolean isUsersPassword(final User user, final String password);
	
	public List<Favourite> getAllFavourites(final User user);
	public PagedResult<Favourite> getFavourites(final User user, final Filter filter);
	
	public Favourite addFavourite(final FavouriteBuilder builder);
	public boolean removeFavourite(final Favourite favourite);
	
	public Comment issueComment(final CommentBuilder builder);
	
	public int getNumberOfUsers();
	
	public Set<Role> getRoles(final User user);
	public boolean addRole(final User user, final Role role);
	
	public List<User> getAllUsers();
	
	public boolean updateUser(final User user);
	
	public Purchase purchase(final PurchaseBuilder builder);
	
	public PagedResult<Purchase> getPurchases(final User user, final Filter filter);
	public Set<Purchase> getAllPurchases(final User user);
	public Map<PurchaseStatus, Set<Purchase>> getGroupedPurchases(final User user);
	public List<Purchase> getPendingPurchases(final User user);
	public List<Purchase> getApprovedPurchases(final User user);
	public List<Purchase> getDeclinedPurchases(final User user);
	
	public int getNumberOfPurchases(final User user, final Filter filter);
	public int getNumberOfPurchases(final User user);
	public int getNumberOfPendingPurchases(final User user);
	public int getNumberOfApprovedPurchases(final User user);
	public int getNumberOfDeclinedPurchases(final User user);
	
	public PagedResult<Purchase> getTransactions(final User user, final Filter filter);
	public List<Purchase> getAllTransactions(final User user);
	public Map<PurchaseStatus, Set<Purchase>> getGroupedTransactions(final User user);
	public List<Purchase> getPendingTransactions(final User user);
	public List<Purchase> getApprovedTransactions(final User user);
	public List<Purchase> getDeclinedTransactions(final User user);
	
	public int getNumberOfTransactions(final User user, final Filter filter);
	public int getNumberOfTransactions(final User user);
	public int getNumberOfPendingTransactions(final User user);
	public int getNumberOfApprovedTransactions(final User user);
	public int getNumberOfDeclinedTransactions(final User user);
	
	public PagedResult<StoreItem> getPublishedItems(final User user, final Filter filter);
	
	public List<StoreItem> getAllPublishedItems(final User user);
	public List<StoreItem> getActivePublishedItems(final User user);
	public List<StoreItem> getPausedPublishedItems(final User user);
//	public Set<StoreItem> getPublishedItemsWithStatus(final User user, final StoreItemStatus status);
	public Map<StoreItemStatus, Set<StoreItem>> getPublishedItemsGroupedByStatus(final User user);
	
	public boolean approvePurchase(final User user, final Purchase purchase);
	public boolean declinePurchase(final User user, final Purchase purchase);
	public boolean reviewPurchaseAsBuyer(final User user, final Purchase purchase, final PurchaseReviewBuilder builderReview);
	public boolean reviewPurchaseAsSeller(final User user, final Purchase purchase, final PurchaseReviewBuilder builderReview);
	
	
	public float getBuyerRating(final User user);
	public float getSellerRating(final User user);
	public boolean recoverPassword(final User email);
	
}
