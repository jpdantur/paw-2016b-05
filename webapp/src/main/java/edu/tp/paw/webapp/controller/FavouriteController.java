package edu.tp.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.tp.paw.interfaces.service.IFavouriteService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

@Controller
@RequestMapping("/favourites")
public class FavouriteController extends BaseController {

	@Autowired IStoreItemService itemService;
	@Autowired IUserService userService;
	@Autowired IFavouriteService favouriteService;
	
	@RequestMapping( value = "/add/{itemId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String makeFavourite(
			@PathVariable("itemId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final StoreItem item = itemService.findById(id);
		
		if (item == null) {
			return "{\"err\":3 }";
		}
		
		final FavouriteBuilder builder = new FavouriteBuilder(item, user);
		final Favourite favourite = userService.addFavourite(builder); 
		if (favourite != null) {
			
			return "{\"err\":0 }";
		}
		
		return "{\"err\":2 }";
	}
	
	@RequestMapping( value = "/remove/{favId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String removeFavourite(
			@PathVariable("favId") final long id,
			@ModelAttribute("loggedUser") final User user
			) {
		
		final Favourite fav = favouriteService.findById(id);
		
		if (fav == null) {
			return "{\"err\":3 }";
		}
		
		if (userService.removeFavourite(fav)) {
			
			return "{\"err\":0 }";
		}
		
		return "{\"err\":2 }";
	}
	
}
