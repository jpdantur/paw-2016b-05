package edu.tp.paw.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.dao.ICommentDao;
import edu.tp.paw.interfaces.service.ICommentService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Comment;
import edu.tp.paw.model.CommentBuilder;
import edu.tp.paw.model.StoreItem;

@Service
@Transactional
public class CommentService implements ICommentService {

	@Autowired private ICommentDao commentDao;
	@Autowired private IStoreItemService storeItemService;
	@Autowired private IUserService userService;

	@Override
	public Comment createComment(final CommentBuilder builder) {
		
		if (builder == null) {
			throw new IllegalArgumentException("comment cant be null");
		}
		
		if (builder.getContent() == null) {
			throw new IllegalArgumentException("comment content cant be null");
		}
		
		if (builder.getContent().trim().length() == 0) {
			throw new IllegalArgumentException("comment content cant be empty");
		}
		
		if (!storeItemService.itemExists(builder.getItem())) {
			throw new IllegalArgumentException("comment item must exist");
		}
		
		if (!userService.userExists(builder.getUser())) {
			throw new IllegalArgumentException("user must exist");
		}
		
		return commentDao.createComment(builder);
	}
	
	@Override
	public Set<Comment> commentsForItem(final StoreItem item) {
		return commentDao.commentsForItem(item);
	}

}
