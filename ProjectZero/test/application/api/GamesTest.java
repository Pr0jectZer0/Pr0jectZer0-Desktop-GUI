package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.model.Game;

public class GamesTest {
	
	private static String name = "JUNIT_TEST",
			description = "JUNIT_TEST";
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void getPublishers() {
		try {
			assertTrue((Games.getPublishers().size() > 0));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getGames() {
		try {
			assertTrue(Games.getGames().size() > 0);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getGenres() {
		try {
			assertTrue((Games.getGenres().size() > 0));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addGameZero() {
		try {
			assertEquals(Games.addGame(0, 0, name, description), -1);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addGameNull() {
		try {
			assertEquals(Games.addGame(1, 1, null, null), -1);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addGameValid() {
		try {
			int id = Games.addGame(1, 1, name, description);
			assertTrue(id > 0);
			Games.deleteGame(id);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteGameInvalid() {
		try {
			assertFalse(Games.deleteGame(-1));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteGameValid() {
		try {
			int id = Games.addGame(1, 1, name, description);
			assertTrue(Games.deleteGame(id));
			boolean found = false;
			for (Game game : Games.getGames()) {
				if (game.getId() == id) {
					found = true;
					break;
				}
			}
			assertFalse(found);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addGame() {
		try {
			int id = Games.addGame(1, 1, name, description);
			boolean found = false;
			for (Game game : Games.getGames()) {
				if (game.getId() == id) {
					found = true;
					break;
				}
			}
			assertTrue(found);
			Games.deleteGame(id);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
}