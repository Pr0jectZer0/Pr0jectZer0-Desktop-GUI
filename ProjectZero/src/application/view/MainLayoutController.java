package application.view;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import application.api.Friends;
import application.api.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
	
	private AnchorPane freundesliste = null;
	@FXML
	private void initialize() {
		initFreundesliste();
		System.out.println(User.getLoginToken());
	}

	private void initFreundesliste() {
		
		try {
		    freundesliste = FXMLLoader.load(getClass().getResource("Freundesliste.fxml"));
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
			//	drawer.setVisible(false);
			//	freundesliste.setVisible(false);
			//	hamburger.setVisible(false);
			} else
				drawer.open();
			//	drawer.setVisible(true);
			//	freundesliste.setVisible(true);
			//	hamburger.setVisible(true);
		});
	}

}
