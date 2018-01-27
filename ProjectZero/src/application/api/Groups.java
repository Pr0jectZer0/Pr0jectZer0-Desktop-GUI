package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.Group;
import application.model.GroupRequest;
import application.model.HttpWebRequest;
import application.model.Message;
import application.model.Note;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Das ist die Api-Klasse für die Gruppen
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class Groups
{

	private Groups()
	{
	}

	private static ObservableList<Group> allGroups = null;
	private static ObservableList<Group> userGroups = null;
	private static ObservableList<GroupRequest> groupRequests = null;

	/**
	 * 
	 * @param name
	 *            of the group
	 * @param description
	 *            of the group
	 * @return the created group or null on ServerError
	 * @throws IOException
	 * @throws JSONException
	 */
	public static Group createGroup(String name, String description) throws JSONException, IOException
	{
		if (name == null || name.isEmpty() || description == null || description.isEmpty())
			return null;
		String[][] parameter = { { "name", name }, { "beschreibung", description } };
		JSONObject response = new JSONObject(
				HttpWebRequest.sendPostRequest("group?token=" + application.api.User.getLoginToken(), parameter));
		return getGroupByID(response.getInt("id"));
	}

	/**
	 * 
	 * @param id
	 *            of the group
	 * @return the group object assigned to this id
	 * @throws IOException
	 *             on ServerError
	 */
	public static Group getGroupByID(int id) throws IOException
	{
		if (id < 0)
			return null;
		JSONObject response = new JSONObject(
				HttpWebRequest.sendGetRequest("group/" + id + "?token=" + application.api.User.getLoginToken()));
		JSONObject group = response.getJSONObject("group");
		int id_got = group.getInt("id");
		String name = group.getString("name");
		String description = group.getString("beschreibung");
		JSONArray userArr = group.getJSONArray("users");
		List<User> userList = new ArrayList<User>();
		User admin = null;
		for (int j = 0; j < userArr.length(); j++)
		{
			JSONObject curUser = userArr.getJSONObject(j);
			int userID = curUser.getInt("id_user");
			String rolle = curUser.getString("rolle");
			User user = Users.getUserByID(userID);
			if (rolle.equals("admin"))
			{
				admin = user;
			}
			userList.add(user);
		}
		return new Group(name, description, id_got, userList, admin);
	}

	/**
	 * 
	 * @return all groups existing
	 * @throws JSONException
	 *             on JSON not well formed
	 * @throws IOException
	 *             on ServerError
	 */
	public static ObservableList<Group> getAllGroups() throws JSONException, IOException
	{
		if (allGroups == null)
		{
			JSONObject response = new JSONObject(
					HttpWebRequest.sendGetRequest("groups?token=" + application.api.User.getLoginToken()));
			JSONArray groupArr = response.getJSONArray("groups");
			int groupAmount = groupArr.length();
			allGroups = FXCollections.observableArrayList();
			for (int i = 0; i < groupAmount; i++)
			{
				// get group info
				JSONObject curGroup = groupArr.getJSONObject(i);
				String name = curGroup.getString("name");
				String description = curGroup.getString("beschreibung");
				int id = curGroup.getInt("id");
				allGroups.add(new Group(name, description, id));
			}
		}
		return allGroups;
	}

	/**
	 * @return all groups where the current user is member or admin
	 * @throws IOException
	 * @throws JSONException
	 */
	public static ObservableList<Group> getUserGroups() throws JSONException, IOException
	{
		userGroups = null;
		userGroups = FXCollections.observableArrayList();
		JSONObject response = new JSONObject(
				HttpWebRequest.sendGetRequest("user/groups?token=" + application.api.User.getLoginToken()));
		JSONArray groupArr = response.getJSONArray("groups");
		for (int i = 0; i < groupArr.length(); i++)
		{
			JSONObject curGroup = groupArr.getJSONObject(i);
			int id = curGroup.getInt("id");
			String name = curGroup.getString("name");
			String description = curGroup.getString("beschreibung");
			JSONArray userArr = curGroup.getJSONArray("users");
			List<User> userList = new ArrayList<User>();
			User admin = null;
			for (int j = 0; j < userArr.length(); j++)
			{
				JSONObject curUser = userArr.getJSONObject(j);
				int userID = curUser.getInt("id_user");
				String rolle = curUser.getString("rolle");
				JSONObject userObj = curUser.getJSONObject("user");
				String userName = userObj.getString("name");
				User user = new User(userName, userID);
				if (rolle.equals("admin"))
					admin = user;
				userList.add(user);
			}
			userGroups.add(new Group(name, description, id, userList, admin));

		}
		return userGroups;
	}

	public static boolean addUserToGroup(int userID, int groupID) throws JSONException, IOException
	{
		if (userID < 0 || groupID <= 0)
			return false;
		String[][] parameter = { { "id", userID + "" } };
		JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest(
				"group/" + groupID + "/add_user?token=" + application.api.User.getLoginToken(), parameter));
		return response.getString("message").equals("User wurde in Gruppe hinzugef�gt.");
	}

	public static boolean deleteUserFromGroup(int userID, int groupID) throws JSONException, IOException
	{
		if (userID < 0 || groupID <= 0)
			return false;
		String[][] parameter = { { "id", userID + "" } };
		JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest(
				"group/" + groupID + "/remove_user?token=" + application.api.User.getLoginToken(), parameter));
		return response.getString("message").equals("User wurde aus Gruppe entfenrt.");
	}

	public static boolean acceptGroup(int groupID, int userID) throws JSONException, IOException
	{
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest(
				"group/" + groupID + "/accept/userID" + "?token=" + application.api.User.getLoginToken()));
		return response.has("message");
	}

	public static boolean declineGroup(int groupID, int userID) throws JSONException, IOException
	{
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest(
				"group/" + groupID + "/decline/" + userID + "?token=" + application.api.User.getLoginToken()));
		return response.has("message");
	}

	public static boolean acceptGroupRequest(int groupID) throws JSONException, IOException
	{
		JSONObject reponse = new JSONObject(HttpWebRequest
				.sendGetRequest("user/group/" + groupID + "/accept?token=" + application.api.User.getLoginToken()));
		return reponse.has("message");
	}

	public static boolean declineGroupRequest(int groupID) throws JSONException, IOException
	{
		JSONObject response = new JSONObject(HttpWebRequest
				.sendGetRequest("user/group/" + groupID + "/decline?token=" + application.api.User.getLoginToken()));
		return response.has("message");
	}

	public static boolean deleteGroup(int id)
	{
		if (id < 0)
			return false;
		try
		{
			JSONObject response = new JSONObject(
					HttpWebRequest.sendDeleteRequest("group/" + id + "?token=" + application.api.User.getLoginToken()));
			return response.getString("message").equals("Gruppe wurde gel�scht.");
		}
		catch (JSONException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}
	}

	public static boolean addGroupNote(int groupID, int noteID)
	{
		if (groupID < 0 || noteID < 0)
			return false;
		try
		{
			JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest(
					"group/" + groupID + "/attach/note/" + noteID + "?token=" + application.api.User.getLoginToken()));
			return response.getString("message").equals("Notiz wurde Gruppe hinzugef�gt.");
		}
		catch (IOException | JSONException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public static List<Note> getGroupNotes(int groupID) throws JSONException, IOException
	{
		if (groupID <= 0)
		{
			return null;
		}
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("group/" + groupID + "/notes"));
		if (response.has("message")
				&& response.getString("message").equals("Gruppe wurde nicht gefunden oder besitzt keine Notizen."))
		{
			return null;
		}
		// TODO: BACKEND-Route doesnt work, adding notes or getting tehm is not
		// possible
		return null;
	}

	public static List<Message> getGroupMessages(int groupID) throws JSONException, IOException
	{
		if (groupID <= 0)
		{
			return null;
		}
		List<Message> messageList = new ArrayList<Message>();
		JSONArray messages = new JSONObject(HttpWebRequest
				.sendGetRequest("groupchat/" + groupID + "/messages?token=" + application.api.User.getLoginToken()))
						.getJSONArray("message");
		for (int i = 0; i < messages.length(); i++)
		{
			JSONObject curMessage = messages.getJSONObject(i);
			String message = curMessage.getString("message");
			int id = curMessage.getInt("id");
			String date = curMessage.getString("updated_at");
			int userID = curMessage.getInt("user_id");
			JSONObject userObj = curMessage.getJSONObject("user");
			String userName = userObj.getString("name");
			User user = new User(userName, userID);
			messageList.add(new Message(message, id, user, date));
		}
		return messageList;
	}

	public static boolean sendGroupMessage(int groupID, String message) throws JSONException, IOException
	{
		if (groupID <= 0 || message == null || message.isEmpty())
		{
			return false;
		}
		String[][] parameters = { { "message", message } };
		JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest(
				"groupchat/" + groupID + "/messages?token=" + application.api.User.getLoginToken(), parameters));
		return response.getString("message").equals("Nachricht wurde gesendet.");
	}

	public static ObservableList<GroupRequest> getGroupRequests() throws JSONException, IOException
	{
		if (groupRequests == null)
		{
			groupRequests = FXCollections.observableArrayList();
			JSONObject response = new JSONObject(HttpWebRequest
					.sendGetRequest("user/groups/requests?token=" + application.api.User.getLoginToken()));
			if (response.has("message"))
			{
				return groupRequests;
			}
			JSONArray grouprequestsArr = response.getJSONArray("groups");
			for (int i = 0; i < grouprequestsArr.length(); i++)
			{
				JSONObject curGroupRequest = grouprequestsArr.getJSONObject(i);
				int requestID = curGroupRequest.getInt("id");
				JSONObject curGroup = curGroupRequest.getJSONObject("group");
				int groupID = curGroup.getInt("id");
				String groupName = curGroup.getString("name");
				String groupDescription = curGroup.getString("beschreibung");
				Group group = new Group(groupName, groupDescription, groupID);
				GroupRequest request = new GroupRequest(requestID, group);
				groupRequests.add(request);
			}
		}
		return groupRequests;
	}
}