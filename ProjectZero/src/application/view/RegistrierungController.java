package application.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.model.ErrorMsg;
import application.model.ErrorWindow;
import application.model.HttpPostRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Diese Klasse enthält die Controller und Methoden zur Registrierung
 * 
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class RegistrierungController {
	@FXML
	private JFXTextField tFUsername;
	@FXML
	private JFXTextField tFEmail;
	@FXML
	private JFXPasswordField tFPw;
	@FXML
	private JFXPasswordField tFPw2;
	@FXML
	private VBox vBoxErrorMsg;
	private LoginController loginController;

	/**
	 * Diese Methode gibt eine Referenz zu den LoginController
	 * 
	 * @param loginController
	 */
	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	/**
	 * Diese Methode registriert einen Nutzer
	 */
	@FXML
	private void handleRegisterButtonAction(ActionEvent event) {
		vBoxErrorMsg.getChildren().clear();
		tFUsername.getStyleClass().remove("wrong-details");
		tFEmail.getStyleClass().remove("wrong-details");
		tFPw.getStyleClass().remove("wrong-details");
		tFPw2.getStyleClass().remove("wrong-details");
		boolean wrongRegister = false;
		// TODO Mögliche Abfragen beim Registrieren ergänzen, z.B. mind. Zeichen
		// bei Benutzername oder Passwort
		if (tFUsername.getText().equals("")) {
			ErrorMsg.newError("Bitte geben Sie einen gültigen Benutzernamen an!", vBoxErrorMsg, tFUsername);
			wrongRegister = true;
		}
		if (!tFEmail.getText().matches(".*@.*.[.].*")) {
			ErrorMsg.newError("Bitte geben Sie eine gültige Email-Adresse an!", vBoxErrorMsg, tFEmail);
			wrongRegister = true;
		}
		if (tFPw.getText().equals("") || tFPw2.getText().equals("")) {
			if (tFPw.getText().equals("")) {
				ErrorMsg.newError("Bitte geben Sie Ihr Passwort an!", vBoxErrorMsg, tFPw);
			}
			if (tFPw2.getText().equals("")) {
				ErrorMsg.newError("Bitte geben Sie Ihr wiederholt Passwort an!", vBoxErrorMsg, tFPw2);
			}
			wrongRegister = true;
		}
		if (!tFPw.getText().equals(tFPw2.getText())) {
			ErrorMsg.newError("Die Passwörter sind nicht gleich!", vBoxErrorMsg, tFPw);
			tFPw2.getStyleClass().add("wrong-details");
			wrongRegister = true;
		}
		if (wrongRegister == false) {
			String[][] parameter = { { "email", tFEmail.getText() }, { "name", tFUsername.getText() },
					{ "password", tFPw.getText() } };
			try {
				HttpPostRequest.send("user/", parameter);
				loginController.getRegisterStage().close();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(loginController.getRegisterStage());
				alert.setTitle("Erfolgreich registriert!");
				alert.setHeaderText("Sie haben sich erfolgreich registriert!");
				alert.setContentText("Sie können sich nun einloggen.");
				alert.showAndWait();
			} catch (Exception e) {
				ErrorWindow.newErrorWindow("Es gab ein Fehler beim Registrieren!", loginController.getRegisterStage(), e);
			}
		}
	}
}
