package application.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.model.HttpPostRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Controller-Klasse für das Login-Fenster
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class LoginController {
	@FXML
	private JFXTextField tFUsernameEmail;
	@FXML
	private JFXPasswordField tFpw;
	private Stage mainStage;
	private Main main;

	/**
	 * Übergibt eine Referenz zu den Controllern der Main-Methode
	 * @param main
	 */
	public void setMain(Main main) {
		this.main = main;
	}
	
	/**
	 * Diese Methode wird beim Start des Fensters automatisch initialisiert
	 */
	@FXML
	private void initialize() {
		tFUsernameEmail.requestFocus();
	}
	
	/**
	 * Methode zum Anmelden/Einloggen
	 */
	@FXML
	private void handleLoginButtonAction(ActionEvent event) {
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
	
	/**
	 * Methode zum Registrieren
	 */
	@FXML
	private void handleRegisterButtonAction(ActionEvent event) {
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
			alert.setHeaderText("Es gab ein Fehler beim Öffnen des Registrier-Fensters!");
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
}
