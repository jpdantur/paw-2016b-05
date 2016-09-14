package edu.tp.paw.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;

@Repository
public class StoreItemJdbcDao implements IStoreItemDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<StoreItem> rowMapper = (ResultSet resultSet, int rowNum) -> {
		return new StoreItemBuilder(
				resultSet.getInt("item_id"),
				resultSet.getString("name"),
				resultSet.getString("description"),
				resultSet.getFloat("price")
			)
			.created(resultSet.getTimestamp("created"))
			.lastUpdated(resultSet.getTimestamp("last_updated"))
			.sold(resultSet.getInt("sold"))
			.build();
	};
	
	@Autowired
	public StoreItemJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("store_items")
			.usingGeneratedKeyColumns("item_id")
			.usingColumns("name", "description", "price");
		
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
	
	@Override
	public List<StoreItem> findNMostSold(long n) {
		
		System.out.println("finding n most sold " + n);
		
		return
				
				jdbcTemplate
				.query(
						"select * from store_items order by sold desc limit ?",
						rowMapper,
						n);
	}
	
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
	
	@Override
	public StoreItem create(final String name, final String description, final float price) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("name", name);
		args.put("description", description);
		args.put("price", price);
		
		final Number storeItemId = jdbcInsert.executeAndReturnKey(args);
		
		return new StoreItemBuilder(storeItemId.longValue(), name, description, price).build();
		
	}
}
