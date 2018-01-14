package application.model;

public class Note {
	
	private String title, text;
	private int userID, id;
	
	public Note(String title, String text, int userID, int id) {
		this.title = title;
		this.text = text;
		this.userID = userID;
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public int getID() {
		return id;
	}
}
