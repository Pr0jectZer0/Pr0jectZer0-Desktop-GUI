package application.api;

import java.io.IOException;

import org.json.JSONObject;

import application.model.HttpWebRequest;

public final class User {

	private User() {
	}

	private static String loginToken;

	public static String getLoginToken() {
		return loginToken;
	}

	public static LoginState login(String eMail, String password) {
		try {
			String[][] parameter = { { "email", eMail }, { "password", password } };
			JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("user/login", parameter));
			if (response.toString().contains("token")) {
				loginToken = response.getString("token");
				return LoginState.Success;
			} else if (response.toString().contains("error"))
				return LoginState.WrongData;
		} catch (IOException e) {
			return LoginState.ServerError;
		}
		return LoginState.ServerError;
	}

	public static RegisterState register(String username, String eMail, String password) {
		String[][] parameter = { { "email", eMail }, { "name", username }, { "password", password } };
		try {
			String response = HttpWebRequest.sendPostRequest("user/", parameter);
			if (response.contains("id"))
				return RegisterState.Success;
			else // Redirecting ¯\_(ツ)_/¯
				return RegisterState.EmailAlreadyUsed; // +
														// RegisterState.UsernameNotAvailable;
		} catch (Exception e) {
			return RegisterState.ServerError;
		}
	}

	public enum RegisterState {
		Success, UsernameNotAvailable, EmailAlreadyUsed, ServerError,
	}

	public enum LoginState {
		Success, WrongData, ServerError,
	}

	public static boolean delete(int userID) throws IOException {
		String response = HttpWebRequest.sendDeleteRequest("user/" + Integer.toString(userID));
		return !response.contains("token");
		// TODO: always returns "token not provided"
	}
}