package edu.tp.paw.webapp.restcontroller;

import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Favourite;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;
import edu.tp.paw.webapp.dto.FavouriteDTO;
import edu.tp.paw.webapp.dto.PurchaseDTO;
import edu.tp.paw.webapp.dto.StoreItemDTO;
import edu.tp.paw.webapp.dto.UserDTO;
import edu.tp.paw.webapp.form.ChangePasswordForm;
import edu.tp.paw.webapp.form.ProfileItemSearchForm;

@Path("/api/me")
@Component
public class MeController {
	
	private static final Logger logger = LoggerFactory.getLogger(MeController.class);
	
	@Autowired
	private IUserService userService;
	
	@GET
	@Path("/profile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response profile(@Context SecurityContext context) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		return Response.ok(UserDTO.fromUser(user)).build();
	}
	
//	@GET
//	@Path("{id}")
//	@Produces(value = { MediaType.APPLICATION_JSON })
//	public Response user(@PathParam("id") final long id) {
//		
//		final User user = userService.findById(id);
//		
//		if (user == null) {
//			return Response.status(Status.NOT_FOUND).build();
//		}
//		
//		return Response.ok(UserDTO.fromUser(user)).build();
//	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(final UserDTO userDto, @Context SecurityContext context) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final User updatedUser = new UserBuilder(user).email(userDto.getEmail()).lastName(userDto.getLastName()).firstName(userDto.getFirstName()).build();
		
		if (userService.updateUser(updatedUser)) {
			return Response.ok(UserDTO.fromUser(updatedUser)).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
	}
	
	@PUT
	@Path("/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePassword(final ChangePasswordForm form, @Context SecurityContext context) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		if (!userService.isUsersPassword(user, form.getOldPassword())) {
			return Response.status(Status.CONFLICT).build();
		}
		
		if (!form.getPassword().equals(form.getRepeatPassword())) {
			return Response.status(Status.CONFLICT).build();
		}
		
		if (userService.changePassword(user, form.getPassword())) {
			return Response.noContent().build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
	}
	
	@GET
	@Path("/published")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response published(
			@Context SecurityContext context,
			@DefaultValue("0") @QueryParam("pageNumber") final int pageNumber,
			@DefaultValue("20") @QueryParam("pageSize") final int pageSize,
			@QueryParam("query") final String query,
			@DefaultValue("ASC") @QueryParam("sortOrder") final SortOrder sortOrder,
			@DefaultValue("PRICE") @QueryParam("sortField") final SortField sortField,
			@DefaultValue("ANY") @QueryParam("status") final ItemStatusFilter status) {
		
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
				.and().status()
					.status(status)
				.and().page()
					.size(pageSize)
					.take(pageNumber)
				.and().sort()
					.by(sortField)
					.order(sortOrder)
				.end().build();
		
		final PagedResult<StoreItem> queryResult = userService.getPublishedItems(user, filter);
		
		logger.trace("filtered");
		
		final PagedResult<StoreItemDTO> dtoResult = new PagedResult<StoreItemDTO>(
				queryResult.getNumberOfTotalResults(),
				queryResult.getNumberOfAvailableResults(),
				queryResult.getPageSize(),
				queryResult.getCurrentPage(),
				queryResult.getResults().stream().map(v -> new StoreItemDTO(v)).collect(Collectors.toList())
		);
		
		GenericEntity<PagedResult<StoreItemDTO>> e = new GenericEntity<PagedResult<StoreItemDTO>>(dtoResult) {
			
		};
		
		logger.trace("converted result");
		
		return Response.ok(e).build();
	}
	
	@GET
	@Path("/sales")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sales(
			@Context SecurityContext context,
			@DefaultValue("0") @QueryParam("pageNumber") final int pageNumber,
			@DefaultValue("20") @QueryParam("pageSize") final int pageSize,
			@QueryParam("query") final String query,
			@DefaultValue("ASC") @QueryParam("sortOrder") final SortOrder sortOrder,
			@DefaultValue("PRICE") @QueryParam("sortField") final SortField sortField,
			@DefaultValue("PENDING") @QueryParam("status") final PurchaseStatus status) {
		
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
				.and().purchaseStatus()
					.status(status)
				.and().page()
					.size(pageSize)
					.take(pageNumber)
				.and().sort()
					.by(sortField)
					.order(sortOrder)
				.end().build();
		
		final PagedResult<Purchase> queryResult = userService.getTransactions(user, filter);
		
		logger.trace("filtered");
		
		final PagedResult<PurchaseDTO> dtoResult = new PagedResult<PurchaseDTO>(
				queryResult.getNumberOfTotalResults(),
				queryResult.getNumberOfAvailableResults(),
				queryResult.getPageSize(),
				queryResult.getCurrentPage(),
				queryResult.getResults().stream().map(v -> new PurchaseDTO(v)).collect(Collectors.toList())
		);
		
		GenericEntity<PagedResult<PurchaseDTO>> e = new GenericEntity<PagedResult<PurchaseDTO>>(dtoResult) {
			
		};
		
		logger.trace("converted result");
		
		return Response.ok(e).build();
	}
	
	@GET
	@Path("/purchases")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response purchases(
			@Context SecurityContext context,
			@DefaultValue("0") @QueryParam("pageNumber") final int pageNumber,
			@DefaultValue("20") @QueryParam("pageSize") final int pageSize,
			@QueryParam("query") final String query,
			@DefaultValue("ASC") @QueryParam("sortOrder") final SortOrder sortOrder,
			@DefaultValue("PRICE") @QueryParam("sortField") final SortField sortField,
			@DefaultValue("PENDING") @QueryParam("status") final PurchaseStatus status) {
		
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
				.and().purchaseStatus()
					.status(status)
				.and().page()
					.size(pageSize)
					.take(pageNumber)
				.and().sort()
					.by(sortField)
					.order(sortOrder)
				.end().build();
		
		final PagedResult<Purchase> queryResult = userService.getPurchases(user, filter);
		
		logger.trace("filtered");
		
		final PagedResult<PurchaseDTO> dtoResult = new PagedResult<PurchaseDTO>(
				queryResult.getNumberOfTotalResults(),
				queryResult.getNumberOfAvailableResults(),
				queryResult.getPageSize(),
				queryResult.getCurrentPage(),
				queryResult.getResults().stream().map(v -> new PurchaseDTO(v)).collect(Collectors.toList())
		);
		
		GenericEntity<PagedResult<PurchaseDTO>> e = new GenericEntity<PagedResult<PurchaseDTO>>(dtoResult) {
			
		};
		
		logger.trace("converted result");
		
		return Response.ok(e).build();
	}
	
	@GET
	@Path("/favourites")
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
}
