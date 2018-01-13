package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.Group;
import application.model.HttpWebRequest;
import application.model.Note;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Groups
{
	private static ObservableList<Group> allGroups = null;
	private static ObservableList<Group> userGroups = null;
	private static int userID = -1;
	
	/**
	 * 
	 * @param name of the group
	 * @param description of the group
	 * @return the created group or null on ServerError
	 */
	public static Group createGroup(String name, String description)
	{
		String[][] parameter = {{"name", name}, {"beschreibung", description}};
		try
		{
			JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("group?token=" + application.api.User.getLoginToken(), parameter));
			return getGroupByID(response.getInt("id"));
		}
		catch(IOException e)
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @param id of the group
	 * @return the group object assigned to this id
	 * @throws IOException on ServerError
	 */
	public static Group getGroupByID(int id) throws IOException
	{
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("group/" + id +"?token=" + application.api.User.getLoginToken()));
		JSONObject group = response.getJSONObject("group");
		int id_got = group.getInt("id");
		String name = group.getString("name");
		String description = group.getString("beschreibung");
		JSONArray userArr = group.getJSONArray("users");
		List<User> userList = new ArrayList<User>();
		User admin = null;
		for (int j = 0; j < userArr.length(); j++) {
			JSONObject curUser = userArr.getJSONObject(j);
			int userID = curUser.getInt("id_user");
			String rolle = curUser.getString("rolle");
			//TODO: Create Function getUserByID and get the user name with that ? used role as placeholder
			User user = new User(rolle, userID);
			if (rolle.equals("admin")) {
				admin = user;
			}
			userList.add(user);
		}
		return new Group(name, description, id_got, userList, admin);
	}
	
	/**
	 * 
	 * @return all groups existing
	 * @throws JSONException on JSON not well formed
	 * @throws IOException on ServerError
	 */
	public static ObservableList<Group> getAllGroups() throws JSONException, IOException
	{
		if (allGroups == null) {
			JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("groups?token=" + application.api.User.getLoginToken()));
			JSONArray groupArr = response.getJSONArray("groups");
			int groupAmount = groupArr.length();
			allGroups = FXCollections.observableArrayList();
			for (int i = 0; i < groupAmount; i++) {
				//get group info
				JSONObject curGroup = groupArr.getJSONObject(i);
				if (userID == -1)
					userID = curGroup.getInt("id_user");
				String name = curGroup.getString("name");
				String description = curGroup.getString("beschreibung");
				int id = curGroup.getInt("id");
				User admin = null;
				//get member info
				JSONArray userArr = curGroup.getJSONArray("users");
				List<User> userList = new ArrayList<User>();
				for (int j = 0; j < userArr.length(); j++) {
					JSONObject curUser = userArr.getJSONObject(j);
					int userID = curUser.getInt("id_user");
					String rolle = curUser.getString("rolle");
					//TODO: Create Function getUserByID and get the user name with that ? used role as placeholder
					User user = new User(rolle, userID);
					if (rolle.equals("admin")) {
						admin = user;
					}
					userList.add(user);
				}
				allGroups.add(new Group(name, description, id, userList, admin));	
			}
		}
		return allGroups;
	}
	
	/**
	 * @return all groups where the current user is member or admin
	 */
	public static ObservableList<Group> getUserGroups() {
		if (userGroups == null && userID != -1) {
			userGroups = FXCollections.observableArrayList();
			for (int i = 0; i < allGroups.size(); i++) {
				Group curGroup = allGroups.get(i);
				for (User user : curGroup.getMembers()) {
					if (user.getId() == userID) {
						userGroups.add(curGroup);
					}
				}
			}
		}
		return userGroups;
	}
	
	public boolean addFromGroup(int id) throws JSONException, IOException {
		//String[][] parameter = {{"id", id + ""}, {"", ""}};
		//JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("group/" + id + "/add_user", parameter));
		//TODO: not working yet
		return false;
	}
	
	public boolean deleteFromGroup(int id) {
		//JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("group/" + id + "/remove_user", parameter));
		//TODO: not working yet
		return false;
	}
	
	public boolean acceptGroup(int id) throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("group/" + id + "/accept"));
		//TODO: not working yet
		return response.has("message");
	}
	
	public boolean declineGroup(int id) throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("group/" + id + "/decline"));
		//TODO: not working yet
		return response.has("message");
	}
	
	/**
	 * 
	 * @return current users userID or -1
	 */
	public static int getUserID() {
		return userID;
	}
	
	public boolean deleteGroup(int id) {
		try {
			JSONObject response = new JSONObject(HttpWebRequest.sendDeleteRequest("group/" +id));
			//TODO: not tested
			return true;
		} catch (JSONException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean addGroupNote(int groupID, int noteID) {
		try {
			String response = HttpWebRequest.sendGetRequest("group/" + groupID + "/attach/note/" + noteID);
			return response.contains("id");
		} catch (IOException e) {
			return false;
		}
		//TODO: not tested
	}
	
	public List<Note> getGroupNotes(int groupID) throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("group/" + groupID + "/notes"));
		return null;
		//TODO: finish function
	}
}