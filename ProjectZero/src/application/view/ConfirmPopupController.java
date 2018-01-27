package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * Controller-Klasse für das Popupfenster in Notizen
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class ConfirmPopupController
{

	@FXML
	Label lblprim;
	@FXML
	Label lblsec;

	private Stage stage;

	@FXML
	private void initialize()
	{
		this.stage = NotizController.getNotizController().getConfStage();
		this.stage.setResizable(false);
		this.stage.setTitle("Speichern");
		this.stage.show();
	}

	@FXML
	private void jaButtonAction()
	{
		NotizController.getNotizController().saveExtern();
		this.stage.close();
	}

	@FXML
	private void neinButtonAction()
	{
		NotizController.getNotizController().dontSave();
		this.stage.close();
	}
}
