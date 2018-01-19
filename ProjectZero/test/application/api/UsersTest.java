package application.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class UsersTest {
	
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void getAllUsers() {
		try {
			assertNotNull(Users.getUsers().get(0));
		} catch (JSONException | IOException e) {
			fail();
		}
	}
	
	@Test
	public void getNoFriends() {
		try {
			assertNotNull(Users.getNoFriends().get(0));
		} catch (JSONException | IOException e) {
			fail();
		}
	}
}