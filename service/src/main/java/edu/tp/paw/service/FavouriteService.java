package edu.tp.paw.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IFavouriteDao;
import edu.tp.paw.interfaces.service.IFavouriteService;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;

@Service
public class FavouriteService implements IFavouriteService {
	
	@Autowired private IFavouriteDao favouriteDao; 
	
	@Override
	public boolean exists(final Favourite favourite) {
		if (favourite == null) {
			throw new IllegalArgumentException("favourite cant be null");
		}
		return favouriteDao.exists(favourite);
	}

	@Override
	public Favourite addFavourite(final FavouriteBuilder builder) {
		return favouriteDao.createFavourite(builder);
	}

	@Override
	public boolean removeFavourite(final Favourite favourite) {
		return favouriteDao.removeFavourite(favourite);
	}

	@Override
	public Set<Favourite> getFavouritesForUser(final User user) {
		return favouriteDao.getFavouritesForUser(user);
	}

	@Override
	public List<Favourite> getFavouritesForUser(final User user, final Filter filter) {
		return favouriteDao.getFavouritesForUser(user, filter);
	}

	@Override
	public Favourite findById(final long id) {
		return favouriteDao.findById(id);
	}

}
