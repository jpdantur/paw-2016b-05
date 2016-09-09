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

import edu.tp.paw.interfaces.service.StoreItemDao;
import edu.tp.paw.model.StoreItem;

@Repository
public class StoreItemJdbcDao implements StoreItemDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<StoreItem> rowMapper = (ResultSet resultSet, int rowNum) -> {
		return new StoreItem(
				resultSet.getInt("item_id"),
				resultSet.getString("description"),
				resultSet.getString("name"),
				resultSet.getTimestamp("created"),
				resultSet.getTimestamp("last_updated"));
	};
	
	@Autowired
	public StoreItemJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("store_items")
			.usingGeneratedKeyColumns("item_id");
		
		jdbcTemplate.execute(
			"create table if not exists store_items ("
				+ "item_id serial primary key,"
				+ "name varchar(100),"
				+ "description text,"
				+ "sold integer default 0,"
				+ "created timestamp default current_timestamp,"
				+ "last_updated timestamp default current_timestamp"
			+ ")"
		);
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
		
		
		return
				
				jdbcTemplate
				.query(
						"select * from store_items order by sold limit ?",
						rowMapper,
						n);
	}
	
	@Override
	public StoreItem create(final String name, final String description) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("name", name);
		args.put("description", description);
		
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		
		return new StoreItem(userId.longValue(), description, name);
		
	}
}
