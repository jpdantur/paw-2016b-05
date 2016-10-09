package edu.tp.paw.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@Repository
public class UserJdbcDao implements IUserDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
//private static final ResultSetExtractor<List<User>> extractor = (ResultSet resultSet) -> {
//		
//		final Map<Long, UserBuilder> items = new HashMap<>();
//		
//		while (resultSet.next()) {
//			
//			
//			
//			final Category category = new CategoryBuilder(
//						resultSet.getString("category_name"),
//						resultSet.getLong("parent")
//						)
//					.id(resultSet.getLong("category_id"))
//					.build();
//			
//			final StoreItemBuilder item = new StoreItemBuilder(
//					resultSet.getString("name"),
//					resultSet.getString("description"),
//					resultSet.getBigDecimal("price"),
//					category,
//					resultSet.getBoolean("used")
//					)
//			.id(resultSet.getLong("item_id"))
//			.created(resultSet.getTimestamp("created"))
//			.lastUpdated(resultSet.getTimestamp("last_updated"))
//			.sold(resultSet.getInt("sold"))
//			.owner(user);
//			
//			final User user = new UserBuilder(resultSet.getString("username"))
//					.email(resultSet.getString("email"))
//					.firstName(resultSet.getString("first_name"))
//					.lastName(resultSet.getString("last_name"))
//					.id(resultSet.getLong("user_id"))
//					.build();
//			
//			
//			if (!items.containsKey(item.getId())) {
//				
//				items.put(item.getId(), item);
//				
//			}
//			
//			if (resultSet.getString("mime_type") != null) {
//				
//				final StoreImage image = new StoreImageBuilder(
//						resultSet.getString("mime_type"),
//						resultSet.getBytes("content"))
//						.id(resultSet.getLong("image_id"))
//						.build();
//				
//				items.get(item.getId()).images(image);
//			}
//			
//		}
//		
//		return items
//				.values()
//				.stream()
//				.map( UserBuilder::build )
//				.collect(Collectors.toList());
//		
//	};
	
	private final static RowMapper<User> rowMapper = (ResultSet resultSet, int rowNum) -> {
		final User user = new UserBuilder(resultSet.getString("username"))
				.firstName(resultSet.getString("first_name"))
				.lastName(resultSet.getString("last_name"))
				.email(resultSet.getString("email"))
				.id(resultSet.getLong("user_id"))
				.password(resultSet.getString("password"))
				.build(); 
		return user;
	};
	
	/**
	 * Creates a new User Dao for #{dataSource}
	 * @param dataSource The Given Data Source
	 */
	@Autowired
	public UserJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("users")
			.usingGeneratedKeyColumns("user_id");
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IUserDao#findById(long)
	 */
	@Override
	public User findById(long id) {
		
		List<User> userList =
		jdbcTemplate
		.query(
				"select * from users "
//				+ "inner join favourites on favourites.user_id=users.user_id "
//				+ "inner join store_items on favourites.item_id=store_items.item_id "
//				+ "inner join store_categories on store_categories.category_id=store_items.category "
				+ "where users.user_id = ?",
				rowMapper,
				id);
		
		return userList.isEmpty() ? null : userList.get(0);
	}
	
	@Override
	public User findByUsername(final String username) {
		
		List<User> userList =
		jdbcTemplate
		.query(
				"select * from users where username = ?",
				rowMapper,
				username);
		
		return userList.isEmpty() ? null : userList.get(0);
	}
	
	@Override
	public User findByEmail(final String email) {
		
		List<User> userList =
		jdbcTemplate
		.query(
				"select * from users where email = ?",
				rowMapper,
				email);
		
		return userList.isEmpty() ? null : userList.get(0);
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IUserDao#create(java.lang.String)
	 */
	@Override
	public User create(final UserBuilder builder) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("username", builder.getUsername());
		args.put("password", builder.getPassword());
		args.put("first_name", builder.getFirstName());
		args.put("last_name", builder.getLastName());
		args.put("email", builder.getEmail());
		
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		
		return builder.id(userId.longValue()).build();
		
	}
	
	/* package */ JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public boolean usernameExists(final String username) {
		return findByUsername(username) == null ?  false : true;
	}
	
	@Override
	public boolean idExists(final long id) {
		return findById(id) == null ?  false : true;
	}
	
	@Override
	public boolean emailExists(final String email) {
		return findByEmail(email) == null ?  false : true;
	}

	@Override
	public boolean changePassword(User user, String password) {
		
		return
			jdbcTemplate
			.update(
				"update users where user_id = ? set password=?",
				user.getId(),
				user.getPassword()) == 1;
	}

	@Override
	public boolean addFavourite(final User user, final StoreItem item) {
		
		return
				jdbcTemplate
				.update(
					"insert into favourites (store_item_id, user_id) values (?, ?)",
					item.getId(),
					user.getId()) == 1;		
	}
	
	@Override
	public boolean removeFavourite(final User user, final StoreItem item) {
		
		return
				jdbcTemplate
				.update(
					"delete from favourites where user_id=? and store_item_id=?",
					user.getId(),
					item.getId()) == 1;		
	}

	@Override
	public int getNumberOfUsers() {
		return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}

}
