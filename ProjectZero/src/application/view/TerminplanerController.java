package application.view;

import java.time.LocalDate;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class TerminplanerController {
	// ########################################################################
	// Initialisierungen
	// ########################################################################
	
	DatePicker dp = new DatePicker(LocalDate.now());
	@FXML
	private VBox calendarVBox;

	@FXML
	private void initialize() {
		initiateCalendar();
	}

	private void initiateCalendar() {
		DatePickerSkin calenderSkin = new DatePickerSkin(dp);
		DatePickerContent popupContent = (DatePickerContent) calenderSkin.getPopupContent();
		calendarVBox.getChildren().add(0, popupContent);
		dp.setShowWeekNumbers(false);
	}

}
