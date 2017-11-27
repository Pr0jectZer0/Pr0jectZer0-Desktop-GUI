package application.view;

import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SidePanelController implements Initializable {
	// ########################################################################
	// Initialisierungen
	// ########################################################################
	
	@FXML
	private JFXButton spielebibliothekB;
	@FXML
	private JFXButton terminplanerB;
	@FXML
	private JFXButton gruppenB;
	@FXML
	private JFXButton chaträumeB;
	@FXML
	private JFXButton notizenB;
	@FXML
	private JFXButton logoutB;
	@FXML
	private JFXButton closeB;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	// ########################################################################
	// Funktionen-Anchorpanes aufrufen
	// ########################################################################

	@FXML
	private void changeAction(ActionEvent event) {
		JFXButton btn = (JFXButton) event.getSource();
		switch (btn.getText()) {
		case "Spielebibliothek": MainLayoutController.rootTerminplanerAnchor.setVisible(false);
		MainLayoutController.rootChaträumeAnchor.setVisible(false);
		MainLayoutController.rootGruppenAnchor.setVisible(false);
		MainLayoutController.rootNotizenAnchor.setVisible(false);
		MainLayoutController.rootSpielebibliothekAnchor.setVisible(true);
			;
			break;
		case "Terminplaner": MainLayoutController.rootTerminplanerAnchor.setVisible(true);
		MainLayoutController.rootChaträumeAnchor.setVisible(false);
		MainLayoutController.rootGruppenAnchor.setVisible(false);
		MainLayoutController.rootNotizenAnchor.setVisible(false);
		MainLayoutController.rootSpielebibliothekAnchor.setVisible(false);
			;
			break;
		case "Gruppen": MainLayoutController.rootTerminplanerAnchor.setVisible(false);
		MainLayoutController.rootChaträumeAnchor.setVisible(false);
		MainLayoutController.rootGruppenAnchor.setVisible(true);
		MainLayoutController.rootNotizenAnchor.setVisible(false);
		MainLayoutController.rootSpielebibliothekAnchor.setVisible(false);
			;
			break;
		case "Chaträume": MainLayoutController.rootTerminplanerAnchor.setVisible(false);
		MainLayoutController.rootChaträumeAnchor.setVisible(true);
		MainLayoutController.rootGruppenAnchor.setVisible(false);
		MainLayoutController.rootNotizenAnchor.setVisible(false);
		MainLayoutController.rootSpielebibliothekAnchor.setVisible(false);
			;
			break;
		case "Notizen": MainLayoutController.rootTerminplanerAnchor.setVisible(false);
		MainLayoutController.rootChaträumeAnchor.setVisible(false);
		MainLayoutController.rootGruppenAnchor.setVisible(false);
		MainLayoutController.rootNotizenAnchor.setVisible(true);
		MainLayoutController.rootSpielebibliothekAnchor.setVisible(false);
			;
			break;
		case "Abmelden": System.exit(0);
			;
			break;
		case "Beenden": System.exit(0);
			;
			break;
		}
	}

}
