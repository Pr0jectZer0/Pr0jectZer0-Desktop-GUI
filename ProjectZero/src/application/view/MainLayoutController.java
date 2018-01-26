package application.view;

import java.io.IOException;

import org.json.JSONException;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import application.api.Friends;
import application.api.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Main-Controller-Klasse
 * 
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class MainLayoutController {
	@FXML
	private JFXDrawer drawer;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	public Label benachrichtigungenCount;
	private static MainLayoutController mainLayoutController;

	@FXML
	private void initialize() {
		initFreundesliste();
		mainLayoutController = this;
		benachrichtigungenCount.setText(Integer.toString(BenachrichtigungenController.benachrichtigungCounter));
		
		if (BenachrichtigungenController.benachrichtigungCounter > 0) {
			benachrichtigungenCount.setStyle(benachrichtigungenCount.getStyle() + "-fx-background-color: #9C2B27;");
		} else {
			benachrichtigungenCount.setStyle(benachrichtigungenCount.getStyle() + "-fx-background-color: #B2B2B2;");
		}
	}

	private void initFreundesliste() {
		try {
			AnchorPane freundesliste = FXMLLoader.load(getClass().getResource("Freundesliste.fxml"));
			drawer.setSidePane(freundesliste);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
		transition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();
			if (drawer.isShown()) {
				drawer.close();
			} else
				drawer.open();
		});
	}

	public static MainLayoutController getMainLayoutController()
	{
		return mainLayoutController;
	}

}
