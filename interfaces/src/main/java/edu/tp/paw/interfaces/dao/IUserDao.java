package edu.tp.paw.interfaces.dao;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

public interface IUserDao {

	/**
	 * Retrieve an user by id
	 * @param id The id of the user to retrieve
	 * @return The requested user, or null if not found
	 */
	User findById(final long id);
	
	User findByUsername(final String username);
	User findByEmail(final String email);
	
	boolean idExists(final long id);
	boolean usernameExists(final String username);
	boolean emailExists(final String email);
	
	/**
	 * Create a new user
	 * 
	 * @param username The name of the user
	 * @return The created user
	 */
	User create(final UserBuilder builder);
	
	boolean changePassword(final User user, final String password);
	
	boolean addFavourite(final User user, final StoreItem item);
	Set<StoreItem> getFavourites(final User user);
	boolean removeFavourite(final User user, final StoreItem item);
	
	int getNumberOfUsers();
	
	public boolean addRole(final User user, final Role role);
	
	public List<User> getAll();
	
	public boolean updateUser(final User user);
	
	public boolean purchase(final PurchaseBuilder builder);
	
	public List<Purchase> getTransactions(final User user);
	public List<Purchase> getTransactions(final User user, final PurchaseStatus status);
	public Set<Purchase> getPurchases(final User user);
	
}
