package edu.tp.paw.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserBuilderTest {

	
	
	private static final String PASSWORD = "password";
	private static final String LAST_NAME = "lastName";
	private static final String FIRST_NAME = "firstName";
	private static final String USERNAME = "username";

	@Test
	public void testBuild() {
		
		final User user = new UserBuilder(USERNAME)
			.firstName(FIRST_NAME)
			.lastName(LAST_NAME)
			.password(PASSWORD)
			.build();
		
		assertEquals(USERNAME, user.getUsername());
		assertEquals(FIRST_NAME, user.getFirstName());
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void testBuildInvalidName() {
		
		final User user = new UserBuilder(USERNAME)
			.firstName("a")
			.lastName(LAST_NAME)
			.password(PASSWORD)
			.build();
		
		assertEquals(USERNAME, user.getUsername());
		assertEquals(FIRST_NAME, user.getFirstName());
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testBuildMissingLastName() {
		
		final User user = new UserBuilder(USERNAME)
			.firstName(FIRST_NAME)
			.password(PASSWORD)
			.build();
		
		assertEquals(USERNAME, user.getUsername());
		assertEquals(FIRST_NAME, user.getFirstName());
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testWithNullFirstName() {
		final User user = new UserBuilder(USERNAME).firstName(null).build();
		
		assertEquals(USERNAME, user.getUsername());
		assertEquals(null, user.getFirstName());
	}

}
