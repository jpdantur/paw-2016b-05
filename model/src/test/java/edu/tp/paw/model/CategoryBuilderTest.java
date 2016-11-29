package edu.tp.paw.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CategoryBuilderTest {
	
	

	private static final String NAME = "Categoria";
	private Category parent = new Category();

	@Test
	public void testBuild() {
		final Category category = new CategoryBuilder(NAME, parent).build();
		assertEquals(NAME,category.getName());
		assertEquals(parent, category.getParent());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildMissingParent() {
		final Category category = new CategoryBuilder(NAME,null).build();
		assertEquals(NAME, category.getName());
		assertEquals(null,category.getParent());
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testWithNullName() {
		final Category category = new CategoryBuilder(null,parent).build();
		assertEquals(null, category.getName());
		assertEquals(parent,category.getParent());
	}

}