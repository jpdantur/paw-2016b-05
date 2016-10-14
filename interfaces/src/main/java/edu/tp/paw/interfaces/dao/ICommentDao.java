package edu.tp.paw.interfaces.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

public interface ICommentDao {

	/**
	 * 
	 * @param user
	 * @param item
	 * @param string
	 * @return
	 * @deprecated
	 */
	public Comment createComment(final User user, final StoreItem item, final String string);
	public Comment createComment(final CommentBuilder builder);
	
	public List<Comment> commentsForItem(final StoreItem item);
	
}
