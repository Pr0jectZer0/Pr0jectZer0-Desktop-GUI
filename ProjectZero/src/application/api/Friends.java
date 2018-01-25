package application.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Friends {

	private static ObservableList<User> friends = null;
	
	private Friends() {}

	public static FriendAddState add(int id) {
		try {
			String[][] parameter = { { "id", Integer.toString(id) } };
			HttpWebRequest.sendPostRequest("friend/add?token=" + application.api.User.getLoginToken(), parameter);
			return FriendAddState.Success;
		}
		catch (Exception e) {
			e.printStackTrace();
			return FriendAddState.ServerError;
		}
	}
	
	public static FriendDeleteState delete(int id) {
		try {
			HttpWebRequest.sendDeleteRequest("friend/remove/" + Integer.toString(id) + "?token=" + application.api.User.getLoginToken());
			return FriendDeleteState.Success;
		} catch (IOException e) {
			return FriendDeleteState.ServerError;
		}
	}
	
	public enum FriendAddState {
		Success,
		UserNotFound,
		AlreadyFriends,
		ServerError,
	}
	
	public enum FriendDeleteState {
		Success,
		UserNotFound,
		NotFriends,
		ServerError,
	}
	
	public static ObservableList<User> getFriends() throws JSONException, IOException {
		if (friends == null) {
			JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("friends?token=" + application.api.User.getLoginToken()));
			JSONArray friendArr = response.getJSONArray("friends");
			int friendAmount = friendArr.length();
			friends = FXCollections.observableArrayList();
			for (int i = 0; i < friendAmount; i++) {
				JSONObject curFriend = friendArr.getJSONObject(i);
				friends.add(new application.model.User(curFriend.getString("name"), curFriend.getInt("id")));
			}
		}
		return friends;
	}
}
