package application.view;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

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
	private void initialize() {
		initFreundesliste();
	}

	private void initFreundesliste() {
		try {
		ScrollPane freundesliste = FXMLLoader.load(getClass().getResource("Freundesliste.fxml"));
		drawer.setSidePane(freundesliste);
		} catch (Exception e) {
			
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
}
