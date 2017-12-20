package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;

public class Friends {
	
	private Friends() {}
	
	public static FriendAddState add(int id) {
		try {
			String[][] parameter = { { "", "" }, { "id", Integer.toString(id) } };
			HttpWebRequest.sendPostRequest("friend/add?token=" + User.getLoginToken(), parameter);
			return FriendAddState.Success;
		}
		catch (Exception e) {
			return FriendAddState.ServerError;
		}
	}
	
	public static FriendDeleteState delete(int id) {
		try {
			HttpWebRequest.sendDeleteRequest("friend/remove/" + Integer.toString(id) + "?token=" + User.getLoginToken());
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
	
	public static List<application.model.User> getFriends() throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("friends?token=" + User.getLoginToken()));
		JSONArray friendArr = response.getJSONArray("friends");
		List<application.model.User> friends = new ArrayList<application.model.User>();
		int friendAmount = friendArr.length();
		for (int i = 0; i < friendAmount; i++) {
			JSONObject curFriend = friendArr.getJSONObject(i);
			friends.add(new application.model.User(curFriend.getString("name"), curFriend.getInt("id")));
		}
		return friends;
	}
}
