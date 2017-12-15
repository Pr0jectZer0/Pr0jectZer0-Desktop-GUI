package application.api;

import java.util.ArrayList;
import java.util.List;

public class Games {
	public static List<application.model.User> getPublishers() { // Publisher ??
		List<application.model.User> publisherList = new ArrayList<application.model.User>(); // Publisher ??
		return publisherList;
	}
	
	public static List<application.model.User> getGenres() { // Genres ??
		List<application.model.User> genreList = new ArrayList<application.model.User>(); // Genres ??
		return genreList;
	}
	
	public static List<application.model.Game> getGames() {
		List<application.model.Game> gameList = new ArrayList<application.model.Game>();
		return gameList;
	}
	
	/**
	 * 
	 * @param id_genre
	 * @param id_publisher
	 * @param name
	 * @param description
	 * @return the new id of the added game
	 */
	public int addGame(int id_genre, int id_publisher, String name, String description) {
		return 0;
	}
	
	public boolean deleteGame(int game_id) {
		return true;
	}
}
