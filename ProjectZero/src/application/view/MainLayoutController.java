package application.view;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Main-Controller-Klasse
 * 
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class MainLayoutController implements Initializable {
	// ########################################################################
	// Initialisierungen
	// ########################################################################

	@FXML
	private JFXDrawer drawer;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private AnchorPane actionAnchor;
	@FXML
	private AnchorPane chatr‰umeAnchor;
	@FXML
	private AnchorPane gruppenAnchor;
	@FXML
	private AnchorPane terminplanerAnchor;
	@FXML
	private AnchorPane notizenAnchor;
	@FXML
	private AnchorPane spielebibliothekAnchor;
	public static AnchorPane rootChatr‰umeAnchor;
	public static AnchorPane rootGruppenAnchor;
	public static AnchorPane rootTerminplanerAnchor;
	public static AnchorPane rootNotizenAnchor;
	public static AnchorPane rootSpielebibliothekAnchor;

	/**
	 * Diese Methode initialisiert den Drawer und Hamburger
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		rootChatr‰umeAnchor = chatr‰umeAnchor;
		rootGruppenAnchor = gruppenAnchor;
		rootTerminplanerAnchor = terminplanerAnchor;
		rootNotizenAnchor = notizenAnchor;
		rootSpielebibliothekAnchor = spielebibliothekAnchor;
		try {
			VBox box = FXMLLoader.load(getClass().getResource("SidePanel.fxml"));
			drawer.setSidePane(box);
		} catch (IOException ex) {
			Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
		}
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
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

	/**
	 * Die Methode schlieﬂt die Applikation
	 */
	@FXML
	private void close() {
		System.exit(0);
	}

	/**
	 * Getter
	 * 
	 * @return chatr‰umeAnchor
	 */
	public AnchorPane getChatr‰umeAnchor() {
		return chatr‰umeAnchor;
	}

	/**
	 * Getter
	 * 
	 * @return gruppenAnchor
	 */
	public AnchorPane getGruppenAnchor() {
		return gruppenAnchor;
	}

	/**
	 * Getter
	 * 
	 * @return terminplanerAnchor
	 */
	public AnchorPane getTerminplanerAnchor() {
		return terminplanerAnchor;
	}

	/**
	 * Getter
	 * 
	 * @return notizenAnchor
	 */
	public AnchorPane getNotizenAnchor() {
		return notizenAnchor;
	}

	/**
	 * Getter
	 * 
	 * @return spielebibliothekAnchor
	 */
	public AnchorPane getSpielebibliothekAnchor() {
		return spielebibliothekAnchor;
	}

}
