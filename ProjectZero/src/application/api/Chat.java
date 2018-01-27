package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.Message;
import application.model.User;
/**
 * Das ist die Api-Klasse für den Chat
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class Chat
{

	private Chat()
	{
	}

	public static int getChatroomID(int userID)
	{
		try
		{
			return new JSONObject(HttpWebRequest
					.sendGetRequest("chatroom/" + userID + "?token=" + application.api.User.getLoginToken()))
							.getInt("chatroom");
		}
		catch (JSONException | IOException e)
		{
			return -1;
		}
	}

	public static List<Message> getMessages(int chatID)
	{
		if (chatID < 0)
			return null;
		try
		{
			String response = HttpWebRequest
					.sendGetRequest("chatroom/" + chatID + "/messages?token=" + application.api.User.getLoginToken());
			JSONArray messages = new JSONObject(response).getJSONArray("message");
			List<Message> messageList = new ArrayList<Message>();
			for (int i = 0; i < messages.length(); i++)
			{
				JSONObject curMessage = messages.getJSONObject(i);
				int id = curMessage.getInt("id");
				String message = curMessage.getString("message");
				String date = curMessage.getString("updated_at");
				JSONObject userJSON = curMessage.getJSONObject("user");
				int userID = userJSON.getInt("id");
				String userName = userJSON.getString("name");
				User user = new User(userName, userID);
				messageList.add(new Message(message, id, user, date));
			}
			return messageList;
		}
		catch (IOException | JSONException e)
		{
			return null;
		}
	}

	public static List<Message> getGroupMessages(int chatID)
	{
		if (chatID < 0)
			return null;
		try
		{
			String response = HttpWebRequest
					.sendGetRequest("groupchat/" + chatID + "/messages?token=" + application.api.User.getLoginToken());
			JSONArray messages = new JSONObject(response).getJSONArray("message");
			List<Message> messageList = new ArrayList<Message>();
			for (int i = 0; i < messages.length(); i++)
			{
				JSONObject curMessage = messages.getJSONObject(i);
				int id = curMessage.getInt("id");
				String message = curMessage.getString("message");
				String date = curMessage.getString("updated_at");
				JSONObject userJSON = curMessage.getJSONObject("user");
				int userID = userJSON.getInt("id");
				String userName = userJSON.getString("name");
				User user = new User(userName, userID);
				messageList.add(new Message(message, id, user, date));
			}
			return messageList;
		}
		catch (IOException | JSONException e)
		{
			return null;
		}
	}

	public static boolean sendMessage(int chatID, String message)
	{
		if (message == null || message.isEmpty())
			return false;
		String[][] parameter = { { "message", message } };
		try
		{
			String response = HttpWebRequest.sendPostRequest(
					"chatroom/" + chatID + "/messages?token=" + application.api.User.getLoginToken(), parameter);
			return response.contains("Nachricht wurde gesendet.");
		}
		catch (IOException e)
		{
			return false;
		}
	}

	public static boolean sendGroupMessage(int chatID, String message)
	{
		if (message == null || message.isEmpty())
			return false;
		String[][] parameter = { { "message", message } };
		try
		{
			String response = HttpWebRequest.sendPostRequest(
					"groupchat/" + chatID + "/messages?token=" + application.api.User.getLoginToken(), parameter);
			return response.contains("Nachricht wurde gesendet.");
		}
		catch (IOException e)
		{
			return false;
		}
	}
}