package application.view;

import java.io.IOException;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.api.Friends;
import application.api.Notes;
import application.model.User;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller-Klasse für die Notizenfunktion
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class NotizController
{
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

	// -3 = Notiz offen und gespeichert, -2 = noch keine Notiz ge�ffnet, -1 =
	// neue Notiz offen NICHT gespeichert, rest = ID der Notiz (NICHT
	// gespeichert)
	// private int currentNoteId = -2;
	private String currentNoteTitle;
	private String currentNoteText;
	private Note currentNote;

	private static NotizController notizcontroller;

	// -1 = neue Notiz, sonst ID der Notiz
	private int currentNoteId = -1;

	/**
	 * Initialisierung
	 */
	@FXML
	private void initialize()
	{
		notizcontroller = this;
		initNoteList();
		initMemberList();
	}

	/**
	 * Initialiesierung der Notizen-Liste
	 */
	private void initNoteList()
	{
		try
		{
			noteList.setItems(Notes.getNotesFromUser());
			newNoteAction();
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab einen Fehler beim Hinzuf�gen aller Notizen!",
					(Stage) noteList.getScene().getWindow(), e);
		}
		noteIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
		noteNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

		noteList.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY)
				{
					try
					{
						selectNoteAction();
					}
					catch (Exception e)
					{
						ErrorWindow.newErrorWindow("Es gab ein Fehler beim �ffnen der Notiz!",
								(Stage) noteList.getScene().getWindow(), e);
					}

				}
			}
		});
	}

	private void initMemberList()
	{
		try
		{
			memberList.setItems(Friends.getFriends());
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim laden der Freunde!",
					(Stage) noteList.getScene().getWindow(), e);
		}
		memberIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		memberNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	}

	/**
	 * Methode, die ausgef�hrt wird, wenn btnNewNote genutzt wird.
	 */
	@FXML
	private void newNoteAction()
	{
		selectedNoteTitle.setText("Bitte Titel eingeben");
		selectedNoteText.setText("Bitte Text eingeben");
		currentNoteTitle = "Bitte Titel eingeben";
		currentNoteText = "Bitte Text eingeben";
		currentNoteId = -1;
	}

	/**
	 * Methode, die ausgef�hrt wird, wenn btnSaveNote genutzt wird.
	 */
	@FXML
	private void saveNoteAction()
	{
		if (currentNoteId < -1)
		{

		}
		else if (currentNoteId == -1)
		{
			if (!(selectedNoteTitle.getText().equals(currentNoteTitle))
					|| !(selectedNoteText.getText().equals(currentNoteText)))
			{
				try
				{
					Note tempNote = Notes.createNote(selectedNoteTitle.getText(), selectedNoteText.getText());
					if (tempNote != null)
					{
						currentNoteId = -3;
						currentNoteTitle = tempNote.getTitle();
						currentNoteText = tempNote.getText();
						noteList.getItems().add(tempNote);
					}
				}
				catch (Exception e)
				{
					ErrorWindow.newErrorWindow("Es gab ein Fehler beim speichern der neuen Notiz!",
							(Stage) noteList.getScene().getWindow(), e);
				}
			}

		}
		else
		{
			if (!(selectedNoteTitle.getText().equals(currentNoteTitle))
					|| !(selectedNoteText.getText().equals(currentNoteText)))
			{
				try
				{
					Note tempNote = Notes.changeNote(selectedNoteTitle.getText(), selectedNoteText.getText(),
							currentNoteId);
					if (tempNote != null)
					{
						noteList.getItems().set(noteList.getItems().indexOf(currentNote), tempNote);
						currentNote = tempNote;
						currentNoteId = -3;
						currentNoteTitle = tempNote.getTitle();
						currentNoteText = tempNote.getText();
					}
				}
				catch (Exception e)
				{
					ErrorWindow.newErrorWindow("Es gab ein Fehler beim speichern der Notiz!",
							(Stage) noteList.getScene().getWindow(), e);
				}
			}
		}
		noteList.refresh();

	}

	/**
	 * Diese Methode lädt die ausgewählte Notiz in die initialisierten Paramter
	 */
	@FXML
	private void selectNoteAction()
	{
		if (/* (currentNoteId <= -2)|| */((selectedNoteTitle.getText().equals(currentNoteTitle))
				&& (selectedNoteText.getText().equals(currentNoteText))))
		{
			Note tempNote = noteList.getSelectionModel().getSelectedItem();
			if (tempNote != null)
			{
				currentNote = tempNote;
				currentNoteId = tempNote.getID();
				selectedNoteTitle.setText(tempNote.getTitle());
				selectedNoteText.setText(tempNote.getText());
				currentNoteTitle = tempNote.getTitle();
				currentNoteText = tempNote.getText();
			}
		}
		else
		{
			loadConfirmationPopup();
		}
	}

	/**
	 * Diese Methode verwirft die initialisierten Paramter
	 */
	@FXML
	private void deleteNoteAction()
	{
		if (currentNoteId == -1)
		{
			selectedNoteTitle.setText("Bitte Titel eingeben");
			selectedNoteText.setText("Bitte Text eingeben");
			currentNoteTitle = "Bitte Titel eingeben";
			currentNoteText = "Bitte Text eingeben";
			currentNoteId = -1;
		}
		else if (currentNoteId == -3 || currentNoteId > -1)
		{
			Notes.deleteNote(currentNote.getID());
			noteList.getItems().remove(noteList.getItems().indexOf(currentNote));
			selectedNoteTitle.setText("Bitte Titel eingeben");
			selectedNoteText.setText("Bitte Text eingeben");
			currentNoteTitle = "Bitte Titel eingeben";
			currentNoteText = "Bitte Text eingeben";
			currentNoteId = -1;
		}
		noteList.refresh();
	}

	/**
	 * Diese Methode öffnet ein Sicherheitsabfrage-Stage wenn eine Notiz-Änderung vorgenommen wurde und ohne Speicherung eine andere Notiz ausgewählt wurde
	 */
	private void loadConfirmationPopup()
	{
		try
		{
			confStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ConfirmPopup.fxml"));
			confStage.initStyle(StageStyle.UNDECORATED);
			popAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(popAnchor);
			confStage.setScene(scene);
			confStage.setY(selectedNoteTitle.getLayoutY() + 300);
			confStage.setX(selectedNoteTitle.getLayoutX() + 700);
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim �ffnen des Popup-Fensters!",
					(Stage) noteList.getScene().getWindow(), e);
		}
	}

	/**
	 * Getter: confStage
	 * @return
	 */
	public Stage getConfStage()
	{
		return this.confStage;
	}

	/**
	 * Getter: notizcontroller
	 * @return
	 */
	public static NotizController getNotizController()
	{
		return notizcontroller;
	}

	/**
	 * Diese Methode speichert die Methode
	 */
	public void saveExtern()
	{
		saveNoteAction();
	}

	/**
	 * Diese Methode speichert die Änderungen nicht
	 */
	public void dontSave()
	{
		selectedNoteTitle.setText(currentNoteTitle);
		selectedNoteText.setText(currentNoteText);
	}

	/**
	 * Diese Methode macht eine Notizanfrage an einen Freund
	 */
	@FXML
	private void addMemberAction()
	{
		if (currentNoteId > -1)
		{
			User tempUser = memberList.getSelectionModel().getSelectedItem();
			try
			{
				Notes.addUserToNote(currentNote.getID(), tempUser.getId());
			}
			catch (Exception e)
			{
				ErrorWindow.newErrorWindow("Es gab ein Fehler beim hinzuf�gen des Mitglieds!",
						(Stage) noteList.getScene().getWindow(), e);
			}
		}
	}
}
