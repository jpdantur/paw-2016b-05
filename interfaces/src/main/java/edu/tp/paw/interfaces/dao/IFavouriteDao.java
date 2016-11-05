package edu.tp.paw.interfaces.dao;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;

public interface IFavouriteDao {

	public boolean exists(final Favourite favourite);
	
	public Favourite findById(final long id);
	
	public Favourite createFavourite(final FavouriteBuilder builder);
	public boolean removeFavourite(final Favourite favourite);
	
	public Set<Favourite> getFavouritesForUser(final User user);
	public List<Favourite> getFavouritesForUser(final User user, final Filter filter);
	
}
