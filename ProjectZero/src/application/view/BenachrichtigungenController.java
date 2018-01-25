package application.view;

import java.io.IOException;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;

import application.api.Friends;
import application.api.Groups;
import application.model.FriendRequest;
import application.model.GroupRequest;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BenachrichtigungenController {
	@FXML
	private VBox benachrichtigungenBox;

	@FXML
	private void initialize() {
	initBenachrichtigungen();
	}

	private void initBenachrichtigungen() {
		try {
			ObservableList<FriendRequest> friendRequests = Friends.getFriendRequests();
			if (friendRequests != null) {
				for (int i = 0; i < friendRequests.size(); i++) {
					FriendRequest curFR = friendRequests.get(i);
					Label label = new Label();
					label.setText("Freundschaftsanfrage von " + curFR.getUserName() + "(Id: "
							+ curFR.getUserId() + "):");
					JFXButton btnAccept = new JFXButton("Akzeptieren");
					JFXButton btnDecline = new JFXButton("Ablehnen");
					btnAccept.getStyleClass().add("login-button");
					btnAccept.setStyle(btnAccept.getStyle() + "-fx-text-fill: #B2B2B2;");
					btnDecline.getStyleClass().add("login-button");
					btnDecline.setStyle(btnDecline.getStyle() + "-fx-text-fill: #B2B2B2;");
					DropShadow shadow = new DropShadow();
					shadow.setBlurType(BlurType.TWO_PASS_BOX);
					shadow.setColor(Color.RED);
			
					HBox hBox = new HBox();
					hBox.setEffect(shadow);
					hBox.setSpacing(20);
					hBox.getChildren().addAll(label, btnAccept, btnDecline);
					
					VBox vBox = new VBox();
					vBox.setSpacing(20);
					vBox.setStyle(vBox.getStyle() + "-fx-background-color: derive(#2A2E37, 20.0%);");
					vBox.getStylesheets().add("application/data/css/ProjectZero_Theme.css");
					vBox.setPadding(new Insets(10, 0, 10, 10));
					vBox.getChildren().addAll(hBox);
									
					benachrichtigungenBox.getChildren().add(vBox);
					
					btnAccept.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							acceptFriendRequest(curFR.getId(), vBox);
						}
					});
					btnDecline.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							declineFriendRequest(curFR.getId(), vBox);
						}
					});
				}
			}
			ObservableList<GroupRequest> groupRequests = Groups.getGroupRequests();
			if (groupRequests != null) {
				for (int i = 0; i < groupRequests.size(); i++) {
					GroupRequest curGR = groupRequests.get(i);
					Label label = new Label();
					label.setText("Gruppenanfrage von " + curGR.getGroup().getName() + "(Id: "
							+ curGR.getGroup().getID() + "):");
					JFXButton btnAccept = new JFXButton("Akzeptieren");
					JFXButton btnDecline = new JFXButton("Ablehnen");
					btnAccept.getStyleClass().add("login-button");
					btnAccept.setStyle(btnAccept.getStyle() + "-fx-text-fill: #B2B2B2;");
					btnDecline.getStyleClass().add("login-button");
					btnDecline.setStyle(btnDecline.getStyle() + "-fx-text-fill: #B2B2B2;");
					DropShadow shadow = new DropShadow();
					shadow.setBlurType(BlurType.TWO_PASS_BOX);
					shadow.setColor(Color.RED);
			
					HBox hBox = new HBox();
					hBox.setEffect(shadow);
					hBox.setSpacing(20);
					hBox.getChildren().addAll(label, btnAccept, btnDecline);
					
					VBox vBox = new VBox();
					vBox.setSpacing(20);
					vBox.setStyle(vBox.getStyle() + "-fx-background-color: derive(#2A2E37, 20.0%);");
					vBox.getStylesheets().add("application/data/css/ProjectZero_Theme.css");
					vBox.setPadding(new Insets(10, 0, 10, 10));
					vBox.getChildren().addAll(hBox);
									
					benachrichtigungenBox.getChildren().add(vBox);
					
					btnAccept.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							acceptGroupRequest(curGR.getGroup().getID(), vBox);
						}
					});
					btnDecline.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							declineGroupRequest(curGR.getGroup().getID(), vBox);
						}
					});
				}
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void acceptFriendRequest(int id, VBox vBox) {
		try {
			if(Friends.acceptRequest(id)) {
				benachrichtigungenBox.getChildren().remove(vBox);
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void declineFriendRequest(int id, VBox vBox) {
		try {
			if(Friends.declineRequest(id)) {
				benachrichtigungenBox.getChildren().remove(vBox);
			}
				
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void acceptGroupRequest(int groupID, VBox vBox) {
		try {
			if (Groups.acceptGroupRequest(groupID)) {
				benachrichtigungenBox.getChildren().remove(vBox);
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void declineGroupRequest(int groupID, VBox vBox) {
		try {
			if (Groups.declineGroupRequest(groupID)) {
				benachrichtigungenBox.getChildren().remove(vBox);
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
}