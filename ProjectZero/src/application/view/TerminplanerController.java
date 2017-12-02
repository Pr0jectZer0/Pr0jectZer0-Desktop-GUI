package application.view;

import java.time.LocalDate;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

/**
 * Diese Methode enthält die Controller und Methoden des Terminplaners
 * 
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
@SuppressWarnings("restriction")
public class TerminplanerController {
	DatePicker dp = new DatePicker(LocalDate.now());
	@FXML
	private VBox calendarVBox;

	/**
	 * Initilisierungen
	 */
	@FXML
	private void initialize() {
		initiateCalendar();
	}

	/**
	 * Diese Methode Initialisiert den Kalender im Terminplaner
	 */
	private void initiateCalendar() {
		DatePickerSkin calenderSkin = new DatePickerSkin(dp);
		DatePickerContent popupContent = (DatePickerContent) calenderSkin.getPopupContent();
		calendarVBox.getChildren().add(0, popupContent);
		dp.setShowWeekNumbers(false);
	}

}
