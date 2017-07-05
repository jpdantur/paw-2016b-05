package edu.tp.paw.webapp.restcontroller;

import java.net.URI;
import java.util.List;

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
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.webapp.dto.StoreItemDTO;
import edu.tp.paw.webapp.dto.StoreItemWriteDTO;

@Path("/api/store/item")
@Component
public class StoreItemController {

	private static final Logger logger = LoggerFactory.getLogger(StoreItemController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private IStoreService storeService;
	@Autowired private IUserService userService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IImageService imageService;
	
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
		
		final StoreItem item = storeItemService.findById(id);
		
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(new StoreItemDTO(item)).build();
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

		StringBuffer fileDetails = new StringBuffer("");

		/* Save multiple files */
		
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
	
	@DELETE
	@Path("/{id}")
	public Response delete() {
		return Response.status(Status.METHOD_NOT_ALLOWED).build();
	}
	
}
