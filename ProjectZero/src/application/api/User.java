package application.api;

import java.io.IOException;

import org.json.JSONException;
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
		if (eMail == null || eMail.isEmpty() || password == null || password.isEmpty()) {
			return LoginState.WrongData;
		}
		try {
			String[][] parameter = { { "email", eMail }, { "password", password } };
			String response = HttpWebRequest.sendPostRequest("user/login", parameter);
			System.out.println(eMail + response);
			if (response.contains("error") || response.contains("Laravel")) {
				return LoginState.WrongData;
			}
			else if (response.contains("token")) {
				loginToken = new JSONObject(response).getString("token");
				return LoginState.Success;
			}
		} catch (IOException e) {
			return LoginState.WrongData;
		} catch (JSONException e) {
			return LoginState.WrongData;
		}
		return LoginState.ServerError;
	}

	public static RegisterState register(String username, String eMail, String password) {
		String[][] parameter = { { "email", eMail }, { "name", username }, { "password", password } };
		try {
			String response = HttpWebRequest.sendPostRequest("user/", parameter);
			if (response.contains("id")) {
				return RegisterState.Success;
			}
			else { // Redirecting ¯\_(ツ)_/¯
				return RegisterState.EmailAlreadyUsed;
			}
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