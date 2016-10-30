package edu.tp.paw.interfaces.dao;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;

public interface ICommentDao {

	public Comment createComment(final CommentBuilder builder);
	
	public Set<Comment> commentsForItem(final StoreItem item);
	
}
