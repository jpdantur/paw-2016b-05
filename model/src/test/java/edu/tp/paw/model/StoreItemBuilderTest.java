package edu.tp.paw.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class StoreItemBuilderTest {
	
	private static final String NAME = "Name";
	private static final String DESC = "Desc";
	private static final BigDecimal PRICE = new BigDecimal(100);
	private static final User user = new User();
	private static final Category category = new Category();

	@Test
	public void testBuild() {
		final StoreItem item = new StoreItemBuilder(NAME,DESC,PRICE,false).owner(user).category(category).build();
		assertEquals(NAME,item.getName());
		assertEquals(DESC,item.getDescription());
		assertEquals(PRICE,item.getPrice());
		assertEquals(false,item.isUsed());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildNullName() {
		final StoreItem item = new StoreItemBuilder(null,DESC,PRICE,false).owner(user).category(category).build();
		assertEquals(null,item.getName());
		assertEquals(DESC,item.getDescription());
		assertEquals(PRICE,item.getPrice());
		assertEquals(false,item.isUsed());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildNullDesc() {
		final StoreItem item = new StoreItemBuilder(NAME,null,PRICE,false).owner(user).category(category).build();
		assertEquals(NAME,item.getName());
		assertEquals(null,item.getDescription());
		assertEquals(PRICE,item.getPrice());
		assertEquals(false,item.isUsed());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildNullPrice() {
		final StoreItem item = new StoreItemBuilder(NAME,DESC,null,false).owner(user).category(category).build();
		assertEquals(NAME,item.getName());
		assertEquals(DESC,item.getDescription());
		assertEquals(null,item.getPrice());
		assertEquals(false,item.isUsed());
	}
}
