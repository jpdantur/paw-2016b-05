package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.Comment;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

public interface ICommentService {

	public Comment createComment(final User user, final StoreItem item, final String comment);
	
	public List<Comment> commentsForItem(final StoreItem item);
	
}
