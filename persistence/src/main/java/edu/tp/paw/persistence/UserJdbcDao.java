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

@Repository
public class UserJdbcDao implements IUserDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<User> rowMapper = (ResultSet resultSet, int rowNum) -> {
		return new User(resultSet.getString("username"), resultSet.getInt("userid"));
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
			.usingGeneratedKeyColumns("userid");
		
		/*jdbcTemplate.execute("create table if not exists users ("
				+ "userid serial primary key,"
				+ "username varchar(100)"
				+ ")"
		);*/
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IUserDao#findById(long)
	 */
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
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.IUserDao#create(java.lang.String)
	 */
	@Override
	public User create(final String username, final String password) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("username", username);
		args.put("password", password);
		
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		
		return new User(username, userId.longValue());
		
	}
	
	/* package */ JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
