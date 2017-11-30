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
	private AnchorPane chaträumeAnchor;
	@FXML
	private AnchorPane gruppenAnchor;
	@FXML
	private AnchorPane terminplanerAnchor;
	@FXML
	private AnchorPane notizenAnchor;
	@FXML
	private AnchorPane spielebibliothekAnchor;
	public static AnchorPane rootChaträumeAnchor;
	public static AnchorPane rootGruppenAnchor;
	public static AnchorPane rootTerminplanerAnchor;
	public static AnchorPane rootNotizenAnchor;
	public static AnchorPane rootSpielebibliothekAnchor;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		rootChaträumeAnchor = chaträumeAnchor;
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

	// ########################################################################
	// Beenden
	// ########################################################################

	@FXML
	private void close() {
		System.exit(0);
	}

	// ########################################################################
	// Getters & Setters
	// ########################################################################

	public AnchorPane getChaträumeAnchor() {
		return chaträumeAnchor;
	}

	public AnchorPane getGruppenAnchor() {
		return gruppenAnchor;
	}

	public AnchorPane getTerminplanerAnchor() {
		return terminplanerAnchor;
	}

	public AnchorPane getNotizenAnchor() {
		return notizenAnchor;
	}

	public AnchorPane getSpielebibliothekAnchor() {
		return spielebibliothekAnchor;
	}

}
