package edu.tp.paw.interfaces.service;

import java.util.Set;

import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PagedResult;

public interface IFavouriteService {

	public boolean exists(final Favourite favourite);
	
	public Favourite findById(final long id);
	
	public Favourite addFavourite(final FavouriteBuilder builder);
	public boolean removeFavourite(final Favourite favourite);
	
	public Set<Favourite> getFavouritesForUser(final User user);
	public PagedResult<Favourite> getFavouritesForUser(final User user, final Filter filter); 
	
}
