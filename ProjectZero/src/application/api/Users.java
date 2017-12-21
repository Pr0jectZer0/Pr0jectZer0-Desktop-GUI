package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.User;

public class Users {
	
	private Users() {}
	
	public static List<application.model.User> getUsers() throws JSONException, IOException {
		List<application.model.User> userList = new ArrayList<application.model.User>();
		JSONObject userObj = new JSONObject(HttpWebRequest.sendGetRequest("users"));
		JSONArray userArr = userObj.getJSONArray("users");
		for (int i = 0; i < userArr.length(); i++) {
			JSONObject curUser = userArr.getJSONObject(i);
			userList.add(new User(curUser.getString("name"), curUser.getInt("id")));
		}
		return userList;
	}
}
