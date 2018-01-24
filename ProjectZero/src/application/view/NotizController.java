package application.view;

import java.io.IOException;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.api.Notes;
import application.api.User;
import application.model.ErrorWindow;
import application.model.Note;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NotizController {
	@FXML
	JFXTextArea selectedNoteText;
	@FXML
	JFXTextArea selectedNoteTitle;
	
	@FXML
	TableColumn<Note, String> noteNameCol;
	@FXML
	TableColumn<Note, Integer> noteIdCol;
	
	@FXML
	TableView<Note> noteList;
	@FXML
	TableView<User> memberList;
	
	@FXML
	TableColumn<User, String> memberNameCol;
	@FXML
	TableColumn<User, Integer> memberIdCol;
	
	@FXML
	JFXButton btnSelectNote;
	@FXML
	JFXButton btnNewNote;
	@FXML
	JFXButton btnSaveNote;
	
	
	//-3 = Notiz offen und gespeichert, -2 = noch keine Notiz ge�ffnet, -1 = neue Notiz offen NICHT gespeichert, rest = ID der Notiz (NICHT gespeichert)
	int currentNoteId = -2;
	String currentNoteTitle;
	String currentNoteText;
	Note currentNote;
	
	/**
	 * Initialisierungen
	 */
	@FXML
	private void initialize() {
		initNoteList();
	}
	
	private void initNoteList() {
		try {
			noteList.setItems(Notes.getNotesFromUser());
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Hinzuf�gen aller Notizen!", (Stage) noteList.getScene().getWindow(), e);
		}
		noteIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
		noteNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
	}
	
	@FXML
	private void newNoteAction() {
		if ((currentNoteId <= -2)||((selectedNoteTitle.getText().equals(currentNoteTitle)) && (selectedNoteText.getText().equals(currentNoteText)))) {
			currentNoteId = -1;
			selectedNoteTitle.setText("Unbenannte Notiz");
			selectedNoteText.setText("");
			currentNoteTitle = "Unbenannte Notiz";
			currentNoteText = "";
		}else {
			System.out.println("Test!");
		}
	}
	@FXML
	private void saveNoteAction() {
		if(currentNoteId < -1) {
			
		}else if(currentNoteId == -1) {
			if(!(selectedNoteTitle.getText().equals(currentNoteTitle)) || !(selectedNoteText.getText().equals(currentNoteText))) {
				try {
					Note tempNote = Notes.createNote(selectedNoteTitle.getText(), selectedNoteText.getText());
					currentNoteId = -3;
					currentNoteTitle = tempNote.getTitle();
					currentNoteText = tempNote.getText();
					noteList.getItems().add(tempNote);
				} catch (Exception e) {
					ErrorWindow.newErrorWindow("Es gab ein Fehler beim speichern der neuen Notiz!", (Stage) noteList.getScene().getWindow(), e);
				}
			}
		}else {
			if(!(selectedNoteTitle.getText().equals(currentNoteTitle)) || !(selectedNoteText.getText().equals(currentNoteText))) {
				try {
					Note tempNote = Notes.changeNote(selectedNoteTitle.getText(), selectedNoteText.getText(), currentNoteId);
					noteList.getItems().add(tempNote);
					currentNote.setTitle(currentNoteTitle = tempNote.getTitle());
					currentNote.setText(currentNoteText = tempNote.getText());
					currentNoteId = -3;
					currentNoteTitle = tempNote.getTitle();
					currentNoteText = tempNote.getText();
				} catch (Exception e) {
					ErrorWindow.newErrorWindow("Es gab ein Fehler beim speichern der Notiz!", (Stage) noteList.getScene().getWindow(), e);
				}
			}
		}
		//initNoteList();
		noteList.refresh();
	}
	@FXML
	private void selectNoteAction() {
		if((currentNoteId <= -2)||((selectedNoteTitle.getText().equals(currentNoteTitle)) && (selectedNoteText.getText().equals(currentNoteText)))) {
			Note tempNote = noteList.getSelectionModel().getSelectedItem();
			currentNote = tempNote;
			currentNoteId = tempNote.getID();
			selectedNoteTitle.setText(tempNote.getTitle());
			selectedNoteText.setText(tempNote.getText());
			currentNoteTitle = tempNote.getTitle();
			currentNoteText = tempNote.getText();
		}else {
			
		}
	}
}
