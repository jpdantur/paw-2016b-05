package edu.tp.paw.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StoreImageBuilderTest {

	
	private static final String TYPE = "Type";
	private static final byte[] bytes = new byte[100];
	private static final StoreItem item = new StoreItem();

	@Test
	public void testBuild() {
		final StoreImage image = new StoreImageBuilder(TYPE, bytes).item(item).build();
		assertEquals(TYPE,image.getMimeType());
		assertEquals(bytes,image.getContent());
		assertEquals(item,image.getItem());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullType() {
		final StoreImage image = new StoreImageBuilder(null, bytes).item(item).build();
		assertEquals(TYPE,image.getMimeType());
		assertEquals(bytes,image.getContent());
		assertEquals(item,image.getItem());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullItem() {
		final StoreImage image = new StoreImageBuilder(TYPE, bytes).item(null).build();
		assertEquals(TYPE,image.getMimeType());
		assertEquals(bytes,image.getContent());
		assertEquals(item,image.getItem());
	}
}
