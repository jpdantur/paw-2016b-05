package edu.tp.paw.persistence;

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

@Repository
public class StoreItemJdbcDao implements IStoreItemDao {

	@Autowired
	private CategoryJdbcDao categoryDao;
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final RowMapper<StoreItem> rowMapper = (ResultSet resultSet, int rowNum) -> {
		return new StoreItemBuilder(
				resultSet.getInt("item_id"),
				resultSet.getString("name"),
				resultSet.getString("description"),
				resultSet.getFloat("price"),
				categoryDao.findById(resultSet.getLong("category")),
				resultSet.getString("email")
			)
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
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#findByTerm(java.lang.String)
	 */
	@Override
	public List<StoreItem> findByTerm(String term) {
		
		
		return
				
				jdbcTemplate
				.query(
						"select * from store_items where lower(name) LIKE ? OR lower(description) LIKE ?",
						rowMapper,
						"%"+term.toLowerCase()+"%",
						"%"+term.toLowerCase()+"%");
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IStoreItemDao#create(java.lang.String, java.lang.String, float, edu.tp.paw.model.Category, java.lang.String)
	 */
	@Override
	public StoreItem create(final String name, final String description, final float price, final Category category, final String email) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("name", name);
		args.put("description", description);
		args.put("price", price);
		args.put("category", category.getId());
		args.put("email", email);
		
		final Number storeItemId = jdbcInsert.executeAndReturnKey(args);
		
		return new StoreItemBuilder(storeItemId.longValue(), name, description, price, category, email).build();
		
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
}
