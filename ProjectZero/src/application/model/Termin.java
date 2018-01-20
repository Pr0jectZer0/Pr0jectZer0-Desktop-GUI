package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Termin {
	
	private final StringProperty startDate,
			endDate,
			title,
			description;
	private final IntegerProperty id;
	
	public Termin(String startDate, String endDate, String title, String description, int id) {
		this.startDate = new SimpleStringProperty(startDate);
		this.endDate = new SimpleStringProperty(endDate);
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.id = new SimpleIntegerProperty(id);
	}
	
	public String getStartDate() {
		return startDate.get();
	}
	
	public String getEndDate() {
		return endDate.get();
	}
	
	public String getTitle() {
		return title.get();
	}
	
	public String getDescription() {
		return description.get();
	}
	
	public int getID() {
		return id.get();
	}
}