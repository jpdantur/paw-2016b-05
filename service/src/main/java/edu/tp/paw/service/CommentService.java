package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.ICommentDao;
import edu.tp.paw.interfaces.service.ICommentService;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

@Service
public class CommentService implements ICommentService {

	@Autowired private ICommentDao commentDao;
	
	@Override
	public Comment createComment(final User user, final StoreItem item, final String comment) {
		return commentDao.createComment(user, item, comment);
	}

	@Override
	public Comment createComment(final CommentBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Comment> commentsForItem(final StoreItem item) {
		return commentDao.commentsForItem(item);
	}

}
