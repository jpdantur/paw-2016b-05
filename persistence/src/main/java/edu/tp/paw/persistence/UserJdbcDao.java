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

import edu.tp.paw.interfaces.service.UserDao;
import edu.tp.paw.model.User;

@Repository
public class UserJdbcDao implements UserDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<User> rowMapper = (ResultSet resultSet, int rowNum) -> {
		return new User(resultSet.getString("username"), resultSet.getInt("userid"));
	};
	
	@Autowired
	public UserJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("users")
			.usingGeneratedKeyColumns("userid");
		
		jdbcTemplate.execute("create table if not exists users ("
				+ "userid serial primary key,"
				+ "username varchar(100)"
				+ ")"
		);
	}
	
	@Override
	public User findById(long id) {
		
		List<User> userList =
		jdbcTemplate
		.query(
				"SELECT * FROM users where userid = ?",
				rowMapper,
				id);
		
		return userList.isEmpty() ? null : userList.get(0);
	}
	
	@Override
	public User create(final String username) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("username", username);
		
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		
		return new User(username, userId.longValue());
		
	}

}
