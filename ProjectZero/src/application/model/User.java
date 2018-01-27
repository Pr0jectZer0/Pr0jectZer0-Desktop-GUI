package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Das ist die Klasse, die die Daten von einem Benutzer beinhaltet
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class User
{
	private final StringProperty name;
	private final IntegerProperty id;

	public User()
	{
		this("", 0);
	}

	public User(String name, int id)
	{
		this.name = new SimpleStringProperty(name);
		this.id = new SimpleIntegerProperty(id);
	}

	public String getName()
	{
		return name.get();
	}

	public void setName(String name)
	{
		this.name.set(name);
	}

	public StringProperty nameProperty()
	{
		return name;
	}

	public int getId()
	{
		return id.get();
	}

	public void setId(int id)
	{
		this.id.set(id);
	}

	public IntegerProperty idProperty()
	{
		return id;
	}
}
