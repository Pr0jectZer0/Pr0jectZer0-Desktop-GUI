package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Users {
	
	private static ObservableList<User> noFriends = null; 
	
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
	
	public static ObservableList<User> getNoFriends() throws JSONException, IOException {
		if (noFriends == null) {
			noFriends = FXCollections.observableArrayList();
			User[] users = getUsers().toArray(new User[0]);
			User[] friends = Friends.getFriends().toArray(new User[0]);
			for (int i = 0; i < users.length; i++) {
				boolean found = true;
				for (int j = 0; j < friends.length; j++) {
					if (users[i] == friends[j])
						break;
					else
						found = false;
				}
				if (!found)
					noFriends.add(users[i]);
			}
		}
		return noFriends;
	}
}
