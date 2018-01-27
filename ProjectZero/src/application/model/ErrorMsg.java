package application.model;

import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ErrorMsg
{

	public static void newError(String msg, VBox vBox, TextField tField)
	{
		HBox hBox = new HBox();
		hBox.setSpacing(5);
		hBox.setAlignment(Pos.CENTER_LEFT);
		OctIconView icon = new OctIconView(OctIcon.ALERT);
		icon.setSize("14");
		Label label = new Label();
		label.setText(msg);
		label.getStyleClass().clear();
		label.getStyleClass().add("labelerror");
		tField.getStyleClass().add("wrong-details");
		hBox.getChildren().addAll(icon, label);
		vBox.getChildren().add(hBox);
	}

	public static void newError(String msg, VBox vBox)
	{
		HBox hBox = new HBox();
		hBox.setSpacing(5);
		hBox.setAlignment(Pos.CENTER_LEFT);
		OctIconView icon = new OctIconView(OctIcon.ALERT);
		icon.setSize("14");
		Label label = new Label();
		label.setText(msg);
		label.getStyleClass().clear();
		label.getStyleClass().add("labelerror");
		hBox.getChildren().addAll(icon, label);
		vBox.getChildren().add(hBox);
	}
}
