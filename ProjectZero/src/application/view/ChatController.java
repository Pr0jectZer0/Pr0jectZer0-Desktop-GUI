package application.view;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;

public class ChatController {

	@FXML
	private JFXTextArea tfanzeige;
	@FXML
	private JFXTextArea tfschreiben;
	
	@FXML
	private void initialize()
	{
		//tfanzeige.setStyle("-fx-text-inner-color: #9c2b27;");
	}
	
}
