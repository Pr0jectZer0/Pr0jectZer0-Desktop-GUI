package application;

import java.io.PrintWriter;
import java.io.StringWriter;

import application.view.LoginController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Diese Klasse startet das Login-Fenster
 *  @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class Main extends Application {
	private Stage loginStage;
	private double xOffset = 0;
	private double yOffset = 0;

	/**
	 * Initialisiert und Startet das loginStage (Login-Fenster)
	 */
	@Override
	public void start(Stage loginStage) {
		try {
			this.loginStage = loginStage;
			FXMLLoader loader = new FXMLLoader();
			loginStage.initStyle(StageStyle.UNDECORATED);
			loader.setLocation(Main.class.getResource("view/Login.fxml"));
			AnchorPane loginAnchor = (AnchorPane) loader.load();
			loginAnchor.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
				}
			});
			loginAnchor.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					loginStage.setX(event.getScreenX() - xOffset);
					loginStage.setY(event.getScreenY() - yOffset);
				}
			});
			Scene scene = new Scene(loginAnchor);
			LoginController controller = loader.getController();
			controller.setMain(this);
			loginStage.setTitle("ProjectZero");
			loginStage.setScene(scene);
			loginStage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.initOwner(loginStage);
			alert.setHeaderText("Es gab ein Fehler beim Starten der Applikation!");
			alert.setContentText("Bitte wenden Sie sich an den Support.");

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
