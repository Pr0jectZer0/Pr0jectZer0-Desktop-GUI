package application.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import application.api.Friends;
import application.api.Users;
import application.model.User;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * Diese Klasse verwaltet die Freundesliste
 * 
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class FreundeslisteController {
	@FXML
	private TableView<User> friendlist;
	private TableView<User> unfriendlist;
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
	 * Fï¿½gt die Freunde in die Freundesliste und legt deren Funktionen fest
	 */
	private void initFriends() {
		idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());		
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		try {
			friendlist.setItems(Friends.getFriends());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
		
		nameCol.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>() {
			@Override
			public TableCell<User, String> call(TableColumn<User, String> param) {
				ImageView imageView = new ImageView();
				TableCell<User, String> cell = new TableCell<User, String>() {
					public void updateItem(String item, boolean empty) {
						if (item != null) {
							imageView.setImage(new Image("application/data/images/friend.png"));
							setText(item);
						}
					}
				};
				cell.setGraphic(imageView);
				return cell;
			}
		});

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
	 * Diese Methode ï¿½ffnet die Freund-Hinzufï¿½gen-Funktionen
	 */
	private void initNewFriend() {
		friend.textProperty().addListener((ChangeListener<String>) (observableValue, newValue, oldValue) -> {
			if (friend.getText().length() >= 3) {
				JFXHamburger hamburger = new JFXHamburger();
				try {
					unfriendlist = new TableView<User>();
					unfriendlist.setItems(Users.getNoFriends());
					unfriendlist.getItems().add(new User ("test", 2));
					
					AnchorPane freundesliste = new AnchorPane();
					unfriendlist.setMinHeight(500);
					VBox vBox = new VBox();
					JFXButton button = new JFXButton("Hinzufügen");
					button.setMinWidth(250);
					button.setStyle(button.getStyle() + "-fx-fill: #B2B2B2;");
					vBox.getChildren().addAll(unfriendlist, button);
					freundesliste.getChildren().addAll(vBox);

					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							try {
								friendlist.getItems().add(unfriendlist.getSelectionModel().getSelectedItem());
								unfriendlist.getItems().remove(unfriendlist.getSelectionModel().getSelectedIndex());
								unfriendlist.refresh();
								friendlist.refresh();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					drawer.setSidePane(freundesliste);
				} catch (Exception e) {
					e.printStackTrace();
				}
				HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
				transition.setRate(-1);
				transition.setRate(transition.getRate() * -1);
				transition.play();
				drawer.setVisible(true);
				drawer.open();
				friendlist.setVisible(false);
			} else {
				drawer.setVisible(false);
				drawer.close();
				friendlist.setVisible(true);
			}
		});
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
	 * ï¿½ffnet die Zusatzfunktion wenn man mit der rechten Maustaste auf einen
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
	
//	public static TableView<User> getFriends() {
//		return friendlist;
//	}
	
//	public static TableView<User> getUnFriends() {
//		return unfriendlist;
//	}
	
}
