package application.view;

import application.api.Friends;
import application.api.Users;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopupController {
	@FXML
	private AnchorPane panemain;
	private Stage popupstage;

	@FXML
	private void initialize() {
		this.popupstage = FreundeslisteController.getPopupstage();
		panemain.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				popupstage.hide();
			}
		});
	}

	@FXML
	private void chat() {
		try {
			Stage chatstage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Chat.fxml"));
			chatstage.setTitle("Platzhalter");
			Image image = new Image("application/data/images/logo.png");
			chatstage.getIcons().add(image);
			// chatstage.initStyle(StageStyle.UNDECORATED);
			AnchorPane chatAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(chatAnchor);
			chatstage.setScene(scene);
			chatstage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void removeFriend() {		
//		FreundeslisteController.getFriends().getItems().add(FreundeslisteController.getUnFriends().getSelectionModel().getSelectedItem());
//		FreundeslisteController.getUnFriends().getItems().remove(FreundeslisteController.getUnFriends().getSelectionModel().getSelectedIndex());
	}

}
