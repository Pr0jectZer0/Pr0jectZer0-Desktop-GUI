package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Das ist die Klasse, die die Daten von einer Freundesanfrage beinhaltet
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class FriendRequest
{

	private final IntegerProperty id;
	private final StringProperty userName;
	private final IntegerProperty userId;

	public FriendRequest(int id, String userName, int userId)
	{
		this.id = new SimpleIntegerProperty(id);
		this.userName = new SimpleStringProperty(userName);
		this.userId = new SimpleIntegerProperty(userId);
	}

	public int getId()
	{
		return id.get();
	}

	public IntegerProperty idProperty()
	{
		return id;
	}

	public String getUserName()
	{
		return userName.get();
	}

	public StringProperty userNameProperty()
	{
		return userName;
	}

	public int getUserId()
	{
		return userId.get();
	}

	public IntegerProperty userIdProperty()
	{
		return userId;
	}
}