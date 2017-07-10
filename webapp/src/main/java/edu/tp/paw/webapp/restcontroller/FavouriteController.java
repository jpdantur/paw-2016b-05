package edu.tp.paw.webapp.restcontroller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IFavouriteService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.FavouriteBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;
import edu.tp.paw.webapp.dto.FavouriteDTO;
import edu.tp.paw.webapp.dto.FavouriteWriteDTO;
import edu.tp.paw.webapp.dto.StoreItemDTO;
import edu.tp.paw.webapp.dto.StoreItemWriteDTO;

@Path("/me/favourites")
@Component
public class FavouriteController {

	private static final Logger logger = LoggerFactory.getLogger(FavouriteController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private IStoreService storeService;
	@Autowired private IUserService userService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IFavouriteService favouriteService;
	
	@Context private UriInfo uriInfo;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response favourites(
			@Context SecurityContext context,
			@DefaultValue("0") @QueryParam("pageNumber") final int pageNumber,
			@DefaultValue("20") @QueryParam("pageSize") final int pageSize,
			@QueryParam("query") final String query,
			@DefaultValue("ASC") @QueryParam("sortOrder") final SortOrder sortOrder,
			@DefaultValue("PRICE") @QueryParam("sortField") final SortField sortField) {
		
		logger.trace("published request");
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		logger.trace("user found");
		
//		ItemStatusFilter s;
//		try {
//			s = ItemStatusFilter.valueOf(status.toUpperCase());
//		} catch (Exception e) {
//			s = ItemStatusFilter.ANY;
//		}
		
		logger.trace("decided filter");
		
//		logger.trace(form.toString());
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(query)
				.and().page()
					.size(pageSize)
					.take(pageNumber)
				.and().sort()
					.by(sortField)
					.order(sortOrder)
				.end().build();
		
		final PagedResult<Favourite> queryResult = userService.getFavourites(user, filter);
		
		logger.trace("filtered");
		
		final PagedResult<FavouriteDTO> dtoResult = new PagedResult<FavouriteDTO>(
				queryResult.getNumberOfTotalResults(),
				queryResult.getNumberOfAvailableResults(),
				queryResult.getPageSize(),
				queryResult.getCurrentPage(),
				queryResult.getResults().stream().map(v -> new FavouriteDTO(v)).collect(Collectors.toList())
		);
		
		GenericEntity<PagedResult<FavouriteDTO>> e = new GenericEntity<PagedResult<FavouriteDTO>>(dtoResult) {
			
		};
		
		logger.trace("converted result");
		
		return Response.ok(e).build();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response all(@Context SecurityContext context) {
		
		logger.trace("published request");
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		List<FavouriteDTO> favs = userService.getAllFavourites(user).stream().map(v -> new FavouriteDTO(v)).collect(Collectors.toList());
		
		GenericEntity<List<FavouriteDTO>> e = new GenericEntity<List<FavouriteDTO>>(favs) {
			
		};
		
		return Response.ok(
				e
		).build();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Context SecurityContext context, final FavouriteWriteDTO form) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final StoreItem item = storeItemService.findById(form.getItem());
		
		if (item == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		final Favourite favourite = userService.addFavourite(new FavouriteBuilder(item, user));
		
		if (favourite == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.status(Status.CREATED).entity(new FavouriteDTO(favourite)).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") long id) {
		
		final Favourite fav = favouriteService.findById(id);
		
		if (fav == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		if (userService.removeFavourite(fav)) {
			return Response.noContent().build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
	}
	
}
