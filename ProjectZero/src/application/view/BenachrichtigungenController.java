package application.view;

import java.io.IOException;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;

import application.api.Benachrichtigungen;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
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
			ObservableList<application.model.Benachrichtigungen> benachrichtigungen = Benachrichtigungen.getBenachrichtigungen();
			if (benachrichtigungen != null) {
				for (int i = 0; i < benachrichtigungen.size(); i++) {
					Label label = new Label();
					label.setText("Freundschaftsanfrage von " + benachrichtigungen.get(i).getUserName() + "(Id: "
							+ benachrichtigungen.get(i).getUserId() + "):");
	
					JFXButton accept = new JFXButton("Akzeptieren");
					JFXButton decline = new JFXButton("Ablehnen");
					accept.getStyleClass().add("login-button");
					accept.setStyle(accept.getStyle() + "-fx-text-fill: #B2B2B2;");
					decline.getStyleClass().add("login-button");
					decline.setStyle(decline.getStyle() + "-fx-text-fill: #B2B2B2;");
	
					DropShadow shadow = new DropShadow();
					shadow.setBlurType(BlurType.TWO_PASS_BOX);
					shadow.setColor(Color.RED);
					
					HBox hBox = new HBox();
					hBox.setEffect(shadow);
					hBox.setSpacing(20);
					hBox.getChildren().addAll(label, accept, decline);
					
					VBox vBox = new VBox();
					vBox.setSpacing(20);
					vBox.setStyle(vBox.getStyle() + "-fx-background-color: derive(#2A2E37, 20.0%);");
					vBox.getStylesheets().add("application/data/css/ProjectZero_Theme.css");
					vBox.setPadding(new Insets(10, 0, 10, 10));
					vBox.getChildren().addAll(hBox);
									
					benachrichtigungenBox.getChildren().add(vBox);
				}
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

	}
}
