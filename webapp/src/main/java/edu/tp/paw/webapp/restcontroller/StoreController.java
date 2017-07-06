package edu.tp.paw.webapp.restcontroller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IStoreService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.filter.FilterBuilder;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.OrderFilter.SortField;
import edu.tp.paw.model.filter.OrderFilter.SortOrder;
import edu.tp.paw.model.filter.StoreItemStatusFilter.ItemStatusFilter;
import edu.tp.paw.webapp.dto.CategoryDTO;
import edu.tp.paw.webapp.dto.CategoryListDTO;
import edu.tp.paw.webapp.dto.PurchaseDTO;
import edu.tp.paw.webapp.dto.SearchResultDTO;
import edu.tp.paw.webapp.dto.StoreItemDTO;
import edu.tp.paw.webapp.dto.StoreItemListDTO;

@Path("/api/store")
@Component
public class StoreController {

	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private ICategoryService categoryService;
	
	@Autowired private IStoreService storeService;
	
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
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response all(
			@Context SecurityContext context,
			@DefaultValue("0") @QueryParam("pageNumber") final int pageNumber,
			@DefaultValue("20") @QueryParam("pageSize") final int pageSize,
			@QueryParam("query") final String query,
			@DefaultValue("ASC") @QueryParam("sortOrder") final SortOrder sortOrder,
			@DefaultValue("PRICE") @QueryParam("sortField") final SortField sortField,
			@QueryParam("minPrice") BigDecimal minPrice,
			@QueryParam("maxPrice") BigDecimal maxPrice,
			@QueryParam("categories") List<Long> categories
	) {
		
		final FilterBuilder filter = FilterBuilder
				.create()
				.status()
					.status(ItemStatusFilter.ACTIVE)
				.end().price()
					.between(minPrice, maxPrice)
				.end().page()
					.size(pageSize)
					.take(pageNumber)
				.end().query()
					.text(query)
				.end().sort()
					.by(sortField)
					.order(sortOrder)
				.end();
		
		List<Category> selectedCategories = new ArrayList<>();
		
		if (categories != null) {
			// map values
			selectedCategories = categories.stream().map( id -> categoryService.findById(id.longValue())).collect(Collectors.toList());
			// add values to filter
			filter.category().in(selectedCategories).end();
		}
		
		final PagedResult<StoreItem> pagedResults = storeService.search(filter);
		
		filter.page().size(pagedResults.getNumberOfTotalResults()).take(0);
		
		final PagedResult<StoreItem> pagedResults2 = storeService.search(filter);
		
		final Set<Category> similarCategories = storeService
				.getCategoriesForResultsInHigherDepthCategories(
						filter.getCategoryFilter().getCategories(),
						pagedResults2.getResults()
		);
		
		final SearchResultDTO<StoreItemDTO> dto = new SearchResultDTO<>(
				pagedResults.getNumberOfTotalResults(),
				pagedResults.getNumberOfAvailableResults(),
				pagedResults.getPageSize(),
				pagedResults.getCurrentPage(),
				pagedResults.getResults().stream().map(v -> new StoreItemDTO(v)).collect(Collectors.toList()),
				similarCategories.stream().map(v -> new CategoryDTO(v)).collect(Collectors.toList()),
				selectedCategories.stream().map(v -> new CategoryDTO(v)).collect(Collectors.toList())
		);		
		
		GenericEntity<SearchResultDTO<StoreItemDTO>> e = new GenericEntity<SearchResultDTO<StoreItemDTO>>(dto) {
			
		};
		
		return Response.ok(e).build();
	}
	
}
