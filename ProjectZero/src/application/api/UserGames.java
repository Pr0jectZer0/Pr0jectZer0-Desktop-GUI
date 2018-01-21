package application.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.Game;
import application.model.HttpWebRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserGames {
	
	private static ObservableList<Game> games = null;
	
	private UserGames() {}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static boolean add(int id) throws IOException {
		if (id < 0)
			return false;
		String[][] parameter = {{ "id", Integer.toString(id) }};
		String response = HttpWebRequest.sendPostRequest("user/game/add?token=" + User.getLoginToken(), parameter);
		return response.contains("Spiel wurde User Liste hinzugef\\u00fcgt.");
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public static boolean delete(int id) throws IOException {
		if (id < 0)
			return false;
		String response = HttpWebRequest.sendDeleteRequest("user/game/remove/" + id + "?token=" + User.getLoginToken());
		return response.contains("Spiel wurde aus Liste entfernt.");
	}
	
	public static ObservableList<Game> getGames() throws JSONException, IOException {
		if (games == null) {
			games = FXCollections.observableArrayList();
			JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("user/game/list?token=" + User.getLoginToken()));
			JSONArray gameArr = response.getJSONArray("games");
			for (int i = 0; i < gameArr.length(); i++) {
				JSONObject gameObj = gameArr.getJSONObject(i);
				games.add(new Game(gameObj.getString("name"), gameObj.getString("beschreibung"), gameObj.getInt("id")));
			}
		}
		return games;
	}
}