package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Benachrichtigungen {
	private final IntegerProperty id;
	private final StringProperty userName;
	private final IntegerProperty userId;
	
	public Benachrichtigungen() {
		this(0, "", 0);
	}
	
	public Benachrichtigungen(int id, String userName, int userId) {
		this.id = new SimpleIntegerProperty(id);
		this.userName = new SimpleStringProperty(userName);
		this.userId = new SimpleIntegerProperty(userId);
	}
	
	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public IntegerProperty idProperty() {
		return id;
	}
	
	public String getUserName() {
		return userName.get();
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public StringProperty userNameProperty() {
		return userName;	
	}
	
	public int getUserId() {
		return userId.get();
	}

	public void setUserId(int userId) {
		this.userId.set(userId);
	}

	public IntegerProperty userIdProperty() {
		return userId;
	}
}
