package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.Game;
import application.model.HttpWebRequest;
import application.view.LoginController;

public class UserGames {
	
	private UserGames() {}
	
	/**
	 * not finished
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static boolean add(int id) throws IOException {
		String[][] parameter = { { "", "" }, { "id", Integer.toString(id) } };
		String response = HttpWebRequest.sendPostRequest("user/game/add?token=" + LoginController.getToken(), parameter);
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
		String response = HttpWebRequest.sendPostRequest("user/game/remove?token=" + LoginController.getToken(), parameter);
		return response.contains("success");
		//TODO: check for sucess or failure
	}
	
	public static List<application.model.Game> getGames(String token) throws JSONException, IOException {
		List<application.model.Game> gameList = new ArrayList<application.model.Game>();
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("user/game/list?token=" + User.getLoginToken()));
		JSONArray gameArr = response.getJSONArray("games");
		for (int i = 0; i < gameArr.length(); i++) {
			JSONObject gameObj = gameArr.getJSONObject(i);
			gameList.add(new Game(gameObj.getString("name"), gameObj.getString("beschreibung"), gameObj.getInt("id")));
		}
		return gameList;
		//TODO: not tested
	}

}
