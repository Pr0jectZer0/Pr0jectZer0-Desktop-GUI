package application.api;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;

public final class User
{

	private User()
	{
	}

	private static String loginToken;
	private static int userID = -1;
	private static String userName = null, userEmail = null;

	public static String getLoginToken()
	{
		return loginToken;
	}

	public static int getUserID() throws JSONException, IOException
	{
		if (userID == -1)
		{
			JSONObject Response = new JSONObject(HttpWebRequest.sendGetRequest("user" + "?token=" + loginToken))
					.getJSONObject("user");
			int id = Response.getInt("id");
			String name = Response.getString("name");
			String email = Response.getString("email");
			userID = id;
			userName = name;
			userEmail = email;
		}
		return userID;
	}

	public static String getUserName() throws JSONException, IOException
	{
		if (userName == null)
		{
			JSONObject Response = new JSONObject(HttpWebRequest.sendGetRequest("user")).getJSONObject("user");
			int id = Response.getInt("id");
			String name = Response.getString("name");
			String email = Response.getString("email");
			userID = id;
			userName = name;
			userEmail = email;
		}
		return userName;
	}

	public static String getUserEmail() throws JSONException, IOException
	{
		if (userEmail == null)
		{
			JSONObject Response = new JSONObject(HttpWebRequest.sendGetRequest("user" + "?token=" + loginToken))
					.getJSONObject("user");
			int id = Response.getInt("id");
			String name = Response.getString("name");
			String email = Response.getString("email");
			userID = id;
			userName = name;
			userEmail = email;
		}
		return userEmail;
	}

	public static LoginState login(String eMail, String password)
	{
		if (eMail == null || eMail.isEmpty() || password == null || password.isEmpty())
		{
			loginToken = null;
			return LoginState.WrongData;
		}
		try
		{
			String[][] parameter = { { "email", eMail }, { "password", password } };
			String response = HttpWebRequest.sendPostRequest("user/login", parameter);
			if (response.contains("error") || response.contains("Laravel"))
			{
				loginToken = null;
				return LoginState.WrongData;
			}
			else if (response.contains("token"))
			{
				loginToken = new JSONObject(response).getString("token");
				return LoginState.Success;
			}
		}
		catch (IOException e)
		{
			loginToken = null;
			return LoginState.WrongData;
		}
		catch (JSONException e)
		{
			loginToken = null;
			return LoginState.WrongData;
		}
		loginToken = null;
		return LoginState.ServerError;
	}

	public static RegisterState register(String username, String eMail, String password)
	{
		if (username == null || username.isEmpty() || eMail == null || eMail.isEmpty() || password == null
				|| password.isEmpty() || password.length() < 6)
			return RegisterState.WrongData;
		String[][] parameter = { { "email", eMail }, { "name", username }, { "password", password } };
		try
		{
			String response = HttpWebRequest.sendPostRequest("user/", parameter);
			if (response.contains(username) && response.contains(eMail))
			{
				return RegisterState.Success;
			}
			else
			{ // Redirecting without Errordescription
				return RegisterState.WrongData;
			}
		}
		catch (Exception e)
		{
			return RegisterState.ServerError;
		}
	}

	public enum RegisterState
	{
		Success, WrongData, ServerError,
	}

	public enum LoginState
	{
		Success, WrongData, ServerError,
	}

	public static boolean delete(int userID)
	{
		if (userID < 0)
			return false;
		try
		{
			String response = HttpWebRequest
					.sendDeleteRequest("user/" + Integer.toString(userID) + "?token=" + loginToken);
			return !response.contains("token");
		}
		catch (IOException e)
		{
			return false;
		}
		// TODO: Backend route is not working properly
	}
}