package application.model;
/**
 * Das ist die Klasse, die die Daten von einer Notiz beinhaltet
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class Note
{

	private String title, text;
	private int userID, id;

	public Note(String title, String text, int userID, int id)
	{
		this.title = title;
		this.text = text;
		this.userID = userID;
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getText()
	{
		return text;
	}

	public int getUserID()
	{
		return userID;
	}

	public int getID()
	{
		return id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
