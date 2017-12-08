package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Game {
	private final StringProperty name;
	private final StringProperty description;
	private final IntegerProperty id;
	
	public Game() {
		this("", "", 0);
	}
	
	public Game(String name, String description, int id) {
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.id = new SimpleIntegerProperty(id);
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}
	
	public String getDescrption() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
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
}
