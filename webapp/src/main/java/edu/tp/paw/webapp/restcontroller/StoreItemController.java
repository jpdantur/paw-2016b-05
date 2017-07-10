package edu.tp.paw.webapp.restcontroller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IImageService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.StoreItemStatus;
import edu.tp.paw.model.User;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;
import edu.tp.paw.webapp.auth.TokenHelper;
import edu.tp.paw.webapp.dto.CommentDTO;
import edu.tp.paw.webapp.dto.PurchaseDTO;
import edu.tp.paw.webapp.dto.StoreItemDTO;
import edu.tp.paw.webapp.dto.StoreItemWriteDTO;
import edu.tp.paw.webapp.form.CommentForm;
import io.jsonwebtoken.Claims;

@Path("/store/item")
@Component
public class StoreItemController {

	private static final Logger logger = LoggerFactory.getLogger(StoreItemController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private IStoreService storeService;
	@Autowired private IUserService userService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IImageService imageService;
	
	@Context private UriInfo uriInfo;
	
	@Autowired private TokenHelper tokenHelper;
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Context SecurityContext context, final StoreItemWriteDTO form) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final Category category = categoryService.findById(form.getCategory());
		final StoreItemBuilder storeItemBuilder =
				new StoreItemBuilder(form.getName(), form.getDescription(), form.getPrice(), form.isUsed())
				.category(category)
				.owner(user)
				.status(form.getStatus());
		
		final StoreItem storeItem = storeService.sell(storeItemBuilder);
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.format("/api/store/item/%d", storeItem.getId())).build();
		
		return Response.created(uri).entity(new StoreItemDTO(storeItem)).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byId(
			@PathParam("id") long id,
			@Context SecurityContext context,
			@Context HttpServletRequest request) {
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		if (item.getStatus() == StoreItemStatus.ACTIVE) {
			return Response.ok(new StoreItemDTO(item)).build();
		}
		
		String auth = request.getHeader("Authorization");
		
		logger.trace(auth);
		
		if (auth == null || auth.trim().equals("") || !auth.toLowerCase().startsWith("bearer ")) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		Claims claims = tokenHelper.decodeToken(auth.substring(7)); 
		final User user = userService.findByUsername(claims.getSubject());
		
		logger.trace(claims.toString());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		logger.trace(user.toString());
		
		if (user.getUsername().equals(item.getOwner().getUsername())) {
			return Response.ok(new StoreItemDTO(item)).build();
		}
		
		return Response.status(Status.FORBIDDEN).build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, final StoreItemWriteDTO form, @Context SecurityContext context) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final Category category = categoryService.findById(form.getCategory());
		final StoreItem storeItem =
				new StoreItemBuilder(form.getName(), form.getDescription(), form.getPrice(), form.isUsed())
				.category(category)
				.owner(user)
				.status(form.getStatus())
				.id(id)
				.created(item.getCreated())
				.sold(item.getSold())
				.build();
		
		storeItemService.updateItem(storeItem);
		
		return Response.ok(new StoreItemDTO(storeItem)).build();
	}
	
	@PUT
	@Path("/{id}/images")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response images(
			@PathParam("id") long id,
			final FormDataMultiPart multiPart,
			@Context SecurityContext context) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		List<FormDataBodyPart> bodyParts = multiPart.getFields("images");
		
		imageService.removeImagesForItem(item);
		
		try {
			for (int i = 0; i < bodyParts.size(); i++) {
				BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyParts.get(i).getEntity();
				final StoreImageBuilder builder = new StoreImageBuilder(
						bodyParts.get(i).getMediaType().toString(),
						IOUtils.toByteArray(bodyPartEntity.getInputStream()
				)).item(item);
				
				logger.trace("uploading file " + (i+1));
				
				imageService.uploadImage(builder);
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/{id}/purchase")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response purchase(@PathParam("id") long id, @Context SecurityContext context) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final PurchaseBuilder builder = new PurchaseBuilder(user, item);
		final Purchase purchase = storeService.purchase(builder);
		
		if (purchase != null) {
			logger.trace(purchase.toString());
			return Response.ok(new PurchaseDTO(purchase)).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@GET
	@Path("/{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response comments(@PathParam("id") long id) {
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		List<CommentDTO> comments = storeItemService.getComments(item).stream().map(v -> new CommentDTO(v)).collect(Collectors.toList());
		
		GenericEntity<List<CommentDTO>> e = new GenericEntity<List<CommentDTO>>(comments) {
			
		};
		
		return Response.ok(e).build();
	}
	
	@POST
	@Path("/{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComments(@PathParam("id") long id, CommentForm form, @Context SecurityContext context) {
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final CommentBuilder builder = new CommentBuilder(user, form.getContent(), form.getRating()).item(item);
		final Comment c = storeItemService.addComment(builder);
		
		return Response.status(Status.CREATED).entity(new CommentDTO(c)).build();
	}
	
	@GET
	@Path("/{id}/related")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sales(
			@PathParam("id") long id,
			@Context SecurityContext context,
			@DefaultValue("0") @QueryParam("pageNumber") final int pageNumber,
			@DefaultValue("20") @QueryParam("pageSize") final int pageSize,
			@QueryParam("query") final String query,
			@DefaultValue("ASC") @QueryParam("sortOrder") final SortOrder sortOrder,
			@DefaultValue("PRICE") @QueryParam("sortField") final SortField sortField) {
		
		logger.trace("published request");
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
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
		
		final PagedResult<StoreItem> queryResult = userService.getPublishedItems(item.getOwner(), filter);
		
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
	
	@DELETE
	@Path("/{id}")
	public Response delete() {
		return Response.status(Status.METHOD_NOT_ALLOWED).build();
	}
	
}
