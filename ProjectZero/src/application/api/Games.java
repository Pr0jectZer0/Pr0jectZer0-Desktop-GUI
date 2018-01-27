package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.Game;
import application.model.Genre;
import application.model.HttpWebRequest;
import application.model.Publisher;
/**
 * Das ist die Api-Klasse für die Spiele
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class Games
{

	private Games()
	{
	}
/**
 * Gibt eine Liste mit allen Publishern zurück
 * @return Liste mit allen Publishern
 */
	public static List<application.model.Publisher> getPublishers() throws JSONException, IOException
	{
		List<application.model.Publisher> publisherList = new ArrayList<application.model.Publisher>();
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("publisher?token=" + User.getLoginToken()));
		JSONArray publisherArr = response.getJSONArray("publisher");
		int amountGames = publisherArr.length();
		for (int i = 0; i < amountGames; i++)
		{
			JSONObject gameObj = publisherArr.getJSONObject(i);
			publisherList.add(new Publisher(gameObj.getString("name"), gameObj.getInt("id")));
		}
		return publisherList;
	}
/**
 * Gibt eine Liste mit allen Genres zurück
 * @return Liste mit allen Genres
 */
	public static List<application.model.Genre> getGenres() throws JSONException, IOException
	{
		List<application.model.Genre> genreList = new ArrayList<application.model.Genre>();
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("genre?token=" + User.getLoginToken()));
		JSONArray genreArr = response.getJSONArray("Genre");
		int amountGames = genreArr.length();
		for (int i = 0; i < amountGames; i++)
		{
			JSONObject gameObj = genreArr.getJSONObject(i);
			genreList.add(new Genre(gameObj.getString("name"), gameObj.getInt("id")));
		}
		return genreList;
	}
/**
 * Gibt eine Liste mit allen Spielen zurück
 * @return Liste mit allen Spielen
 */
	public static List<application.model.Game> getGames() throws JSONException, IOException
	{
		List<application.model.Game> gameList = new ArrayList<application.model.Game>();
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("games?token=" + User.getLoginToken()));
		JSONArray gameArr = response.getJSONArray("games");
		int amountGames = gameArr.length();
		for (int i = 0; i < amountGames; i++)
		{
			JSONObject gameObj = gameArr.getJSONObject(i);
			gameList.add(new Game(gameObj.getString("name"), gameObj.getString("beschreibung"), gameObj.getInt("id")));
		}
		return gameList;
	}

	/**
	 * Fügt ein Spiel der Datenbank hinzu
	 * @param genreID ID des Genres
	 * @param publisherID ID des Publishers
	 * @param name Name des Spiels
	 * @param description Beschreibung des Spiels
	 * @return the new id of the added game
	 */
	public static int addGame(int genreID, int publisherID, String name, String description) throws IOException
	{
		if (genreID <= 0 || publisherID <= 0 || name == null || name.isEmpty() || description == null
				|| description.isEmpty())
			return -1;
		String[][] parameters = new String[][] { { "id_genre", Integer.toString(genreID) },
				{ "id_publisher", Integer.toString(publisherID) }, { "name", name }, { "beschreibung", description } };
		String response = HttpWebRequest.sendPostRequest("game?token=" + User.getLoginToken(), parameters);
		JSONObject game = new JSONObject(response);
		return game.getInt("id");
	}

	/**
	 * Löscht eine Spiel aus der Datenbank
	 * @param game_id ID des Spiels
	 * @return Rückmeldung, ob es geklappt hat(true = geklappt)
	 */
	public static boolean deleteGame(int gameID) throws JSONException, IOException
	{
		if (gameID < 0)
			return false;
		String response = HttpWebRequest
				.sendDeleteRequest("game/" + Integer.toString(gameID) + "?token=" + User.getLoginToken());
		if (response.contains("Spiel wurde gel\\u00f6scht."))
			return true;
		return false;
	}
}