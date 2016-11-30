package edu.tp.paw.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoleBuilderTest {

	private static final String NAME = "Name";
	private static final String SLUG = "SLUG";

	@Test
	public void testBuild() {
		Role role = new RoleBuilder(NAME,SLUG).id(0).build();
		assertEquals(NAME,role.getRoleName());
		assertEquals(SLUG,role.getSlug());
	}
	
	@Test (expected = IllegalStateException.class)
	public void testBuildInvalidSlug() {
		Role role = new RoleBuilder(NAME,"slug slug").id(0).build();
		assertEquals(NAME,role.getRoleName());
		assertEquals(SLUG,role.getSlug());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullName() {
		Role role = new RoleBuilder(null,SLUG).id(0).build();
		assertEquals(NAME,role.getRoleName());
		assertEquals(SLUG,role.getSlug());
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildNullSlug() {
		Role role = new RoleBuilder(NAME,null).id(0).build();
		assertEquals(NAME,role.getRoleName());
		assertEquals(SLUG,role.getSlug());
	}
	
}
