package edu.tp.paw.webapp.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;
import edu.tp.paw.webapp.dto.CommentDTO;
import edu.tp.paw.webapp.dto.StoreItemDTO;
import edu.tp.paw.webapp.dto.UserBriefDTO;
import edu.tp.paw.webapp.dto.UserDTO;
import edu.tp.paw.webapp.form.ChangePasswordForm;

@Path("/id")
@Component
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired private IUserService userService;
	
	@GET
	@Path("/{username}")
	public Response username(@PathParam("username") String username) {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(UserDTO.fromUser(user)).build();
	}
	
	@GET
	@Path("/{username}/profile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response profile(@PathParam("username") String username) {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final UserBriefDTO ub = new UserBriefDTO(
				userService.getBuyerRating(user),
				userService.getSellerRating(user),
				userService.getNumberOfApprovedTransactions(user),
				userService.getNumberOfDeclinedTransactions(user),
				userService.getNumberOfApprovedPurchases(user),
				userService.getNumberOfDeclinedPurchases(user)
		);
		
		return Response.ok(ub).build();
	}
	
	@GET
	@Path("/{username}/published")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response published(
			@PathParam("username") String username,
			@Context SecurityContext context,
			@DefaultValue("0") @QueryParam("pageNumber") final int pageNumber,
			@DefaultValue("20") @QueryParam("pageSize") final int pageSize,
			@QueryParam("query") final String query,
			@DefaultValue("ASC") @QueryParam("sortOrder") final SortOrder sortOrder,
			@DefaultValue("PRICE") @QueryParam("sortField") final SortField sortField) {
		
		logger.trace("published request");
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
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
					.status(ItemStatusFilter.ACTIVE)
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
	
	@PUT
	@Path("/{username}/reset-password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePassword(
			@PathParam("username") String username,
			final ChangePasswordForm form) {
		
		final User user = userService.findByUsername(username);
		
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		if (!form.getPassword().equals(form.getRepeatPassword())) {
			return Response.status(Status.CONFLICT).build();
		}
		
		if (userService.changePassword(user, form.getPassword())) {
			return Response.noContent().build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
	}
}
