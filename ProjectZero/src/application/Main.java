package application;

import java.io.PrintWriter;
import java.io.StringWriter;

import application.model.ErrorWindow;
import application.view.LoginController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Diese Klasse startet das Login-Fenster
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class Main extends Application {
	private Stage loginStage;

	/**
	 * Initialisiert und Startet das loginStage (Login-Fenster)
	 */
	@Override
	public void start(Stage loginStage) {
		try {
			this.loginStage = loginStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Login.fxml"));
			AnchorPane loginAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(loginAnchor);
			LoginController controller = loader.getController();
			controller.setMain(this);
			loginStage.setTitle("Pr0jectZer0");
			Image image = new Image("application/data/images/logo.png");
			loginStage.getIcons().add(image);
			loginStage.setScene(scene);
			loginStage.show();
		} catch (Exception e) {
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Starten der Applikation!", loginStage, e);
		}
	}

	/**
	 * Aufruf der Start()-Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Getter
	 * 
	 * @return loginStage
	 */
	public Stage getLoginStage() {
		return loginStage;
	}

}
