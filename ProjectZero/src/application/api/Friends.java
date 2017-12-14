package application.api;

import java.util.ArrayList;
import java.util.List;

public class Friends {
	public static FriendAddState add(String token, int id) {
		try {
			return FriendAddState.Success;
		}
		catch (Exception e) {
			return FriendAddState.ServerError;
		}
	}
	
	public static FriendDeleteState delete(String token, int id) {
		return FriendDeleteState.Success;
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
	
	public static List<application.model.User> getFriends(String token) {
		return new ArrayList<application.model.User>();
	}
}
