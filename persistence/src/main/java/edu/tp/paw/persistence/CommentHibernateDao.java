package edu.tp.paw.persistence;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@Repository
public class CommentHibernateDao implements ICommentDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Comment createComment(final CommentBuilder builder) {
		
		final Comment comment = builder.build();
		
		entityManager.persist(comment);
		
		return comment;
	}

	@Override
	public List<Comment> commentsForItem(final StoreItem item) {

		final TypedQuery<Comment> query = entityManager.createQuery("from Comment as c where c.item_id = :id", Comment.class);
		query.setParameter("id", item.getId());
		return query.getResultList();
		
	}

}
