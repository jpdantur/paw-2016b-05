package edu.tp.paw.persistence;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.model.filter.CategoryFilter;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.OrderFilter;
import edu.tp.paw.model.filter.PageFilter;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.PriceFilter;
import edu.tp.paw.model.filter.Range;
import edu.tp.paw.model.filter.TermFilter;

@Repository
public class StoreItemJdbcDao implements IStoreItemDao {

	private static final String ORDER_DESCENDING = "DESC";	
	private static final String ORDER_ASCENDING = "ASC";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	private static final ResultSetExtractor<List<StoreItem>> extractor = (ResultSet resultSet) -> {
		
		final Map<Long, StoreItemBuilder> items = new HashMap<>();
		
		while (resultSet.next()) {
			
			final User user = new UserBuilder(resultSet.getString("username"))
					.email(resultSet.getString("email"))
					.firstName(resultSet.getString("first_name"))
					.lastName(resultSet.getString("last_name"))
					.id(resultSet.getLong("user_id"))
					.build();
			
			final Category category = new CategoryBuilder(
						resultSet.getString("category_name"),
						resultSet.getLong("parent")
						)
					.id(resultSet.getLong("category_id"))
					.build();
			
			final StoreItemBuilder item = new StoreItemBuilder(
					resultSet.getString("name"),
					resultSet.getString("description"),
					resultSet.getBigDecimal("price"),
					category,
					resultSet.getBoolean("used")
					)
			.id(resultSet.getLong("item_id"))
			.created(resultSet.getTimestamp("created"))
			.lastUpdated(resultSet.getTimestamp("last_updated"))
			.sold(resultSet.getInt("sold"))
			.owner(user);
			
			
			if (!items.containsKey(item.getId())) {
				
				items.put(item.getId(), item);
				
			}
			
			if (resultSet.getString("mime_type") != null) {
				
				final StoreImage image = new StoreImageBuilder(
						resultSet.getString("mime_type"),
						resultSet.getBytes("content"))
						.id(resultSet.getLong("image_id"))
						.build();
				
				items.get(item.getId()).images(image);
			}
			
		}
		
		return items
				.values()
				.stream()
				.map( StoreItemBuilder::build )
				.collect(Collectors.toList());
		
	};

	/**
	 * Creates a StoreItem Dao for #{dataSource}
	 * @param dataSource The given dataSource
	 */
	@Autowired
	public StoreItemJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		jdbcInsert = new SimpleJdbcInsert(dataSource)
		.withTableName("store_items")
		.usingGeneratedKeyColumns("item_id")
		.usingColumns("name", "description", "price", "category", "used", "owner");
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#findById(long)
	 */
	@Override
	public StoreItem findById(long id) {

		List<StoreItem> itemsList =
				jdbcTemplate
				.getJdbcOperations()
				.query(
						"select * from store_items "
								+ "inner join users on store_items.owner=users.user_id "
								+ "inner join store_categories on store_items.category=store_categories.category_id "
								+ "left outer join images on store_items.item_id=images.item_id "
								+ "where store_items.item_id = ?",
						extractor,
						id);

		return itemsList.isEmpty() ? null : itemsList.get(0);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#findMostSold(int)
	 */
	@Override
	public List<StoreItem> findMostSold(int n) {

		if (n < 1) {
			return new ArrayList<StoreItem>();
		}

		return

				jdbcTemplate
				.getJdbcOperations()
				.query(
						"select * from store_items "
								+ "inner join users on store_items.owner=users.user_id "
								+ "inner join store_categories on store_items.category=store_categories.category_id "
								+ "left outer join images on store_items.item_id=images.item_id "
								+ "order by sold desc limit ?",
						extractor,
						n);
	}	

	@Override
	public StoreItem create(final StoreItemBuilder builder) {

		final Map<String, Object> args = new HashMap<>();

		args.put("name", builder.getName());
		args.put("description", builder.getDescription());
		args.put("price", builder.getPrice());
		args.put("category", builder.getCategory().getId());
		args.put("used", builder.isUsed());
		args.put("owner", builder.getOwner().getId());

		final Number storeItemId = jdbcInsert.executeAndReturnKey(args);

		return builder.id(storeItemId.longValue()).build();
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#findInCategories(java.util.List)
	 */
	@Override
	public List<StoreItem> findInCategories(List<Category> categories) {

		final MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("categories",
				categories.stream().map((category) -> {
						return category.getId();
				}).toArray());
		
		return
				jdbcTemplate
				.query(
						"select * from store_items "
								+ "inner join users on store_items.owner=users.user_id "
								+ "inner join store_categories on store_items.category=store_categories.category_id "
								+ "left outer join images on store_items.item_id=images.item_id "
								+ "where category in (:categories)",
						params,
						extractor);

	}

	@Override
	public PagedResult<StoreItem> findByTerm(Filter filter) {
		
		final StringBuilder query = new StringBuilder(
				"select * "
				+ "from store_items "
				+ "inner join users on store_items.owner=users.user_id "
				+ "inner join store_categories on store_items.category=store_categories.category_id "
				+ "left outer join images on store_items.item_id=images.item_id "
				+ "where ");

		final MapSqlParameterSource params = new MapSqlParameterSource();
		
		final TermFilter termFilter = filter.getTermFilter();
		
		boolean logicalOperator = false;
		
		if (termFilter.getTerm().isPresent()) {
			
			logicalOperator = true;
			
			query.append(
					"("
							+ "lower(name) LIKE '%' || :term || '%' "
							+ "OR "
							+ "lower(description) LIKE '%' || :term || '%' "
							+ "OR "
							+ "category in ("
							+ "select store_categories.category_id "
							+ "from store_categories "
							+ "where lower(store_categories.category_name) LIKE '%' || :term || '%'"
							+ ")"
					+ ")"
			);
			
			params.addValue("term", termFilter.getTerm().get().replace("%", "\\%"));
			
		}
		
		
		// build sql from filter
		final PriceFilter priceFilter = filter.getPriceFilter();
		final Range<BigDecimal> priceRange = priceFilter.getPriceRange();
		
		if (priceRange.hasLowerBound() || priceRange.hasUpperBound()) {
			if (logicalOperator) {
				query.append(" and ");
			}
			
			if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
				query.append(" price <= :upperBound and :lowerBound <= price");
				params.addValue("lowerBound",
						priceRange.lowerBoundType() == Range.BoundType.CLOSED
						? priceRange.lowerBound().get()
						: priceRange.lowerBound().get().add(new BigDecimal(1)));
				params.addValue("upperBound",
						priceRange.upperBoundType() == Range.BoundType.CLOSED
						? priceRange.upperBound().get()
						: priceRange.upperBound().get().subtract(new BigDecimal(1)));
			} else if (priceRange.hasLowerBound()) {
				query.append(":lowerBound <= price");
				params.addValue("lowerBound",
						priceRange.lowerBoundType() == Range.BoundType.CLOSED
						? priceRange.lowerBound().get()
						: priceRange.lowerBound().get().add(new BigDecimal(1)));
			} else {
				query.append("price <= :upperBound");
				params.addValue("upperBound",
						priceRange.upperBoundType() == Range.BoundType.CLOSED
						? priceRange.upperBound().get()
						: priceRange.upperBound().get().subtract(new BigDecimal(1)));
			}
			
			logicalOperator = true;
		}

		final CategoryFilter categoryFilter = filter.getCategoryFilter();
		final Set<Category> categories = categoryFilter.getCategories();

		if (!categories.isEmpty()) {
			
			if (logicalOperator) {
				query.append(" and ");
			}
			
			query.append(" category in (:categories) ");
			params.addValue("categories", categories.stream().map(x -> x.getId()).collect(Collectors.toList()));

			logicalOperator = true;
			
		}
		
		final OrderFilter orderFilter = filter.getOrderFilter();
		
		switch (orderFilter.getField()) {
			case PRICE:
				query.append(String.format(" order by %s ", "price"));
				break;
			case NAME:
				query.append(String.format(" order by %s ", "name"));
				break;

			default:
				query.append(String.format(" order by %s ", "price"));
				break;
		}
		
		switch (orderFilter.getOrder()) {
			case ASC:
				query.append(String.format(" %s ", ORDER_ASCENDING));
				break;
			case DESC:
				query.append(String.format(" %s ", ORDER_DESCENDING));
				break;
				
			default:
				query.append(String.format(" %s ", ORDER_DESCENDING));
				break;
		}
		

		final PageFilter pageFilter = filter.getPageFilter();

		query.append(" limit :limit offset :offset");

		final PagedResult<StoreItem> pagedResult = new PagedResult<>();

		pagedResult.setCurrentPage(pageFilter.getPageNumber());
		pagedResult.setPageSize(pageFilter.getPageSize());

		params.addValue("limit", pageFilter.getPageSize());
		params.addValue("offset", pageFilter.getPageNumber()*pageFilter.getPageSize());
		
		List<StoreItem> results = jdbcTemplate.query(query.toString(), params, extractor);

		pagedResult.setNumberOfAvailableResults(results.size());
		pagedResult.setResults(results);

		return pagedResult;
	}

	@Override
	public List<StoreItem> getUserItems(User user) {
		
		return 
				jdbcTemplate
				.getJdbcOperations()
				.query(
						"select * from store_items "
						+ "inner join users on store_items.owner=users.user_id "
						+ "inner join store_categories on store_items.category=store_categories.category_id "
						+ "left outer join images on store_items.item_id=images.item_id "
						+ "where owner = ?",
						extractor,
						user.getId());
	}

	@Override
	public boolean updateItem(final StoreItem item) {
		
		final MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("name", item.getName());
		params.addValue("description", item.getDescription());
		params.addValue("price", item.getPrice());
		params.addValue("category", item.getCategory().getId());
		params.addValue("used", item.isUsed());
		params.addValue("owner", item.getOwner().getId());
		params.addValue("id", item.getId());
		
		return 
				jdbcTemplate
				.update(
						"update store_items "
						+ "set name=:name, description=:description, price=:price, category=:category, used=:used, owner=:owner "
						+ "where item_id = :id", params) == 1;
	}

	@Override
	public int getNumberOfItems() {
		
		return jdbcTemplate.getJdbcOperations().queryForObject("select count(*) from store_items", Integer.class);
	}

	@Override
	public List<StoreItem> getFavourites(final User user) {
		return 
				jdbcTemplate
				.getJdbcOperations()
				.query(
						"select * from store_items "
						+ "inner join favourites on store_items.item_id=favourites.store_item_id "
						+ "inner join users on store_items.owner=users.user_id "
						+ "inner join store_categories on store_items.category=store_categories.category_id "
						+ "left outer join images on store_items.item_id=images.item_id "
						+ "where favourites.user_id = ?",
						extractor,
						user.getId());
	}

	@Override
	public boolean itemExists(final long id) {
		return
				jdbcTemplate
				.getJdbcOperations()
				.queryForObject(
						"select count(*) "
						+ "from store_items "
						+ "where item_id=?",
						Integer.class,
						id) > 0;
	}

	@Override
	public boolean itemExists(final StoreItem item) {
		return itemExists(item.getId());
	}

	@Override
	public boolean increaseSellCount(final StoreItem item) {
		
		final MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("sold", item.getSold()+1);
		params.addValue("id", item.getId());
		
		return 
				jdbcTemplate
				.update(
						"update store_items "
						+ "set sold=:sold "
						+ "where item_id = :id", params) == 1;
	}


}
