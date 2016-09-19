package edu.tp.paw.persistence;

import javax.sql.DataSource;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import edu.tp.paw.model.User;
import edu.tp.paw.persistence.UserJdbcDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
	
	
	private UserJdbcDao userDao;
	private static final String	USERNAME = "PAW USER";
	
	@Before
	public void setUp() throws Exception {
		userDao = new UserJdbcDao(null);
	}

	@Test
	public void testFindByIdExistingUser() {
		// precondiciones: db con un registro para el usuario 42 
		
		// ejercitacion del codigo:
		final User user = userDao.findById(42);
		
		// post condiciones:
		Assert.assertNotNull("User is null", user);
		Assert.assertEquals("Searched for user 42, got another one", 42, user.getId());
		
	}
	
	@Test
	public void testFindByIdNonExistingUser() {
		// precondiciones: db sin registro para el usuario 13 
		
		// ejercitacion del codigo:
		final User user = userDao.findById(13);
		
		// post condiciones:
		Assert.assertNull("User is null", user);
		
	}
	
	@Test
	public void testCreate() {
		// precondiciones: db con N registros 
		
		// ejercitacion
		final User user = userDao.create(USERNAME);
		
		// post condiciones: db con N+1 registros
		Assert.assertNotNull("Create returned a null register", user);
		Assert.assertEquals("The created user has an incorrect name", USERNAME, user.getUsername());
	}

}
