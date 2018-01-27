package application.model;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
/**
 * Das ist die Klasse, die das ErrorFenster darstellt falls was schief läuft
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class ErrorWindow
{

	public static void newErrorWindow(String msg, Stage owner, Exception e)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fehler!");
		alert.setHeaderText(msg);
		alert.setContentText("Bitte wenden Sie sich an den Support.");
		alert.initOwner(owner);

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
