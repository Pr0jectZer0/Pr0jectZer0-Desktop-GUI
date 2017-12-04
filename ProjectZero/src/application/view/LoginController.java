package application.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.model.ErrorMsg;
import application.model.ErrorWindow;
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
import javafx.scene.layout.VBox;
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
	@FXML
	private VBox vBoxErrorMsg;
	private Stage mainStage;
	private Stage registerStage;
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
			vBoxErrorMsg.getChildren().clear();
			String[][] parameter = {{ "email", tFUsernameEmail.getText() }, {"password", tFpw.getText()}};
			String response = HttpPostRequest.send("user/login", parameter);
			if (response.contains("token")) {
				try {
					tFUsernameEmail.getStyleClass().remove("wrong-details");
					tFpw.getStyleClass().remove("wrong-details");
					mainStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/MainLayout.fxml"));
					AnchorPane mainAnchor = (AnchorPane) loader.load();
					Scene scene = new Scene(mainAnchor);
					mainStage.setScene(scene);
					mainStage.show();
					main.getLoginStage().close();
				} catch (Exception e) {
					ErrorWindow.newErrorWindow("Es gab ein Fehler bei der Anmeldung!", main.getLoginStage(), e);
				}
			} else {
				ErrorMsg.newError("Falscher Benutzername oder Passwort eingegeben!", vBoxErrorMsg, tFUsernameEmail);
				tFpw.getStyleClass().add("wrong-details");
			}
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim HTTP-Request zur Anmeldung", main.getLoginStage(), e);
		}
	}
	
	/**
	 * Methode zum Registrieren
	 */
	@FXML
	private void handleRegisterButtonAction(ActionEvent event) {
		try {
			this.registerStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Registrierung.fxml"));
			AnchorPane registerAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(registerAnchor);
			RegistrierungController controller = loader.getController();
			controller.setLoginController(this);
			registerStage.initOwner(main.getLoginStage());
			Image image = new Image("application/data/images/logo.png");
			registerStage.getIcons().add(image);
			registerStage.setScene(scene);
			registerStage.show();
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Öffnen des Registrier-Fensters!", main.getLoginStage(), e);
		}
	}
	
	public Stage getRegisterStage() {
		return registerStage;
	}
}
