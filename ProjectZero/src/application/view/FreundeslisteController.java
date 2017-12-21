package application.view;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import application.Main;
import application.api.Friends;
import application.model.HttpWebRequest;
import application.model.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Diese Klasse verwaltet die Freundesliste
 * 
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class FreundeslisteController {
	@FXML
	private TableView<User> friendlist;
	@FXML
	private TableColumn<User, String> nameCol;
	@FXML
	private TableColumn<User, Integer> idCol;
	@FXML
	private JFXTextField friend;
	@FXML
	private JFXDrawer drawer;
	private static Stage popupstage;
	private AnchorPane mainAnchor;

	@FXML
	private void initialize() {
		initFriends();
		initNewFriend();
		initPopup();
	}

	/**
	 * F�gt die Freunde in die Freundesliste und legt deren Funktionen fest
	 */
	private void initFriends() {
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

		try {
			friendlist.setItems(Friends.getFriends());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}

		friendlist.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.SECONDARY) {
					try {
						popupstage.setX(event.getScreenX() - 1);
						popupstage.setY(event.getScreenY() - 1);
						popupstage.hide();
						popupstage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});
	}

	/**
	 * Diese Methode �ffnet die Freund-Hinzuf�gen-Funktionen
	 */
	private void initNewFriend() {
		// friend.textProperty().addListener((ChangeListener<String>)
		// (observableValue, newValue, oldValue) -> {
		// if (friend.getText().length() >= 3) {
		// JFXHamburger hamburger = new JFXHamburger();
		// try {
		// ObservableList<String> subusers =
		// FXCollections.observableArrayList();
		// ArrayList<Integer> filteredusers = new ArrayList<Integer>();
		//
		// for (int i = 0; i < LoginController.getUsers().size(); i++) {
		// if (LoginController.getUsers().get(i).getName().toLowerCase()
		// .contains(friend.getText().toLowerCase())) {
		// subusers.add(LoginController.getUsers().get(i).getName());
		// filteredusers.add(LoginController.getUsers().get(i).getId());
		// }
		// }
		//
		// newfriendlist.getItems().clear();
		// newfriendlist.setItems(subusers);
		//
		// AnchorPane freundesliste = new AnchorPane();
		// newfriendlist.setMinHeight(500);
		// VBox vBox = new VBox();
		// JFXButton button = new JFXButton("Hinzuf�gen");
		// button.setMinWidth(250);
		// button.setStyle(button.getStyle() + "-fx-fill: #B2B2B2;");
		// vBox.getChildren().addAll(newfriendlist, button);
		// freundesliste.getChildren().addAll(vBox);
		//
		// button.setOnAction(new EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent e) {
		// try {
		// addNewFriend(filteredusers);
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// }
		// });
		// drawer.setSidePane(freundesliste);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// HamburgerBasicCloseTransition transition = new
		// HamburgerBasicCloseTransition(hamburger);
		// transition.setRate(-1);
		// transition.setRate(transition.getRate() * -1);
		// transition.play();
		// drawer.setVisible(true);
		// drawer.open();
		// friendlist.setVisible(false);
		// } else {
		// drawer.setVisible(false);
		// drawer.close();
		// friendlist.setVisible(true);
		// }
		// });
	}

	/**
	 * Diese Methode initialisert das Popup-Stage in der Freundesliste
	 */
	private void initPopup() {
		try {
			popupstage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Popup.fxml"));
			popupstage.setTitle("Pr0jectZer0");
			Image image = new Image("application/data/images/logo.png");
			popupstage.getIcons().add(image);
			popupstage.initStyle(StageStyle.UNDECORATED);
			mainAnchor = (AnchorPane) loader.load();
			Scene scene = new Scene(mainAnchor);
			popupstage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode �berpr�ft, ob sich der neu hinzuf�gende User sich
	 * bereits in der Freundesliste befindet und f�gt ihm ansonsten der
	 * Freundesliste hinzu
	 */
	private void addNewFriend(ArrayList<Integer> filteredusers) throws Exception {
		// int friendInTable =
		// newfriendlist.getSelectionModel().getSelectedIndex();
		// int friendId = filteredusers.get(friendInTable);
		// boolean nofriend = true;
		//
		// JSONObject friendsJSON = new JSONObject(
		// HttpWebRequest.sendGetRequest("friends?token=" +
		// LoginController.getToken()));
		// int friends = friendsJSON.getJSONArray("friends").length();
		//
		// for (int i = 0; i < friends; i++) {
		// if (friendsJSON.getJSONArray("friends").getJSONObject(i).getInt("id")
		// == friendId) {
		// nofriend = false;
		// }
		// }
		//
		// if (nofriend == true) {
		// newfriendlist.getItems().remove(friendInTable);
		// newfriendlist.refresh();
		//
		// String name = newfriendlist.getSelectionModel().getSelectedItem();
		// Label label = new Label(name);
		// FontAwesomeIconView icon = new
		// FontAwesomeIconView(FontAwesomeIcon.CHILD);
		// icon.setSize("14");
		// label.setGraphic(icon);
		// friendlist.getItems().add(label);
		//
		// String[][] parameter = { { "", "" }, { "id",
		// Integer.toString(friendId) } };
		// HttpWebRequest.sendPostRequest("friend/add?token=" +
		// LoginController.getToken(), parameter);
		// }
	}

	/**
	 * F�gt einen User der Freundesliste hinzu
	 */
	@FXML
	private void addFriend() {
//		try {
//			String[][] parameter = { { "", "" }, { "id", friend.getText() } };
//			HttpWebRequest.sendPostRequest("friend/add?token=" + LoginController.getToken(), parameter);
//		} catch (Exception e) {
//			// TODO
//		}
	}

	/**
	 * L�scht einen Freund aus der Freundesliste
	 * 
	 * @throws IOException
	 */
	@FXML
	private void removeFriend() {
//		try {
//			HttpWebRequest
//					.sendDeleteRequest("friend/remove/" + friend.getText() + "?token=" + LoginController.getToken());
//		} catch (IOException e) {
//			// TODO
//		}
	}

	/**
	 * �ffnet die Zusatzfunktion wenn man mit der rechten Maustaste auf einen
	 * Freund klickt
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void friendPopupMenu(MouseEvent event) throws IOException {
		JFXPopup popup = new JFXPopup();
		JFXButton b1 = new JFXButton("Nachrichten");
		JFXButton b2 = new JFXButton("Freund entfernen");
		b1.setPadding(new Insets(10));
		b2.setPadding(new Insets(10));
		b1.setMinWidth(100);
		b2.setMinWidth(100);
		b1.setAlignment(Pos.CENTER);
		b2.setAlignment(Pos.CENTER);
		VBox vBox = new VBox(b1, b2);

		popup.setPopupContent(vBox);

		AnchorPane freundesliste = FXMLLoader.load(getClass().getResource("Freundesliste.fxml"));

		freundesliste.getChildren().add(vBox);
		popup.show(friendlist, event.getX(), event.getY());
	}

	public static Stage getPopupstage() {
		return popupstage;
	}
}
