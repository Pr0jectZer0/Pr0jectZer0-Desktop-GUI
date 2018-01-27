package application.view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.api.User;
import application.model.ErrorMsg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

/**
 * Controller-Klasse für die Registrierung
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class RegistrierungController
{
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
	 * Initialisierungen
	 */
	@FXML
	public void initialize()
	{
		tFUsername.textProperty().addListener((observable, oldValue, newValue) -> {
			String string = tFUsername.getText();
			string = string.replace("!", "t");
			string = string.replace("§", "");
			string = string.replace("$", "");
			string = string.replace("%", "");
			string = string.replace("&", "");
			string = string.replace("/", "");
			string = string.replace("(", "");
			string = string.replace(")", "");
			string = string.replace("=", "");
			string = string.replace("?", "");
			string = string.replace("´", "");
			string = string.replace("`", "");
			string = string.replace("{", "");
			string = string.replace("}", "");
			string = string.replace("[", "");
			string = string.replace("]", "");
			string = string.replace("³", "");
			string = string.replace("²", "");
			string = string.replace("°", "");
			string = string.replace("^", "");
			string = string.replace("+", "");
			string = string.replace("*", "");
			string = string.replace("~", "");
			string = string.replace("'", "");
			string = string.replace("#", "");
			string = string.replace(".", "");
			string = string.replace(",", "");
			string = string.replace(";", "");
			string = string.replace(":", "");
			string = string.replace("-", "");
			string = string.replace("_", "");
			string = string.replace(">", "");
			string = string.replace("<", "");
			string = string.replace("|", "");
			tFUsername.setText(string);
		});

		tFPw.textProperty().addListener((observable, oldValue, newValue) -> {
			String string = tFPw.getText();
			string = string.replace("-", "");
			string = string.replace("_", "");
			string = string.replace(">", "");
			string = string.replace("<", "");
			string = string.replace("|", "");
			string = string.replace("{", "");
			string = string.replace("}", "");
			string = string.replace("[", "");
			string = string.replace("]", "");
			string = string.replace("³", "");
			string = string.replace("²", "");
			string = string.replace("/", "");
			string = string.replace("(", "");
			string = string.replace(")", "");
			string = string.replace("§", "");
			tFPw.setText(string);
		});

		tFPw2.textProperty().addListener((observable, oldValue, newValue) -> {
			String string = tFPw2.getText();
			string = string.replace("-", "");
			string = string.replace("_", "");
			string = string.replace(">", "");
			string = string.replace("<", "");
			string = string.replace("|", "");
			string = string.replace("{", "");
			string = string.replace("}", "");
			string = string.replace("[", "");
			string = string.replace("]", "");
			string = string.replace("³", "");
			string = string.replace("²", "");
			string = string.replace("/", "");
			string = string.replace("(", "");
			string = string.replace(")", "");
			string = string.replace("§", "");
			tFPw2.setText(string);
		});
	}

	/**
	 * Diese Methode gibt eine Referenz zu den LoginController
	 * 
	 * @param loginController
	 */
	public void setLoginController(LoginController loginController)
	{
		this.loginController = loginController;
	}

	/**
	 * Diese Methode registriert einen Nutzer
	 */
	@FXML
	private void handleRegisterButtonAction(ActionEvent event)
	{
		vBoxErrorMsg.getChildren().clear();
		tFUsername.getStyleClass().remove("wrong-details");
		tFEmail.getStyleClass().remove("wrong-details");
		tFPw.getStyleClass().remove("wrong-details");
		tFPw2.getStyleClass().remove("wrong-details");
		boolean wrongRegister = false;
		if (tFUsername.getText().equals(""))
		{
			ErrorMsg.newError("Bitte geben Sie einen gültigen Benutzernamen an!", vBoxErrorMsg, tFUsername);
			wrongRegister = true;
		}
		if (!tFEmail.getText().matches(".*@.*.[.].*"))
		{
			ErrorMsg.newError("Bitte geben Sie eine gültige Email-Adresse an!", vBoxErrorMsg, tFEmail);
			wrongRegister = true;
		}
		if (tFPw.getText().equals("") || tFPw2.getText().equals(""))
		{
			if (tFPw.getText().equals(""))
			{
				ErrorMsg.newError("Bitte geben Sie Ihr Passwort an!", vBoxErrorMsg, tFPw);
			}
			if (tFPw2.getText().equals(""))
			{
				ErrorMsg.newError("Bitte geben Sie Ihr wiederholt Passwort an!", vBoxErrorMsg, tFPw2);
			}
			wrongRegister = true;
		}
		if (!tFPw.getText().equals(tFPw2.getText()))
		{
			ErrorMsg.newError("Die Passwörter sind nicht gleich!", vBoxErrorMsg, tFPw);
			tFPw2.getStyleClass().add("wrong-details");
			wrongRegister = true;
		}
		if (wrongRegister == false)
		{
			switch (User.register(tFUsername.getText(), tFEmail.getText(), tFPw.getText()))
			{
			case Success:
				loginController.getRegisterStage().close();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(loginController.getRegisterStage());
				alert.setTitle("Erfolgreich registriert!");
				alert.setHeaderText("Sie haben sich erfolgreich registriert!");
				alert.setContentText("Sie können sich nun einloggen.");
				alert.showAndWait();
				break;
			case ServerError:
				ErrorMsg.newError("Es gab ein Serverfehler beim Registrieren!", vBoxErrorMsg);
				break;
			case WrongData:
				ErrorMsg.newError("Die eingegebenen Daten entsprechen nicht unseren Richtlinien!", vBoxErrorMsg);
				break;
			}
		}
	}
}
