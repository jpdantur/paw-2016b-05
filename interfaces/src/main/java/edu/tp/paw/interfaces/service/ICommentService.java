package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;

public interface ICommentService {
	
//	public Comment createComment(final User user, final StoreItem item, final String comment);
	
	public Comment createComment(final CommentBuilder builder);
	
	public List<Comment> commentsForItem(final StoreItem item);
	
}
