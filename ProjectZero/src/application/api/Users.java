package application.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Das ist die Api-Klasse für die alle in der Datenbank vorhandenen Nutzer
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class Users
{

	private static ObservableList<User> noFriends = null;
	private static ObservableList<User> users = null;

	private Users()
	{
	}
/**
 * Gibt alle Benutzer die in der Datenbank verzeichnet sind zurück
 * @return Liste mit Benutzern
 */
	public static ObservableList<application.model.User> getUsers() throws JSONException, IOException
	{
		if (users == null)
		{
			users = FXCollections.observableArrayList();
			JSONObject userObj = new JSONObject(
					HttpWebRequest.sendGetRequest("users?token=" + application.api.User.getLoginToken()));
			JSONArray userArr = userObj.getJSONArray("users");
			for (int i = 0; i < userArr.length(); i++)
			{
				JSONObject curUser = userArr.getJSONObject(i);
				users.add(new User(curUser.getString("name"), curUser.getInt("id")));
			}
		}
		return users;
	}
/**
 * Gibt eine Liste mit allen Benutzern zurück, die keine Freunde von dem eingeloggten Benutzer sind
 * @return Liste mit Benutzern
 */
	public static ObservableList<User> getNoFriends() throws JSONException, IOException
	{
		if (noFriends == null)
		{
			noFriends = FXCollections.observableArrayList();
			User[] users = getUsers().toArray(new User[0]);
			User[] friends = Friends.getFriends().toArray(new User[0]);
			for (int i = 0; i < users.length; i++)
			{
				boolean found = false;
				for (int j = 0; j < friends.length; j++)
				{
					if (users[i].getId() == friends[j].getId())
					{
						found = true;
						break;
					}
				}
				if (!found)
					noFriends.add(users[i]);
			}
		}
		return noFriends;
	}
/**
 * Gibt einen Benutzer zu der zugehörigen ID zurück
 * @param id ID des Benutzers
 * @return Der Benutzer
 */
	public static User getUserByID(int id)
	{
		try
		{
			for (User user : getUsers())
			{
				if (user.getId() == id)
				{
					return user;
				}
			}
		}
		catch (JSONException | IOException e)
		{
			return null;
		}
		return null;
	}
}
