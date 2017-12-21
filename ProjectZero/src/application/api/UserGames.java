package application.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.Game;
import application.model.HttpWebRequest;
import application.view.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserGames {
	
	private static ObservableList<Game> games = null;
	
	private UserGames() {}
	
	/**
	 * not finished
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static boolean add(int id) throws IOException {
		String[][] parameter = { { "", "" }, { "id", Integer.toString(id) } };
		String response = HttpWebRequest.sendPostRequest("user/game/add?token=" + User.getLoginToken(), parameter);
		return response.contains("success");
		//TODO: check for sucess or failure
	}
	
	/**
	 * not finished
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public static boolean delete(int id) throws IOException {
		String[][] parameter = { { "", "" }, { "id", Integer.toString(id) } };
		String response = HttpWebRequest.sendPostRequest("user/game/remove?token=" + User.getLoginToken(), parameter);
		return response.contains("success");
		//TODO: check for sucess or failure
	}
	
	public static ObservableList<Game> getGames(String token) throws JSONException, IOException {
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
		//TODO: not tested
	}
}