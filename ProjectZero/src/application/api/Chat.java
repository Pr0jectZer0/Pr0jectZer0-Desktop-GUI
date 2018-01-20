package application.api;

import java.io.IOException;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import application.model.HttpWebRequest;

public class Chat {
	
	private Chat() {}
	
	public static int getChatroomID(int userID) {
		try {
			return new JSONObject(HttpWebRequest.sendGetRequest("chatroom/" + userID + "?token=" + User.getLoginToken())).getInt("chatroom");
		} catch (JSONException | IOException e) {
			return -1;
		}
	}
	public static String[][] getMessages(String chatID)
	{
			String name = "";
			String mess = "";
			//System.out.println(HttpWebRequest
			//		.sendGetRequest("chatroom/" + chatID + "/messages" + "?token=" + User.getLoginToken()));
			// message = (JsonObject) new JsonParser().parse(HttpWebRequest
			//		.sendGetRequest("chatroom/" + chatID + "/messages" + "?token=" + User.getLoginToken()));
			//JsonArray messages = (JsonArray) message.get("message");
			//Iterator<JsonElement> iterator = messages.iterator();
			//while (iterator.hasNext())
			//{
			//	JsonObject messobj = (JsonObject) iterator.next();
			//	JsonObject userobj = (JsonObject) messobj.get("user");
			//	name = userobj.get("name").toString();
			//	mess += name.replace("\"", "") + ": " + messobj.get("message").toString().replace("\"", "") + "\n";

			//}
			//return mess;
		return new String[][] { { "pr0jectzer0.ml/api/chatroom/" + chatID + "?token=" + User.getLoginToken() } };
	}
	
	public static boolean sendMessage(int chatID, String message)
	{
		if (message == null || message.isEmpty())
			return false;
		String [][] parameter = { { "message", message } };
		try {
			String response = HttpWebRequest.sendPostRequest("chatroom/" + chatID + "/messages?token=" + User.getLoginToken(), parameter);
			return response.contains("Nachricht wurde gesendet.");
		} catch (IOException e) {
			return false;
		}
	}
}