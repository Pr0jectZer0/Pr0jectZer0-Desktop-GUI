package application.view;

import com.jfoenix.controls.JFXButton;

import application.api.Note;
import application.api.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class NotizController {
	@FXML
	TextArea selectedNoteText;
	@FXML
	TextArea selectedNoteTitle;
	
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
}
