package edu.tp.paw.interfaces.dao;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PagedResult;

public interface IUserDao {

	/**
	 * Retrieve an user by id
	 * @param id The id of the user to retrieve
	 * @return The requested user, or null if not found
	 */
	public User findById(final long id);
	
	public User findByUsername(final String username);
	public User findByEmail(final String email);
	
	public boolean idExists(final long id);
	public boolean usernameExists(final String username);
	public boolean emailExists(final String email);
	
	/**
	 * Create a new user
	 * 
	 * @param username The name of the user
	 * @return The created user
	 */
	public User create(final UserBuilder builder);
	
	public boolean changePassword(final User user, final String password);
	
//	public boolean addFavourite(final FavouriteBuilder builder);
//	public Set<Favourite> getFavourites(final User user);
//	public List<Favourite> getFavourites(final User user, final Filter filter);
//	public boolean removeFavourite(final User user, final StoreItem item);
	
	public int getNumberOfUsers();
	
	public boolean addRole(final User user, final Role role);
	
	public List<User> getAll();
	
	public boolean updateUser(final User user);
	
	public boolean purchase(final PurchaseBuilder builder);
	
	public PagedResult<Purchase> getTransactions(final User user, final Filter filter);
	public List<Purchase> getTransactions(final User user);
	public List<Purchase> getTransactions(final User user, final PurchaseStatus status);
	
	public PagedResult<Purchase> getPurchases(final User user, final Filter filter);
	public Set<Purchase> getPurchases(final User user);
	public List<Purchase> getPurchases(final User user, final PurchaseStatus status);


	
}
