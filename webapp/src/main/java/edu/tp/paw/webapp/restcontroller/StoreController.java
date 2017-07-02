package edu.tp.paw.webapp.restcontroller;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.org.apache.bcel.internal.generic.NEW;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.webapp.dto.CategoryListDTO;
import edu.tp.paw.webapp.dto.StoreItemListDTO;

@Path("/api/store")
@Component
public class StoreController {

	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private ICategoryService categoryService;
	
//	private final static int MOST_SOLD = 12;
	
	@GET
	@Path("/most-sold")
	@Produces(MediaType.APPLICATION_JSON)
	public Response mostSold(@QueryParam("limit")  @DefaultValue("12") final Integer limit) {
		
		logger.trace("most-sold");
		
//		int l = limit == null ? MOST_SOLD : limit;
		
//		logger.trace("limit: " + l);
		
		return Response.ok(
				new StoreItemListDTO(
						storeItemService.getMostSold(limit)
				)
		).build();
	}
	
	@GET
	@Path("/category-tree")
	@Produces(MediaType.APPLICATION_JSON)
	public Response categoryTree() {
		
		return Response.ok(new CategoryListDTO(categoryService.getCategoryTree())).build();
	}
	
}
