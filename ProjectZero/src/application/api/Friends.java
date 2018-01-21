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
			String response = HttpWebRequest.sendPostRequest("friend/add?token=" + application.api.User.getLoginToken(), parameter);
			if (response.contains("User bereits befreundet.")) {
				return FriendAddState.AlreadyFriends;
			} else if (response.contains("User sind jetzt befreundet.")) {
				return FriendAddState.Success;
			} else {
				return FriendAddState.ServerError;
			}
		}
		catch (Exception e) {
			if (e.getMessage().contains("Server returned HTTP response code: 400")) {
				return FriendAddState.AlreadyFriends;
			}
			return FriendAddState.ServerError;
		}
	}
	
	public static FriendDeleteState delete(int id) {
		try {
			String response = HttpWebRequest.sendDeleteRequest("friend/remove/" + Integer.toString(id) + "?token=" + application.api.User.getLoginToken());
			if (response.contains("User nicht befreundet")) {
				return FriendDeleteState.NotFriends;
			}
			return FriendDeleteState.Success;
		} catch (IOException e) {
			if (e.getMessage().contains("Server returned HTTP response code: 400")) {
				return FriendDeleteState.NotFriends;
			} else if (e.getMessage().contains("Server returned HTTP response code: 500")) {
				return FriendDeleteState.Success;
			}
			return FriendDeleteState.ServerError;
		}
	}
	
	public enum FriendAddState {
		Success,
		AlreadyFriends,
		ServerError,
	}
	
	public enum FriendDeleteState {
		Success,
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
