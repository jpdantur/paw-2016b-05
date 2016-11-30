package edu.tp.paw.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class FavouriteBuilderTest {

	private StoreItem item = new StoreItem();
	private User user = new User();

	@Test
	public void testBuild() {
		final Favourite favourite = new FavouriteBuilder(item, user).build();
		assertEquals(item,favourite.getItem());
		assertEquals(user,favourite.getUser());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullItem() {
		final Favourite favourite = new FavouriteBuilder(null, user).build();
		assertEquals(item,favourite.getItem());
		assertEquals(user,favourite.getUser());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullUser() {
		final Favourite favourite = new FavouriteBuilder(item, null).build();
		assertEquals(item,favourite.getItem());
		assertEquals(user,favourite.getUser());
	}
}
