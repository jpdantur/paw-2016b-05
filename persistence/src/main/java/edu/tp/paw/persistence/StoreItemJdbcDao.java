package edu.tp.paw.persistence;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.CategoryFilter;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PageFilter;
import edu.tp.paw.model.filter.PagedResult;
import edu.tp.paw.model.filter.PriceFilter;
import edu.tp.paw.model.filter.Range;
import edu.tp.paw.model.filter.TermFilter;

@Repository
public class StoreItemJdbcDao implements IStoreItemDao {

	private static final String ORDER_DESCENDING = "DESC";	
	private static final String ORDER_ASCENDING = "ASC";

	private static final String TERM_BASED_QUERY_SQL =
			"select item_id, name, description, price, category, email, sold, count(*) OVER() as total_count "
					+ "from store_items "
					+ "where ";

	@Autowired
	private CategoryJdbcDao categoryDao;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	//	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	private final RowMapper<StoreItem> rowMapper = (ResultSet resultSet, int rowNum) -> {
		return new StoreItemBuilder(
				resultSet.getString("name"),
				resultSet.getString("description"),
				resultSet.getBigDecimal("price"),
				categoryDao.findById(resultSet.getLong("category")),
				resultSet.getString("email")
				)
		.id(resultSet.getLong("item_id"))
		.created(resultSet.getTimestamp("created"))
		.lastUpdated(resultSet.getTimestamp("last_updated"))
		.sold(resultSet.getInt("sold"))
		.build();
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
		.usingColumns("name", "description", "price", "category", "email");


		//		jdbcTemplate.execute(
		//			"create table if not exists store_items ("
		//				+ "item_id serial primary key,"
		//				+ "name varchar(100),"
		//				+ "description text,"
		//				+ "sold integer default 0,"
		//				+ "price real,"
		//				+ "created timestamp default current_timestamp,"
		//				+ "last_updated timestamp default current_timestamp"
		//			+ ")"
		//		);
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
						"select * from store_items where item_id = :id",
						rowMapper,
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
						"select * from store_items order by sold desc limit ?",
						rowMapper,
						n);
	}

	//	private static escape

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#findByTerm(java.lang.String)
	 */
	@Override
	public List<StoreItem> findByTerm(String term) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		mapSqlParameterSource.addValue("term", term.toLowerCase().replace("%", "\\%"));

		return

				jdbcTemplate
				.getJdbcOperations()
				.query(
						TERM_BASED_QUERY_SQL,
						rowMapper,
						mapSqlParameterSource
						//						term.toLowerCase().replace("%", "\\%"),
						//						term.toLowerCase().replace("%", "\\%")
						////						term.toLowerCase().replace("%", "\\%")
						);
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#create(java.lang.String, java.lang.String, float, edu.tp.paw.model.Category, java.lang.String)
	 */
	@Override
	public StoreItem create(final String name, final String description, final BigDecimal price, final Category category, final String email) {

		final Map<String, Object> args = new HashMap<>();

		args.put("name", name);
		args.put("description", description);
		args.put("price", price);
		args.put("category", category.getId());
		args.put("email", email);

		final Number storeItemId = jdbcInsert.executeAndReturnKey(args);

		return new StoreItemBuilder(name, description, price, category, email).id(storeItemId.longValue()).build();

	}

	@Override
	public StoreItem create(final StoreItemBuilder builder) {

		final Map<String, Object> args = new HashMap<>();

		args.put("name", builder.getName());
		args.put("description", builder.getDescription());
		args.put("price", builder.getPrice());
		args.put("category", builder.getCategory().getId());
		args.put("email", builder.getEmail());

		final Number storeItemId = jdbcInsert.executeAndReturnKey(args);

		return builder.id(storeItemId.longValue()).build();

		//		return new StoreItemBuilder(storeItemId.longValue(), buiu, description, price, category, email).build();
	}

	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#findInCategories(java.util.List)
	 */
	@Override
	public List<StoreItem> findInCategories(List<Category> categories) {

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < categories.size(); i++) {
			stringBuilder.append("?,");
		}

		stringBuilder.deleteCharAt(stringBuilder.length() - 1);

		return

				jdbcTemplate
				.getJdbcOperations()
				.query(
						"select * from store_items where category in ("+stringBuilder.toString()+")",
						rowMapper,
						categories.stream().map((category) -> {
							return category.getId();
						}).toArray()
						);

	}

	@Override
	public List<StoreItem> findByTerm(String term, Filter filter) {

		// build sql from filter
		final PriceFilter priceFilter = filter.getPriceFilter();
		final Range<BigDecimal> priceRange = priceFilter.getPriceRange();

		StringBuilder query = new StringBuilder(TERM_BASED_QUERY_SQL);


		if (priceRange.hasLowerBound() || priceRange.hasUpperBound()) {
			query.append(" and ");

			if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
				query.append("price <= :upperBound && :lowerBound <= price");
			} else if (priceRange.hasLowerBound()) {
				query.append(":lowerBound <= price");
			} else {
				query.append("price <= :upperBound");
			}
		}
		// TODO make an ordered set of store items
		//query += " order by price " + (priceFilter.getSortOrder() == Filter.SortOrder.ASC ? ORDER_ASCENDING : ORDER_DESCENDING);

		final CategoryFilter categoryFilter = filter.getCategoryFilter();

		final Set<Category> categories = categoryFilter.getCategories();

		if (!categories.isEmpty()) {

			query.append(" or category in (");
			query.append(":categories");
			/*
			query.append(categories.stream().map(x -> "?").collect(Collectors.joining(",")));
			 */

			query.append(") ");

		}

		final PageFilter pageFilter = filter.getPageFilter();

		query.append(" limit :limit offset :offset");

		final PagedResult<StoreItem> pagedResult = new PagedResult<>();

		pagedResult.setCurrentPage(pageFilter.getPageNumber());
		pagedResult.setPageSize(pageFilter.getPageSize());

		final RowMapper<StoreItem> mapper = (ResultSet resultSet, int rowNum) -> {

			pagedResult.setNumberOfTotalResults(resultSet.getInt("total_count"));

			return new StoreItemBuilder(
					resultSet.getString("name"),
					resultSet.getString("description"),
					resultSet.getBigDecimal("price"),
					categoryDao.findById(resultSet.getLong("category")),
					resultSet.getString("email")
					)
			.id(resultSet.getLong("item_id"))
			.sold(resultSet.getInt("sold"))
			.build();
		};

		//		List<StoreItem> results = new ArrayList<>();


		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("term", term.toLowerCase().replace("%", "\\%"), Types.VARCHAR);
		//		params.addValue("term2", term.toLowerCase().replace("%", "\\%"), Types.VARCHAR);
		//		params.addValue("term3", term.toLowerCase().replace("%", "\\%"), Types.VARCHAR);
		params.addValue("limit", pageFilter.getPageSize(), Types.INTEGER);
		params.addValue("offset", pageFilter.getPageNumber()*pageFilter.getPageSize(), Types.INTEGER);


		System.out.println(query);

		if (priceRange.hasLowerBound()) {
			params.addValue("lowerBound",
					priceRange.lowerBoundType() == Range.BoundType.CLOSED
					? priceRange.lowerBound().get()
							: priceRange.lowerBound().get().add(new BigDecimal(1)));
		}

		if (priceRange.hasUpperBound()) {
			params.addValue("upperBound",
					priceRange.upperBoundType() == Range.BoundType.CLOSED
					? priceRange.upperBound().get()
							: priceRange.upperBound().get().subtract(new BigDecimal(1)));
		}

		if (!categories.isEmpty()) {

			params.addValue("categories", categories.stream().map(x -> x.getId()).collect(Collectors.toList()));
		}

		List<StoreItem> results =
				jdbcTemplate
				.query(
						query.toString(),
						params,
						mapper);

		pagedResult.setNumberOfAvailableResults(results.size());

		System.out.println(results.size());
		System.out.println(pagedResult.getNumberOfTotalResults());
		System.out.println(pagedResult.getNumberOfAvailableResults());


		return results;
	}

	@Override
	public PagedResult<StoreItem> findByTerm(Filter filter) {
		
		StringBuilder query = new StringBuilder("select item_id, name, description, price, category, email, sold, count(*) OVER() as total_count from store_items where ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		
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
							+ "where lower(store_categories.name) LIKE '%' || :term || '%'"
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
				query.append(" price <= :upperBound && :lowerBound <= price");
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
		// TODO make an ordered set of store items
		//query += " order by price " + (priceFilter.getSortOrder() == Filter.SortOrder.ASC ? ORDER_ASCENDING : ORDER_DESCENDING);

		final CategoryFilter categoryFilter = filter.getCategoryFilter();
		final Set<Category> categories = categoryFilter.getCategories();

		if (!categories.isEmpty()) {

			System.out.println("logic: " + logicalOperator);
			
			if (logicalOperator) {
				query.append(" and ");
			}
			
			query.append(" category in (:categories) ");
			params.addValue("categories", categories.stream().map(x -> x.getId()).collect(Collectors.toList()));

			logicalOperator = true;
			
		}

		final PageFilter pageFilter = filter.getPageFilter();

		query.append(" limit :limit offset :offset");

		final PagedResult<StoreItem> pagedResult = new PagedResult<>();

		pagedResult.setCurrentPage(pageFilter.getPageNumber());
		pagedResult.setPageSize(pageFilter.getPageSize());

		params.addValue("limit", pageFilter.getPageSize());
		params.addValue("offset", pageFilter.getPageNumber()*pageFilter.getPageSize());


		System.out.println(query);

		final RowMapper<StoreItem> mapper = (ResultSet resultSet, int rowNum) -> {

			pagedResult.setNumberOfTotalResults(resultSet.getInt("total_count"));

			return new StoreItemBuilder(
					resultSet.getString("name"),
					resultSet.getString("description"),
					resultSet.getBigDecimal("price"),
					categoryDao.findById(resultSet.getLong("category")),
					resultSet.getString("email")
					)
			.id(resultSet.getLong("item_id"))
			.sold(resultSet.getInt("sold"))
			.build();
		};
		
		List<StoreItem> results =
				jdbcTemplate
				.query(
						query.toString(),
						params,
						mapper);

		pagedResult.setNumberOfAvailableResults(results.size());

		System.out.println(results.size());
		System.out.println(pagedResult.getNumberOfTotalResults());
		System.out.println(pagedResult.getNumberOfAvailableResults());

		pagedResult.setResults(results);

		return pagedResult;
	}


}
