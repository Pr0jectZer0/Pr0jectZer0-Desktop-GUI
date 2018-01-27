package application.api;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
/**
 * Das ist die Api-Klasse für die den Benutzer
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public final class User
{

	private User()
	{
	}

	private static String loginToken;
	private static int userID = -1;
	private static String userName = null, userEmail = null;
 /**
  * Gibt den LoginToken des eingeloggten Benutzers zurück
  * @return der LoginToken
  */
	public static String getLoginToken()
	{
		return loginToken;
	}
/**
 * Gibt die ID des eingeloggten Benutzers zurück
 * @return ID des Benutzers
 */
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
/**
 * Gibt den Benutzernamen des eingeloggten Benutzers zurück
 * @return der Benutzername
 */
	public static String getUserName() throws JSONException, IOException
	{
		if (userName == null)
		{
			JSONObject Response = new JSONObject(HttpWebRequest.sendGetRequest("user"+ "?token=" + loginToken)).getJSONObject("user");
			int id = Response.getInt("id");
			String name = Response.getString("name");
			String email = Response.getString("email");
			userID = id;
			userName = name;
			userEmail = email;
		}
		return userName;
	}
/**
 * Gibt die E-mail-Adresse von dem eingeloggten Benutzers zurück
 * @return die E-mail des Benutzers
 */
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
/**
 * Methode zum einloggen
 * @param eMail E-Mail-Adresse des Benutzer
 * @param password Passwort des Benutzers
 * @return Rückmeldung, ob es geklappt hat
 */
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
/**
 * Methode zum Registrieren
 * @param username Benutzername des Benutzers
 * @param eMail E-mail des Benutzers
 * @param password Passwort des Benutzers
 * @return Rückmeldung, ob es geklappt hat
 */
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
/**
 * Methode zum löschen eines Benutzers
 * @param userID ID des Benutzers
 * @return Rückmeldung, ob es geklappt hat(true = geklappt)
 */
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