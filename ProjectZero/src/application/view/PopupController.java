package application.view;

import org.json.JSONException;

import application.api.Friends;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * Controller-Klasse für das Popup bei der Freundesliste
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class PopupController
{
	@FXML
	private AnchorPane panemain;
	private Stage popupstage;

	/**
	 * Initialisierungen
	 */
	@FXML
	private void initialize()
	{
		this.popupstage = FreundeslisteController.getPopupstage();
		panemain.setOnMouseExited(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				popupstage.hide();
			}
		});
	}

	/**
	 * Diese Methode initialisiert das Chat-Fenster
	 */
	@FXML
	private void chat()
	{
		try
		{
			Stage chatstage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Chat.fxml"));
			// chatstage.initStyle(StageStyle.UNDECORATED);
			AnchorPane chatAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(chatAnchor);
			chatstage.setScene(scene);
			chatstage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode entfernt ein Freund von der Freundesliste
	 */
	@FXML
	private void removeFriend()
	{
		application.model.User friend = FreundeslisteController.getFreundeslistecontroller().friendlist
				.getSelectionModel().getSelectedItem();
		if (friend != null)
		{
			FreundeslisteController.getFreundeslistecontroller().friendlist.getItems().remove(friend);
			FreundeslisteController.getFreundeslistecontroller().friendlist.refresh();
			try
			{
				if (Friends.delete(friend.getId()))
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					DialogPane dialogPane = alert.getDialogPane();
					dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
					dialogPane.getStyleClass().add("myDialog");
					alert.setTitle("Freund erfolgreich gel�scht");
					alert.setHeaderText(null);
					alert.setContentText("Freund gel�scht!");
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					Image image = new Image("application/data/images/logo.png");
					stage.getIcons().add(image);
					alert.initOwner(null);
					alert.showAndWait();
				}
				else
				{
					Alert alert = new Alert(AlertType.ERROR);
					DialogPane dialogPane = alert.getDialogPane();
					dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
					dialogPane.getStyleClass().add("myDialog");
					alert.setTitle("Freund nicht gel�scht");
					alert.setHeaderText(null);
					alert.setContentText("Fehler beim L�schen des Freundes!");
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					Image image = new Image("application/data/images/logo.png");
					stage.getIcons().add(image);
					alert.initOwner(null);
					alert.showAndWait();
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
	}

}
