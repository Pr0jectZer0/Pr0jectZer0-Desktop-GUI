package application.view;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.model.ErrorMsg;
import application.model.ErrorWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller-Klasse für das Login-Fenster
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class LoginController {
	@FXML
	private JFXTextField tFUsernameEmail;
	@FXML
	private JFXPasswordField tFpw;
	@FXML
	private VBox vBoxErrorMsg;
	@FXML
	private Stage mainStage;
	private Stage registerStage;
	private Main main;

	/**
	 * �bergibt eine Referenz zu den Controllern der Main-Methode
	 * 
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void handleLoginButtonAction(ActionEvent event) {
		vBoxErrorMsg.getChildren().clear();
		switch (application.api.User.login(tFUsernameEmail.getText(), tFpw.getText())) {
		case Success:
			tFUsernameEmail.getStyleClass().remove("wrong-details");
			tFpw.getStyleClass().remove("wrong-details");
			mainStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainLayout.fxml"));
			mainStage.setTitle("Pr0jectZer0");
			Image image = new Image("application/data/images/logo.png");
			mainStage.getIcons().add(image);
			AnchorPane mainAnchor = null;
			try {
				mainAnchor = (AnchorPane) loader.load();
			} catch (IOException e) {
				ErrorWindow.newErrorWindow("Es gab ein Fehler bei der Anmeldung!", main.getLoginStage(), e);
			}
			Scene scene = new Scene(mainAnchor);
			mainStage.setScene(scene);
			mainStage.show();
			main.getLoginStage().close();
			break;
		case WrongData:
			ErrorMsg.newError("Falscher Benutzername oder Passwort eingegeben!", vBoxErrorMsg, tFUsernameEmail);
			tFpw.getStyleClass().add("wrong-details");
			break;
		case ServerError:
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim HTTP-Request zur Anmeldung", main.getLoginStage(),
					new IOException("connection failed"));
		default:
			ErrorWindow.newErrorWindow("Es gab ein Fehler bei der Anmeldung!", main.getLoginStage(),
					new IOException("connection failed"));
			break;
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
			registerStage.setResizable(false);
			registerStage.show();
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim �ffnen des Registrier-Fensters!", main.getLoginStage(),
					e);
		}
	}

	/**
	 * Getter: registerStage
	 * @return
	 */
	public Stage getRegisterStage() {
		return registerStage;
	}

	/**
	 * Getter: mainStage
	 * @return
	 */
	public Stage getMainStage() {
		return mainStage;
	}

}
