package edu.tp.paw.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommentBuilderTest {

	private static final StoreItem item = new StoreItem();
	private static final User user = new User();
	private static final String COMMENT = "Comment";
	
	@Test
	public void testBuild() {
		final Comment comment = new CommentBuilder(user,COMMENT,2).item(item).build();
		assertEquals(user,comment.getUser());
		assertEquals(COMMENT,comment.getContent());
		assertEquals(item,comment.getItem());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildNullUser() {
		final Comment comment = new CommentBuilder(null,COMMENT,2).item(item).build();
		assertEquals(user,comment.getUser());
		assertEquals(COMMENT,comment.getContent());
		assertEquals(item,comment.getItem());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildNullContent() {
		final Comment comment = new CommentBuilder(user,null,2).item(item).build();
		assertEquals(user,comment.getUser());
		assertEquals(COMMENT,comment.getContent());
		assertEquals(item,comment.getItem());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildNullItem() {
		final Comment comment = new CommentBuilder(user,COMMENT,2).item(null).build();
		assertEquals(user,comment.getUser());
		assertEquals(COMMENT,comment.getContent());
		assertEquals(item,comment.getItem());
	}
	

}
