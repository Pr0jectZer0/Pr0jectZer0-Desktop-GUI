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
	
	//-1 = neue Notiz, sonst ID der Notiz
	private int currentNoteId = -1;
	
	/**
	 * Initialisierung
	 */
	@FXML
	private void initialize() {
		initNoteList();
	}
	
	private void initNoteList() {
		try {
			noteList.setItems(Notes.getNotesFromUser());
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab einen Fehler beim Hinzufügen aller Notizen!", (Stage) noteList.getScene().getWindow(), e);
		}
		noteIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
		noteNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
	}
	
	@FXML
	private void newNoteAction() {
		selectedNoteTitle.setText("Bitte Titel eingeben");
		selectedNoteText.setText("Bitte Text eingeben");
		currentNoteId = -1;
	}
	@FXML
	private void saveNoteAction() {
		if (currentNoteId == -1) {
			try {
				Note newNote = Notes.createNote(selectedNoteTitle.getText(), selectedNoteText.getText());
				if (newNote != null) {
					noteList.getItems().add(newNote);
				}
			} catch (IOException | JSONException e) {
				ErrorWindow.newErrorWindow("Es gab einen Fehler beim Speichern der neuen Notiz!", (Stage) noteList.getScene().getWindow(), e);
			}
		
		} else {
			try {
				Note updatedNote = Notes.changeNote(selectedNoteTitle.getText(), selectedNoteText.getText(), currentNoteId);
				if (updatedNote != null) {
					for (int i = 0; i < noteList.getItems().size(); i++) {
						if (noteList.getItems().get(i).getID() == currentNoteId) {
							noteList.getItems().remove(i);
							noteList.getItems().add(updatedNote);
							break;
						}
					}
					selectedNoteText.setText("");
					selectedNoteTitle.setText("");
					currentNoteId = -1;
				}
			} catch(JSONException | IOException e) {
				ErrorWindow.newErrorWindow("Es gab einen Fehler beim Verändern der Notiz!", (Stage) noteList.getScene().getWindow(), e);
			}
		}
		noteList.refresh();
	}
	@FXML
	private void selectNoteAction() {
		Note selectedNote = noteList.getSelectionModel().getSelectedItem();
		if (selectedNote != null)
		{
			currentNoteId = selectedNote.getID();
			selectedNoteText.setText(selectedNote.getText());
			selectedNoteTitle.setText(selectedNote.getTitle());
		}
	}
}