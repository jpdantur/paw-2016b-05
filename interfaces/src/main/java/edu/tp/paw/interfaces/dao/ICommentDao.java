package edu.tp.paw.interfaces.dao;

import java.util.List;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;

public interface ICommentDao {

	public Comment createComment(final CommentBuilder builder);
	
	public List<Comment> commentsForItem(final StoreItem item);
	
}
