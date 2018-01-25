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
	private static ObservableList<application.model.FriendRequest> friendRequests = null;
	
	private Friends() {}

	public static FriendAddState add(int id) {
		try {
			String[][] parameter = { { "id", Integer.toString(id) } };
			HttpWebRequest.sendPostRequest("friend/add?token=" + application.api.User.getLoginToken(), parameter);
			return FriendAddState.Success;
		}
		catch (Exception e) {
			if (e.getMessage().contains("Server returned HTTP response code: 400")) {
				return FriendAddState.AlreadyFriends;
			}
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
				JSONObject curFriend = friendArr.getJSONObject(i).getJSONObject("friend_user");
				if (curFriend.has("name")) {
					friends.add(new application.model.User(curFriend.getString("name"), curFriend.getInt("id")));
				}
			}
		}
		return friends;
	}
	
	public static boolean acceptRequest(int requestID) throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("friend/" + requestID + "/accept?token=" + application.api.User.getLoginToken()));
		return response.getString("message").equals("Freundschaftsanfrage wurde angenommen.");
	}
	
	public static boolean declineRequest(int requestID) throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("friend/" + requestID + "/decline?token=" + application.api.User.getLoginToken()));
		System.out.println(response);
		return response.getString("message").equals("Freundschaftsanfrage wurde abgelehnt.");
	}
	


	public static ObservableList<application.model.FriendRequest> getFriendRequests() throws JSONException, IOException {
		if (friendRequests == null) {
			JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("friend/requests?token=" + application.api.User.getLoginToken()));
			if (response.has("requests") == true) {
				JSONArray requests = response.getJSONArray("requests");
				friendRequests = FXCollections.observableArrayList();
				for (int i = 0; i < requests.length(); i++) {
					JSONObject curRequest = requests.getJSONObject(i);
					friendRequests.add(new application.model.FriendRequest(curRequest.getInt("id"), curRequest.getJSONObject("user").getString("name"), curRequest.getJSONObject("user").getInt("id")));
				}
			}
		}
		return friendRequests;
	}
}