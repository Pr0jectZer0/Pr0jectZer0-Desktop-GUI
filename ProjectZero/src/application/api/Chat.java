package application.api;

public class Chat {
	
	private Chat() {}
	
	public static String[][] getMessages(String user_id, String token)
	{
		//TODO: fully implement
		return new String[][] { { "pr0jectzer0.ml/api/chatroom/" + user_id + "?token=" + User.getLoginToken() }, {""} };
	}
	
	public static boolean sendMessage(String message, String token)
	{
		//TODO: fully implement
		return message.contains("");
	}
}