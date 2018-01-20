package application.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.model.Game;

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
	
	@Test
	public void addGameInvalid() {
		try {
			assertFalse(UserGames.add(-1));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteGameInvalid() {
		try {
			assertFalse(UserGames.delete(-1));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addGame() {
		try {
			int id = Games.addGame(1, 1, "UNIT_TEST_GAME_", "UNIT_TEST_GAME_");
			//assertTrue(UserGames.add(1));
			//UserGames.delete(1);
			Games.deleteGame(id);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteGame() {
		try {
			int id = Games.addGame(1, 1, "UNIT_TEST_GAME", "UNIT_TEST_GAME");
			UserGames.add(1);
			assertTrue(UserGames.delete(1));
			Games.deleteGame(id);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getGamesAdded() {
		try {
			int id = Games.addGame(1, 1, "UNIT_TEST_GAME", "UNIT_TEST_GAME");
			UserGames.add(id);
			boolean found = false;
			for (Game g : UserGames.getGames()) {
				if (g.getId() == id) {
					found = true;
				}
			}
			assertTrue(found);
			UserGames.delete(id);
			Games.deleteGame(id);
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
}