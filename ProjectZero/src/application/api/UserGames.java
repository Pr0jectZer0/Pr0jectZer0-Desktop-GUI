package application.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.Game;
import application.model.HttpWebRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Das ist die Api-Klasse für die Spiele des Benutzers
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class UserGames
{

	private static ObservableList<Game> games = null;

	private UserGames()
	{
	}

	/**
	 * Methode zum hinzufügen eines Spiels zur Spielebibliothek
	 * @param id ID des Spiels
	 * @return Rückmeldung, ob es geklappt hat(true = geklappt)
	 */
	public static boolean add(int id) throws IOException
	{
		if (id < 0)
			return false;
		String[][] parameter = { { "id", Integer.toString(id) } };
		JSONObject response = new JSONObject(
				HttpWebRequest.sendPostRequest("user/game/add?token=" + User.getLoginToken(), parameter));
		return response.getString("message").equals(("Spiel wurde User Liste hinzugef�gt."));
	}

	/**
	 * Methode zum löschen eines Spiels aus der Spielebibliothek
	 * @param id ID des Spiels
	 * @return Rückmeldung, ob es geklappt hat(true = geklappt)
	 * @throws IOException
	 */
	public static boolean delete(int id) throws IOException
	{
		if (id < 0)
			return false;
		JSONObject response = new JSONObject(
				HttpWebRequest.sendDeleteRequest("user/game/remove/" + id + "?token=" + User.getLoginToken()));
		return response.getString("message").equals("Spiel wurde aus Liste entfernt.");
	}
/**
 * Gibt alle Spiele aus der Spielebibliothek des eingeloggten Benutzers zurück
 * @return Liste mit Spielen
 */
	public static ObservableList<Game> getGames() throws JSONException, IOException
	{
		if (games == null)
		{
			games = FXCollections.observableArrayList();
			JSONObject response = new JSONObject(
					HttpWebRequest.sendGetRequest("user/game/list?token=" + User.getLoginToken()));
			JSONArray gameArr = response.getJSONArray("games");
			for (int i = 0; i < gameArr.length(); i++)
			{
				JSONObject gameObj = gameArr.getJSONObject(i);
				games.add(new Game(gameObj.getString("name"), gameObj.getString("beschreibung"), gameObj.getInt("id")));
			}
		}
		return games;
	}
}