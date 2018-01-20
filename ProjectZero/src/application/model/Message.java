package application.model;

public class Message {
	
	private String message,
		date;
	private int id;
	private User sender;
	
	public Message(String message, int id, User sender, String date)
	{
		this.message = message;
		this.id = id;
		this.sender = sender;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}
	
	public int getID() {
		return id;
	}
	
	public User getSender() {
		return sender;
	}
	
	public String getDate() {
		return date;
	}

}
