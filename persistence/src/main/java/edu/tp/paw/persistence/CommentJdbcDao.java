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

import edu.tp.paw.interfaces.dao.ICommentDao;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@Repository
public class CommentJdbcDao implements ICommentDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<Comment> rowMapper = (ResultSet resultSet, int rowNum) -> {
		
		final User user = new UserBuilder(
					resultSet.getString("username")
				)
				.email(resultSet.getString("email"))
				.firstName(resultSet.getString("first_name"))
				.lastName(resultSet.getString("last_name"))
				.build();
		
		final Comment comment = new CommentBuilder(
					user,
					resultSet.getString("comment_content")
				)
				.timestamp(resultSet.getTimestamp("created"))
				.id(resultSet.getLong("comment_id"))
				.build();
		
		return comment;
	};
	
	@Autowired
	public CommentJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("comments")
			.usingGeneratedKeyColumns("comment_id");
	}
	
	@Override
	public Comment createComment(final CommentBuilder builder) {
		
		final Map<String, Object> args = new HashMap<>();
			
		args.put("item_id", builder.getItem().getId());
		args.put("user_id", builder.getUser().getId());
		args.put("comment_content", builder.getContent());
			
		final Number commentId = jdbcInsert.executeAndReturnKey(args);
			
		return
				builder
				.timestamp(new Timestamp((new Date()).getTime()))
				.id(commentId.longValue())
				.build();
	}

	@Override
	public List<Comment> commentsForItem(final StoreItem item) {

		return
				jdbcTemplate
				.query(
						"select * from comments "
						+ "inner join store_items on comments.item_id=store_items.item_id "
						+ "inner join users on users.user_id=comments.user_id "
						+ "where comments.item_id=?",
						rowMapper,
						item.getId());
		
		
	}

}
