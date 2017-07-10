package edu.tp.paw.webapp.restcontroller;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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
import edu.tp.paw.interfaces.service.IPurchaseService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReviewBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.service.PurchaseService;
import edu.tp.paw.webapp.dto.PurchaseDTO;
import edu.tp.paw.webapp.dto.PurchaseReviewWriteDTO;
import edu.tp.paw.webapp.dto.StoreItemDTO;
import edu.tp.paw.webapp.dto.StoreItemWriteDTO;

@Path("/store/sales")
@Component
public class SalesController {

	private static final Logger logger = LoggerFactory.getLogger(SalesController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private IStoreService storeService;
	@Autowired private IUserService userService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IPurchaseService purchaseService;
	
	@Context private UriInfo uriInfo;
	
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
	public Response byId(@PathParam("id") long id) {
		
		final Purchase purchase = purchaseService.findById(id);
		
		if (purchase == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(new PurchaseDTO(purchase)).build();
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
	@Path("/{id}/approve")
	@Produces(MediaType.APPLICATION_JSON)
	public Response approve(@PathParam("id") long id, @Context SecurityContext context) {
		
		final Purchase purchase = purchaseService.findById(id);
		
		if (purchase == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		if (userService.approvePurchase(user, purchase)) {
			return Response.ok().build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@PUT
	@Path("/{id}/decline")
	@Produces(MediaType.APPLICATION_JSON)
	public Response decline(@PathParam("id") long id, @Context SecurityContext context) {
		
		final Purchase purchase = purchaseService.findById(id);
		
		if (purchase == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		if (userService.approvePurchase(user, purchase)) {
			return Response.ok().build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/{id}/buyer-review")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buyerReview(@PathParam("id") long id, PurchaseReviewWriteDTO form, @Context SecurityContext context) {
		
		final Purchase purchase = purchaseService.findById(id);
		
		if (purchase == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		final PurchaseReviewBuilder builder = new PurchaseReviewBuilder(form.getComment()).rating(form.getRating());
		
		if (userService.reviewPurchaseAsBuyer(user, purchase, builder)) {
			return Response.status(Status.CREATED).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/{id}/seller-review")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sellerReview(@PathParam("id") long id, PurchaseReviewWriteDTO form, @Context SecurityContext context) {
		
		final Purchase purchase = purchaseService.findById(id);
		
		if (purchase == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)context.getUserPrincipal(); 
		final User user = userService.findByUsername(userDetails.getName());
		
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		logger.trace(form.toString());
		
		final PurchaseReviewBuilder builder = new PurchaseReviewBuilder(form.getComment()).rating(form.getRating());
		
		if (userService.reviewPurchaseAsSeller(user, purchase, builder)) {
			return Response.status(Status.CREATED).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete() {
		return Response.status(Status.METHOD_NOT_ALLOWED).build();
	}
	
}
