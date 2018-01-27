package application.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import application.api.Friends;
import application.api.Friends.FriendAddState;
import application.api.Users;
import application.model.ErrorWindow;
import application.model.User;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * Diese Klasse verwaltet die Freundesliste
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class FreundeslisteController
{
	@FXML
	public TableView<User> friendlist;
	private TableView<User> newFriendlist;
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
	private static FreundeslisteController freundeslistecontroller;

	/**
	 * Initialisierungen
	 */
	@FXML
	private void initialize()
	{
		initFriends();
		initNewFriend();
		initPopup();
		freundeslistecontroller = this;
	}

	/**
	 * Fügt die Freunde in die Freundesliste und legt deren Funktionen fest
	 */
	private void initFriends()
	{
		friendlist.setPlaceholder(new Label("Füge jetzt deine Freunde hinzu"));
		idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		friendlist.setStyle(friendlist.getStyle() + "-fx-background-color:  derive(-fx-primary, 10.0%);");

		friend.getStyleClass().add("deleteTextField");

		try
		{
			friendlist.setItems(Friends.getFriends());
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Hinzufügen aller Freunde!",
					(Stage) friendlist.getScene().getWindow(), e);
		}

		friendlist.getSelectionModel().selectFirst();

		nameCol.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>()
		{
			@Override
			public TableCell<User, String> call(TableColumn<User, String> param)
			{
				ImageView imageView = new ImageView();
				TableCell<User, String> cell = new TableCell<User, String>()
				{
					public void updateItem(String item, boolean empty)
					{
						if (item != null)
						{
							imageView.setImage(new Image("application/data/images/friend.png"));
							if (friendlist.getSelectionModel().isEmpty())
							{
								friendlist.getSelectionModel().selectFirst();
							}
							setText(item + " (#" + friendlist.getSelectionModel().getSelectedItem().getId() + ")");
							friendlist.getSelectionModel().selectNext();
						}
					}
				};
				cell.setGraphic(imageView);
				return cell;
			}
		});

		friendlist.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.SECONDARY
						&& friendlist.getSelectionModel().getSelectedItem() != null)
				{
					try
					{
						popupstage.setX(event.getScreenX() - 1);
						popupstage.setY(event.getScreenY() - 1);
						popupstage.hide();
						popupstage.show();
					}
					catch (Exception e)
					{
						ErrorWindow.newErrorWindow(
								"Es gab ein Fehler beim Öffnen des Popup-Fensters der Freundesliste!",
								(Stage) friendlist.getScene().getWindow(), e);
					}

				}
			}
		});
	}

	/**
	 * Diese Methode öffnet die Freund-Hinzufügen-Funktionen
	 */
	@SuppressWarnings("unchecked")
	private void initNewFriend()
	{
		newFriendlist = new TableView<User>();
		newFriendlist.setPlaceholder(new Label("Keinen Freund gefunden"));
		newFriendlist.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				try
				{
					if (event.getButton().equals(MouseButton.PRIMARY))
					{
						if (event.getClickCount() == 2)
						{
							String newFriend = newFriendlist.getSelectionModel().getSelectedItem().getName() + " (Id: "
									+ newFriendlist.getSelectionModel().getSelectedItem().getId() + ")";

							FriendAddState response = Friends
									.add(newFriendlist.getSelectionModel().getSelectedItem().getId());
							if (response == FriendAddState.Success)
							{
								Friends.getFriends().add(newFriendlist.getSelectionModel().getSelectedItem());
								Users.getNoFriends().remove(newFriendlist.getSelectionModel().getSelectedItem());
								newFriendlist.refresh();
								friendlist.refresh();

								Alert alert = new Alert(AlertType.INFORMATION);
								DialogPane dialogPane = alert.getDialogPane();
								dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
								dialogPane.getStyleClass().add("myDialog");
								alert.setTitle("Freund hinzufügen");
								alert.setHeaderText(null);
								alert.setContentText(newFriend + " erfolgreich hinzugefügt!");

								Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
								Stage owner = (Stage) newFriendlist.getScene().getWindow();
								Image image = new Image("application/data/images/logo.png");
								stage.getIcons().add(image);
								alert.initOwner(owner);
								alert.showAndWait();
							}
							else if (response == FriendAddState.AlreadyFriends)
							{
								Alert alert = new Alert(AlertType.INFORMATION);
								DialogPane dialogPane = alert.getDialogPane();
								dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
								dialogPane.getStyleClass().add("myDialog");
								alert.setTitle("Freund hinzufügen");
								alert.setHeaderText(null);
								alert.setContentText(newFriend + " hat bereits eine Freundschaftsanfrage erhalten!");

								Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
								Stage owner = (Stage) newFriendlist.getScene().getWindow();
								Image image = new Image("application/data/images/logo.png");
								stage.getIcons().add(image);
								alert.initOwner(owner);
								alert.showAndWait();
							}
							else
							{ // if (response == FriendAddState.ServerError)
								Alert alert = new Alert(AlertType.INFORMATION);
								DialogPane dialogPane = alert.getDialogPane();
								dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
								dialogPane.getStyleClass().add("myDialog");
								alert.setTitle("Freund hinzufügen");
								alert.setHeaderText(null);
								alert.setContentText(
										"Es gabe einen Serverfehler beim hinzufügen von " + newFriend + "!");

								Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
								Stage owner = (Stage) newFriendlist.getScene().getWindow();
								Image image = new Image("application/data/images/logo.png");
								stage.getIcons().add(image);
								alert.initOwner(owner);
								alert.showAndWait();
							}
						}
					}
				}
				catch (Exception e)
				{
					ErrorWindow.newErrorWindow(
							"Es gab ein Fehler beim Hinzufügen eines neuen Freundes durch Maus-Doppelklick!",
							(Stage) friendlist.getScene().getWindow(), e);
				}
			}
		});

		DropShadow shadow = new DropShadow();
		shadow.setBlurType(BlurType.TWO_PASS_BOX);
		shadow.setColor(Color.RED);

		TableColumn<User, String> nameCol2 = new TableColumn<User, String>();
		nameCol2.setStyle(nameCol2.getStyle() + "-fx-alignment: CENTER_LEFT;");
		nameCol2.setEditable(false);
		TableColumn<User, Integer> idCol2 = new TableColumn<User, Integer>();
		idCol2.setMaxWidth(0);
		newFriendlist.getColumns().addAll(nameCol2, idCol2);
		nameCol2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		idCol2.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

		JFXHamburger hamburger = new JFXHamburger();

		AnchorPane newFreundesliste = new AnchorPane();
		newFreundesliste.setMinHeight(546);
		newFreundesliste.setMinWidth(250);
		newFreundesliste.setMaxWidth(250);
		newFreundesliste.setStyle(newFreundesliste.getStyle() + "-fx-background-color:  #2A2E37;");
		newFreundesliste.getStylesheets().add("application/data/css/ProjectZero_Theme.css");
		newFreundesliste.setEffect(shadow);
		newFriendlist.getStyleClass().add("noheader");
		newFriendlist.setMinHeight(465);
		newFriendlist.setMinWidth(250);
		newFriendlist.maxWidth(250);
		newFriendlist.setFixedCellSize(31);

		JFXButton button = new JFXButton("Hinzufügen");
		button.setMinWidth(200);
		button.getStyleClass().add("login-button");
		button.setStyle(button.getStyle() + "-fx-text-fill: #B2B2B2;" + "-fx-font-size: 13.0pt;");

		StackPane stack = new StackPane();
		stack.getChildren().add(button);
		stack.setAlignment(Pos.CENTER);

		VBox vBox = new VBox();
		vBox.setMinHeight(546);
		vBox.setMinWidth(250);
		vBox.setSpacing(12);
		vBox.getChildren().addAll(newFriendlist, stack);

		newFreundesliste.getChildren().addAll(vBox);
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent e)
			{
				try
				{
					if (newFriendlist.getSelectionModel().getSelectedItem() != null)
					{
						String newFriend = newFriendlist.getSelectionModel().getSelectedItem().getName() + " (Id: "
								+ newFriendlist.getSelectionModel().getSelectedItem().getId() + ")";

						Friends.add(newFriendlist.getSelectionModel().getSelectedItem().getId());
						Friends.getFriends().add(newFriendlist.getSelectionModel().getSelectedItem());
						Users.getNoFriends().remove(newFriendlist.getSelectionModel().getSelectedItem());
						newFriendlist.refresh();
						friendlist.refresh();

						Alert alert = new Alert(AlertType.INFORMATION);
						DialogPane dialogPane = alert.getDialogPane();
						dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
						dialogPane.getStyleClass().add("myDialog");
						alert.setTitle("Freund hinzufügen");
						alert.setHeaderText(null);
						alert.setContentText(newFriend + " erfolgreich hinzugefügt!");

						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						Stage owner = (Stage) newFriendlist.getScene().getWindow();
						Image image = new Image("application/data/images/logo.png");
						stage.getIcons().add(image);
						alert.initOwner(owner);
						alert.showAndWait();
					}
				}
				catch (Exception e1)
				{
					ErrorWindow.newErrorWindow("Es gab ein Fehler beim Hinzufügen eines neuen Freunde",
							(Stage) friendlist.getScene().getWindow(), e1);
				}
			}
		});

		try
		{
			FilteredList<User> filteredData = new FilteredList<>(Users.getNoFriends(), p -> true);

			friend.textProperty().addListener((observable, oldValue, newValue) -> {
				newFriendlist.getSelectionModel().selectFirst();
				if (friend.getText().length() >= 1)
				{
					drawer.setSidePane(newFreundesliste);
					HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
					transition.setRate(-1);
					transition.setRate(transition.getRate() * -1);
					transition.play();
					drawer.setVisible(true);
					drawer.open();
					friendlist.setVisible(false);
					filteredData.setPredicate(user -> {
						if (newValue == null || newValue.isEmpty())
						{
							return true;
						}

						String lowerCaseFilter = newValue.toLowerCase();

						if (user.getName().toLowerCase().contains(lowerCaseFilter))
						{
							return true;
						}
						else if (newValue.matches("[0-9]+"))
						{
							if (user.getId() == Integer.parseInt(newValue))
							{
								return true;
							}
						}
						return false;
					});
				}
				else
				{
					drawer.setVisible(false);
					drawer.close();
					friendlist.setVisible(true);
				}
			});

			SortedList<User> sortedData = new SortedList<>(filteredData);

			sortedData.comparatorProperty().bind(newFriendlist.comparatorProperty());

			newFriendlist.setItems(sortedData);

			newFriendlist.getSelectionModel().select(0);
		}
		catch (IOException e2)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Filtern der Nutzerliste!",
					(Stage) friendlist.getScene().getWindow(), e2);
		}
	}

	/**
	 * Diese Methode initialisert das Popup-Stage in der Freundesliste
	 */
	private void initPopup()
	{
		try
		{
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
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Öffnen des Popup-Fensters!",
					(Stage) friendlist.getScene().getWindow(), e);
		}
	}

	/**
	 * Öffnet die Zusatzfunktion wenn man mit der rechten Maustaste auf einen
	 * Freund klickt
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void friendPopupMenu(MouseEvent event) throws IOException
	{
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

	public static Stage getPopupstage()
	{
		return popupstage;
	}

	public static FreundeslisteController getFreundeslistecontroller()
	{
		return freundeslistecontroller;
	}
}