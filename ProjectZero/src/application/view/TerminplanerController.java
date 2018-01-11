package application.view;

import java.time.LocalDate;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class TerminplanerController {
	@FXML
	private VBox calendarBox;
	
	@FXML
	private void initialize() {
		DatePicker dp = new DatePicker(LocalDate.now());
		dp.setShowWeekNumbers(false);
		DatePickerSkin calenderSkin = new DatePickerSkin(dp);
		dp.setMinWidth(500);
//		dp.setMinHeight();
		DatePickerContent popupContent = (DatePickerContent) calenderSkin.getPopupContent();
		calendarBox.getChildren().add(0, popupContent);
	}
}
