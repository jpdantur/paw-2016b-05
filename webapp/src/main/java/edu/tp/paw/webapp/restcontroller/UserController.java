package edu.tp.paw.webapp.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.webapp.dto.UserDTO;
import edu.tp.paw.webapp.form.SearchForm;

@Path("/api/users")
@Component
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	@GET
	@Path("{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response user(@PathParam("id") final long id) {
		
		final User user = userService.findById(id);
		
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(UserDTO.fromUser(user)).build();
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") final long id, final UserDTO userDto, @Context SecurityContext context) {
		
		final User u = userService.findById(id);
		
		if (u == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		if (!context.getUserPrincipal().getName().equals(u.getUsername())) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final User user = new UserBuilder(u).email(userDto.getEmail()).lastName(userDto.getLastName()).firstName(userDto.getFirstName()).build();
		
		if (userService.updateUser(user)) {
			return Response.ok(UserDTO.fromUser(user)).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
	}
	
	@GET
	@Path("{id}/favourites")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response userFavs(@PathParam("id") final long id, @Context SecurityContext context, final ProfileItemSearchForm form) {
		
		final User user = userService.findById(id);
		
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		if (!context.getUserPrincipal().getName().equals(user.getUsername())) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final Filter filter =
				FilterBuilder
				.create()
				.query()
					.text(form.getQuery())
				.and().page()
					.size(form.getPageSize())
					.take(form.getPageNumber())
				.and().sort()
					.by(form.orderBy())
					.order(form.sortOrder())
				.end().build();
		
		final  userService.getFavourites(user, null);
		
		return Response.ok(UserDTO.fromUser(user)).build();
	}
}
