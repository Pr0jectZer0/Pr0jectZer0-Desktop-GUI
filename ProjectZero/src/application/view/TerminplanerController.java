package application.view;

import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import application.api.Termin;
import application.api.Users;
import application.model.ErrorWindow;
import application.model.User;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TerminplanerController {
	@FXML
	private VBox calendarBox;
	@FXML
	private Label date;
	@FXML
	private JFXDatePicker dateStart;
	@FXML
	private JFXDatePicker dateEnd;
	@FXML
	private TextField terminName;
	@FXML
	private TextArea terminBeschreibung;
	@FXML
	private TableView<User> userList;
	@FXML
	private TableColumn<User, String> userNameCol;
	@FXML
	private TableColumn<User, Integer> userIdCol;
	@FXML
	private JFXTextField filterUser;

	@FXML
	private void initialize() {
		DatePicker dp = new DatePicker(LocalDate.now());
		dp.setShowWeekNumbers(false);
		DatePickerSkin calenderSkin = new DatePickerSkin(dp);
		dp.setMinWidth(500);
		DatePickerContent popupContent = (DatePickerContent) calenderSkin.getPopupContent();
		calendarBox.getChildren().add(0, popupContent);

		try {

			userList.setPlaceholder(new Label("Noch keine Freunde hinzugefügt"));
			userIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
			userNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

			FilteredList<User> filteredData = new FilteredList<>(application.api.Friends.getFriends(), p -> true);

			filterUser.textProperty().addListener((observable, oldValue, newValue) -> {
					filteredData.setPredicate(user -> {
						if (newValue == null || newValue.isEmpty()) {
							return true;
						}

						String lowerCaseFilter = newValue.toLowerCase();
						
						if (user.getName().toLowerCase().contains(lowerCaseFilter)) {
							return true;
						} else if (newValue.matches("[0-9]+")) {
							if (user.getId() == Integer.parseInt(newValue)) {
								return true;
							}
						}
						return false;
					});
			});

			SortedList<User> sortedData = new SortedList<>(filteredData);

			sortedData.comparatorProperty().bind(userList.comparatorProperty());

			userList.setItems(sortedData);
			} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Initialisieren der User in Termin-Beschränkungen",
					(Stage) date.getScene().getWindow(), e);
		}

		date.setText("Datum: " + dp.getValue());
		dp.valueProperty().addListener((observable, oldValue, newValue) -> {
			date.setText("Datum: " + dp.getValue());

		});

	}

	@FXML
	private void handleTerminErstellenAction(ActionEvent event) {
		try {
			if (dateStart.getValue() != null && dateEnd.getValue() != null && !terminName.getText().equals("")
					&& !terminBeschreibung.getText().equals("")) {
				Termin.createTermin(terminName.getText(), terminBeschreibung.getText(), dateStart.getValue().toString(),
						dateEnd.getValue().toString());
				Alert alert = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
				dialogPane.getStyleClass().add("myDialog");
				alert.setTitle("Termin erfolgreich hinzugefügt");
				alert.setHeaderText(null);
				alert.setContentText("Der Termin " + terminName.getText() + " wurde erfolgreich hinzugefügt!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				Stage owner = (Stage) date.getScene().getWindow();
				Image image = new Image("application/data/images/logo.png");
				stage.getIcons().add(image);
				alert.initOwner(owner);
				alert.showAndWait();
				terminName.clear();
				terminBeschreibung.clear();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
				dialogPane.getStyleClass().add("myDialog");
				alert.setTitle("Ungültiger Termin");
				alert.setHeaderText(null);
				alert.setContentText("Bitte füllen Sie alle Felder aus!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				Stage owner = (Stage) date.getScene().getWindow();
				Image image = new Image("application/data/images/logo.png");
				stage.getIcons().add(image);
				alert.initOwner(owner);
				alert.showAndWait();
			}
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Erstellen des Termins",
					(Stage) date.getScene().getWindow(), e);
		}
	}
}
