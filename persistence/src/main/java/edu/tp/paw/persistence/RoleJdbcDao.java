package edu.tp.paw.persistence;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IRoleDao;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@Repository
public class RoleJdbcDao implements IRoleDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<Role> rowMapper = (ResultSet resultSet, int rowNum) -> {
		
		return new RoleBuilder(
					resultSet.getString("role_name"),
					resultSet.getString("role_slug")
					)
				.makeDefault(resultSet.getBoolean("default_role"))
				.id(resultSet.getLong("role_id"))
				.build();
	};
	
	@Autowired
	public RoleJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("roles")
			.usingGeneratedKeyColumns("role_id");
	}
	
	@Override
	public Role createRole(final RoleBuilder builder) {
		final Map<String, Object> args = new HashMap<>();
			
		args.put("role_name", builder.getRoleName());
		args.put("role_slug", builder.getSlug());
		args.put("default_role", builder.isDefault());
			
		final Number roleId = jdbcInsert.executeAndReturnKey(args);
			
		return
				builder
				.id(roleId.longValue())
				.build();
	}

	@Override
	public Role findRoleById(long id) {
		
		List<Role> roleList =
				jdbcTemplate
				.query(
						"select * from roles "
						+ "where role_id=?",
						rowMapper,
						id);
		
		return roleList.isEmpty() ? null : roleList.get(0);
	}

	@Override
	public Role findRoleBySlug(String slug) {
		List<Role> roleList =
				jdbcTemplate
				.query(
						"select * from roles "
						+ "where role_slug=?",
						rowMapper,
						slug);
		
		return roleList.isEmpty() ? null : roleList.get(0);
	}

	@Override
	public List<Role> getAll() {
		return 
			jdbcTemplate
			.query(
					"select * from roles ",
					rowMapper);
	}

	@Override
	public List<Role> getForUser(final User user) {
		
		return
				jdbcTemplate
				.query(
						"select * from roles "
						+ "where roles.role_id in ( "
							+ "select user_roles.role_id from user_roles "
							+ "where user_roles.user_id=? "
						+ ")",
						rowMapper,
						user.getId());
	}

	@Override
	public boolean roleExists(final long id) {
		return
				jdbcTemplate
				.queryForObject(
						"select count(*) "
						+ "from roles "
						+ "where role_id=?",
						Integer.class,
						id) > 0;
	}

	@Override
	public boolean roleExists(final Role role) {
		return roleExists(role.getId());
	}

	@Override
	public Role getDefaultRole() {
		
		List<Role> roleList =
				jdbcTemplate
				.query(
						"select * from roles "
						+ "where default_role=true",
						rowMapper);
		
		return roleList.isEmpty() ? null : roleList.get(0);
		
	}

}
