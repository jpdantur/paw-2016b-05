package edu.tp.paw.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.ICommentDao;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;

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
		
		final TypedQuery<Comment> query = entityManager.createQuery("from Comment c where item=:item", Comment.class);
		query.setParameter("item", item);
		return query.getResultList();
		
//		final StoreItem i = entityManager.getReference(StoreItem.class, item.getId());
		
		// hibernate trick
//		i.getComments().iterator();
		
//		return i.getComments();

//		final TypedQuery<Comment> query = entityManager.createQuery("from Comment as c where c.item_id = :id", Comment.class);
//		query.setParameter("id", item.getId());
//		return query.getResultList();
		
	}

}
