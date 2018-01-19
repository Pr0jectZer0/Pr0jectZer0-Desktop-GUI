package application.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class UserGamesTest {

	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void getGames() {
		try {
			assertNotNull(UserGames.getGames());
		} catch (JSONException | IOException e) {
			fail();
		}
	}

}
