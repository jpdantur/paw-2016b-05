package edu.tp.paw.persistence;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.filter.Filter;
import edu.tp.paw.model.filter.PriceFilter;
import edu.tp.paw.model.filter.Range;

@Repository
public class StoreItemJdbcDao implements IStoreItemDao {

	private static final String ORDER_DESCENDING = "DESC";

	private static final String ORDER_ASCENDING = "ASC";

	private static final String TERM_BASED_QUERY_SQL = "select * "
	+ "from store_items "
	+ "where "
	+ "("
	+ "lower(name) LIKE '%' || ? || '%' "
	+ "OR "
	+ "lower(description) LIKE '%' || ? || '%' "
	+ "OR "
	+ "category in ("
		+ "select store_categories.category_id "
		+ "from store_categories "
		+ "where lower(store_items.name) LIKE '%' || ? || '%'"
		+ ")"
	+ ")";

	@Autowired
	private CategoryJdbcDao categoryDao;
	
	private final JdbcTemplate jdbcTemplate;
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
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
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
		.query(
				"select * from store_items where item_id = ?",
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
		
		
		return
				
				jdbcTemplate
				.query(
						TERM_BASED_QUERY_SQL,
						rowMapper,
						term.toLowerCase().replace("%", "\\%"),
						term.toLowerCase().replace("%", "\\%"),
						term.toLowerCase().replace("%", "\\%")
//						term.toLowerCase().replace("%", "\\%")
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
		args.put("category", builder.getCategory());
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
		
		String query = TERM_BASED_QUERY_SQL;
		
		System.out.println(priceRange.lowerBoundType());
		System.out.println(priceRange.upperBoundType());
		
		System.out.println(priceRange.hasLowerBound());
		System.out.println(priceRange.hasUpperBound());
		
		if (priceRange.hasLowerBound() || priceRange.hasUpperBound()) {
			query += " and ";
			
			if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
				query += "price <= ? && ? <= price";
			} else if (priceRange.hasLowerBound()) {
				query += "? <= price";
			} else {
				query += "price <= ?";
			}
		}
		
		query += " order by price " + (priceFilter.getSortOrder() == Filter.SortOrder.ASC ? ORDER_ASCENDING : ORDER_DESCENDING);
		
		System.out.println(priceRange);
		System.out.println(query);
		
		if (priceRange.hasLowerBound() && priceRange.hasUpperBound()) {
			return
					jdbcTemplate
					.query(
							query,
							rowMapper,
							term.toLowerCase().replace("%", "\\%"),
							term.toLowerCase().replace("%", "\\%"),
							term.toLowerCase().replace("%", "\\%"),
							priceRange.upperBoundType() == Range.BoundType.CLOSED
							? priceRange.upperBound().get()
							: priceRange.upperBound().get().subtract(new BigDecimal(1)),
							priceRange.lowerBoundType() == Range.BoundType.CLOSED
							? priceRange.lowerBound().get()
							: priceRange.lowerBound().get().add(new BigDecimal(1))
					);
		} else if (priceRange.hasLowerBound()) {
			return
					jdbcTemplate
					.query(
							query,
							rowMapper,
							term.toLowerCase().replace("%", "\\%"),
							term.toLowerCase().replace("%", "\\%"),
							term.toLowerCase().replace("%", "\\%"),
							priceRange.lowerBoundType() == Range.BoundType.CLOSED
							? priceRange.lowerBound().get()
							: priceRange.lowerBound().get().add(new BigDecimal(1))
					);
		} else if (priceRange.hasUpperBound()) {
			return
					jdbcTemplate
					.query(
							query,
							rowMapper,
							term.toLowerCase().replace("%", "\\%"),
							term.toLowerCase().replace("%", "\\%"),
							term.toLowerCase().replace("%", "\\%"),
							priceRange.upperBoundType() == Range.BoundType.CLOSED
							? priceRange.upperBound().get()
							: priceRange.upperBound().get().subtract(new BigDecimal(1))
					);
		} else {
	
			return
				jdbcTemplate
				.query(
						query,
						rowMapper,
						term.toLowerCase().replace("%", "\\%"),
						term.toLowerCase().replace("%", "\\%"),
						term.toLowerCase().replace("%", "\\%")
				);
		}
	}

	
}
