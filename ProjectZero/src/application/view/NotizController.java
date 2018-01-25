package application.view;

import java.io.IOException;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.Main;
import application.api.Notes;
import application.api.User;
import application.model.ErrorWindow;
import application.model.Note;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NotizController {
	@FXML
	private JFXTextArea selectedNoteText;
	@FXML
	private JFXTextArea selectedNoteTitle;
	
	@FXML
	private TableColumn<Note, String> noteNameCol;
	@FXML
	private TableColumn<Note, Integer> noteIdCol;
	
	@FXML
	private TableView<Note> noteList;
	@FXML
	private TableView<User> memberList;
	
	@FXML
	private TableColumn<User, String> memberNameCol;
	@FXML
	private TableColumn<User, Integer> memberIdCol;
	
	@FXML
	private JFXButton btnSelectNote;
	@FXML
	private JFXButton btnNewNote;
	@FXML
	private JFXButton btnSaveNote;
	
	@FXML
	private Stage confStage;
	
	private AnchorPane popAnchor;
	
	
	//-3 = Notiz offen und gespeichert, -2 = noch keine Notiz geï¿½ffnet, -1 = neue Notiz offen NICHT gespeichert, rest = ID der Notiz (NICHT gespeichert)
	private int currentNoteId = -2;
	private String currentNoteTitle;
	private String currentNoteText;
	private Note currentNote;
	
	private static NotizController notizcontroller;
	
	/**
	 * Initialisierungen
	 */
	@FXML
	private void initialize() {
		notizcontroller = this;
		initNoteList();
	}
	
	private void initNoteList() {
		try {
			noteList.setItems(Notes.getNotesFromUser());
			newNoteAction();
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Hinzufï¿½gen aller Notizen!", (Stage) noteList.getScene().getWindow(), e);
		}
		noteIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
		noteNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		
		noteList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY) {
					try {
						selectNoteAction();
					} catch (Exception e) {
						ErrorWindow.newErrorWindow("Es gab ein Fehler beim öffnen der Notiz!", (Stage) noteList.getScene().getWindow(), e);
					}

				}
			}
		});
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
					noteList.getItems().set(noteList.getItems().indexOf(currentNote), tempNote);
					/*currentNote.setTitle(currentNoteTitle = tempNote.getTitle());
					currentNote.setText(currentNoteText = tempNote.getText());*/
					currentNote = tempNote;
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
			loadConfirmationPopup();
		}
	}
	@FXML
	private void deleteNoteAction() {
		if(currentNoteId == -1) {
			selectedNoteTitle.setText("Unbenannte Notiz");
			selectedNoteText.setText("");
			currentNoteTitle = "Unbenannte Notiz";
			currentNoteText = "";
			currentNoteId = -1;
		}else if(currentNoteId == -3 || currentNoteId > -1) {
			Notes.deleteNote(currentNote.getID());
			selectedNoteTitle.setText("Unbenannte Notiz");
			selectedNoteText.setText("");
			currentNoteTitle = "Unbenannte Notiz";
			currentNoteText = "";
			currentNoteId = -1;
		}
	}
	private void loadConfirmationPopup() {
		try {
			confStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ConfirmPopup.fxml"));
			confStage.initStyle(StageStyle.UNDECORATED);
			popAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(popAnchor);
			confStage.setScene(scene);
			confStage.setY(selectedNoteTitle.getLayoutY()+300);
			confStage.setX(selectedNoteTitle.getLayoutX()+700);
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim öffnen des Popup-Fensters!", (Stage) noteList.getScene().getWindow(), e);
		}
	}
	public Stage getConfStage() {
		return this.confStage;
	}
	public static NotizController getNotizController() {
		return notizcontroller;
	}
	public void saveExtern() {
		saveNoteAction();
	}
	public void dontSave() {
		selectedNoteTitle.setText(currentNoteTitle);
		selectedNoteText.setText(currentNoteText);
	}
}
