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

import edu.tp.paw.interfaces.dao.IUserDao;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@Repository
public class UserJdbcDao implements IUserDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
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
				"select * from users where user_id = ?",
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

}
