package edu.tp.paw.interfaces.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

public interface ICommentDao {

	public Comment createComment(final User user, final StoreItem item, final String string);
	
	public List<Comment> commentsForItem(final StoreItem item);
	
}
