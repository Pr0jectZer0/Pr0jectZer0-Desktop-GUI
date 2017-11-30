package application.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.model.HttpPostRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	// ########################################################################
	// Initialisierungen
	// ########################################################################
	
	@FXML
	private Label minimizeL;
	@FXML
	private Label closeL;
	@FXML
	private JFXTextField tFUsernameEmail;
	@FXML
	private JFXPasswordField tFpw;
	private Main main;
	private Stage mainStage;

	public void setMain(Main main) {
		this.main = main;
	}
	
	// ########################################################################
	// Login
	// ########################################################################

	@FXML
	private void signIn() {
		try {
			String[][] parameter = {{ "email", tFUsernameEmail.getText() }, {"password", tFpw.getText()}};
			String response = HttpPostRequest.send("user/login", parameter);
			if (response.contains("token")) {
				try {
					mainStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/MainLayout.fxml"));
					AnchorPane mainAnchor = (AnchorPane) loader.load();
					Scene scene = new Scene(mainAnchor);
					mainStage.setScene(scene);
					mainStage.show();
					main.getLoginStage().close();
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler!");
					alert.setHeaderText("Es gab ein Fehler bei der Anmeldung!");
					alert.setContentText("Bitte wenden Sie sich an den Support.");
					alert.initOwner(main.getLoginStage());

					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String exceptionText = sw.toString();

					Label label = new Label("The exception stacktrace was:");

					TextArea textArea = new TextArea(exceptionText);
					textArea.setEditable(false);

					textArea.setMaxWidth(Double.MAX_VALUE);
					textArea.setMaxHeight(Double.MAX_VALUE);
					GridPane.setVgrow(textArea, Priority.ALWAYS);
					GridPane.setHgrow(textArea, Priority.ALWAYS);

					GridPane expContent = new GridPane();
					expContent.setMaxWidth(Double.MAX_VALUE);
					expContent.add(label, 0, 0);
					expContent.add(textArea, 0, 1);
					alert.getDialogPane().setExpandableContent(expContent);
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Fehler!");
				alert.setHeaderText("Es gab ein Fehler bei der Anmeldung!");
				alert.setContentText("Falsche Login-Daten.");
				alert.initOwner(main.getLoginStage());
				alert.showAndWait();
			}
		} catch (Exception e) {
			System.out.println("error");
		}
	}
	
	@FXML
	private void register() {
		try {
			Stage registerStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Registrierung.fxml"));
			AnchorPane registerAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(registerAnchor);
			registerStage.initOwner(main.getLoginStage());
			Image image = new Image("application/data/images/logo.png");
			registerStage.getIcons().add(image);
			registerStage.setScene(scene);
			registerStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setHeaderText("Es gab ein Fehler beim ÷ffnen des Registrier-Fensters!");
			alert.setContentText("Bitte wenden Sie sich an den Support.");
			alert.initOwner(main.getLoginStage());

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionText = sw.toString();

			Label label = new Label("The exception stacktrace was:");

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);
			alert.getDialogPane().setExpandableContent(expContent);
			alert.showAndWait();
		}
	}

	// ########################################################################
	// Schlieﬂen & Minimieren
	// ########################################################################

	@FXML
	private void close() {
		System.exit(0);
	}

	@FXML
	private void minimize() {
		main.getLoginStage().setIconified(true);;
	}
	
	@FXML
	private void enterClose() {
		SepiaTone st = new SepiaTone();
		closeL.setEffect(st);
	}
	
	@FXML
	private void leaveClose() {
		closeL.setEffect(null);
	}
	
	@FXML
	private void enterMinimize() {
		SepiaTone st = new SepiaTone();
		minimizeL.setEffect(st);
	}
	
	@FXML
	private void leaveMinimize() {
		minimizeL.setEffect(null);
	}
}
