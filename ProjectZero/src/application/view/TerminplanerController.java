package application.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import application.api.Termin;
import application.api.Users;
import application.model.ErrorWindow;
import application.model.User;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TerminplanerController
{
	@FXML
	private VBox calendarBox;
	@FXML
	private Label date;
	@FXML
	private JFXDatePicker dateStart;
	@FXML
	private JFXDatePicker dateEnd;
	@FXML
	private TextField terminName;
	@FXML
	private TextArea terminBeschreibung;
	@FXML
	private VBox terminPlan;
	private List<Integer> addedTermine = new ArrayList<Integer>();

	@FXML
	private void initialize()
	{
		DatePicker dp = new DatePicker(LocalDate.now());
		dp.setShowWeekNumbers(false);
		DatePickerSkin calenderSkin = new DatePickerSkin(dp);
		dp.setMinWidth(500);
		DatePickerContent popupContent = (DatePickerContent) calenderSkin.getPopupContent();
		calendarBox.getChildren().add(0, popupContent);

		date.setText("Datum: " + dp.getValue());
		dp.valueProperty().addListener((observable, oldValue, newValue) -> {
			date.setText("Datum: " + dp.getValue());

			terminPlan.getChildren().clear();

			try
			{
				for (int i = 0; i < Termin.getUserTermine().size(); i++)
				{
					String[] s = Termin.getUserTermine().get(i).getStartDate().split(" ");
					int id = Termin.getUserTermine().get(i).getID();

					if (dp.getValue().toString().equals(s[0]))
					{
						addedTermine.add(Termin.getUserTermine().get(i).getID());
						Label titel = new Label(Termin.getUserTermine().get(i).getTitle());
						Label terminId = new Label(Integer.toString(Termin.getUserTermine().get(i).getID()));
						terminId.setVisible(false);
						Label beschreibung = new Label(Termin.getUserTermine().get(i).getDescription());
						beschreibung.setStyle(beschreibung.getStyle() + "-fx-font-size: 12.0pt;");
						JFXButton teilnehmer = new JFXButton("Teilnehmer (Id: " + id + ")");
						teilnehmer.getStyleClass().add("login-button");
						teilnehmer.setStyle(teilnehmer.getStyle() + "-fx-text-fill: #B2B2B2;");
						teilnehmer.setOnAction(new EventHandler<ActionEvent>()
						{
							@Override
							public void handle(ActionEvent e)
							{
								try
								{
									Stage teilnehmerStage = new Stage();
									FXMLLoader loader = new FXMLLoader();
									loader.setLocation(getClass().getResource("Teilnehmerliste.fxml"));
									String[] help = teilnehmer.getText().split(" ");
									String idS = help[2].replace(")", "");
									teilnehmerStage.setTitle("Pr0jectZer0 - Teilnehmerliste (" + idS + ")");
									Image image = new Image("application/data/images/logo.png");
									teilnehmerStage.getIcons().add(image);
									AnchorPane mainAnchor = (AnchorPane) loader.load();
									Scene scene = new Scene(mainAnchor);
									teilnehmerStage.setScene(scene);
									teilnehmerStage.showAndWait();
								}
								catch (Exception e1)
								{
									ErrorWindow.newErrorWindow("Es gab ein Fehler beim Öffnen des Teilnehmer-Fensters!",
											(Stage) titel.getScene().getWindow(), e1);
								}
							}
						});
						JFXButton loeschen = new JFXButton("Termin (" + id + ") Löschen");
						loeschen.setOnAction(new EventHandler<ActionEvent>()
						{
							@Override
							public void handle(ActionEvent e)
							{
								try
								{
									String[] help = loeschen.getText().split(" ");
									String terminId = help[1].replace("(", "");
									terminId = terminId.replace(")", "");
									Termin.deleteTermin(Integer.parseInt(terminId));
									dp.setValue(dp.getValue());
								}
								catch (Exception e1)
								{
									ErrorWindow.newErrorWindow("Es gab ein Fehler beim Löschen eines Termins!",
											(Stage) titel.getScene().getWindow(), e1);
								}
							}
						});
						loeschen.getStyleClass().add("login-button");
						loeschen.setStyle(loeschen.getStyle() + "-fx-text-fill: #B2B2B2;");

						HBox hBox = new HBox();
						hBox.setSpacing(10);
						hBox.setAlignment(Pos.CENTER);
						hBox.getChildren().addAll(teilnehmer, loeschen);

						VBox vBox = new VBox();
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(titel, beschreibung, hBox);
						vBox.setStyle(vBox.getStyle() + "-fx-background-color: derive(#2A2E37, 30.0%)");

						terminPlan.getChildren().add(vBox);

					}
					for (int j = 0; j < Termin.getSharedTermine().size(); j++)
					{
						String[] s2 = Termin.getSharedTermine().get(j).getStartDate().split(" ");

						if (dp.getValue().toString().equals(s2[0])
								&& !addedTermine.contains(Termin.getSharedTermine().get(i).getID()))
						{
							Label titel = new Label(Termin.getSharedTermine().get(j).getTitle());
							Label beschreibung = new Label(Termin.getSharedTermine().get(j).getDescription());
							beschreibung.setStyle(beschreibung.getStyle() + "-fx-font-size: 12.0pt;");
							JFXButton teilnehmer = new JFXButton("Teilnehmer");
							teilnehmer.getStyleClass().add("login-button");
							teilnehmer.setStyle(teilnehmer.getStyle() + "-fx-text-fill: #B2B2B2;");
							teilnehmer.setOnAction(new EventHandler<ActionEvent>()
							{
								@Override
								public void handle(ActionEvent e)
								{
									try
									{
										Stage teilnehmerStage = new Stage();
										FXMLLoader loader = new FXMLLoader();
										loader.setLocation(getClass().getResource("Teilnehmerliste.fxml"));
										teilnehmerStage.setTitle("Pr0jectZer0 - Teilnehmerliste");
										Image image = new Image("application/data/images/logo.png");
										teilnehmerStage.getIcons().add(image);
										AnchorPane mainAnchor = (AnchorPane) loader.load();
										Scene scene = new Scene(mainAnchor);
										teilnehmerStage.setScene(scene);
										teilnehmerStage.showAndWait();
									}
									catch (Exception e1)
									{
										ErrorWindow.newErrorWindow(
												"Es gab ein Fehler beim Öffnen des Teilnehmer-Fensters!",
												(Stage) titel.getScene().getWindow(), e1);
									}
								}
							});
							JFXButton loeschen = new JFXButton("Termin Löschen");
							loeschen.setDisable(true);
							loeschen.getStyleClass().add("login-button");
							loeschen.setStyle(loeschen.getStyle() + "-fx-text-fill: #B2B2B2;");

							HBox hBox = new HBox();
							hBox.setSpacing(10);
							hBox.setAlignment(Pos.CENTER);
							hBox.getChildren().addAll(teilnehmer, loeschen);

							VBox vBox = new VBox();
							vBox.setAlignment(Pos.CENTER);
							vBox.getChildren().addAll(titel, beschreibung, hBox);
							vBox.setStyle(vBox.getStyle() + "-fx-background-color: derive(#2A2E37, 30.0%)");

							terminPlan.getChildren().add(vBox);

						}
					}

					if (addedTermine.isEmpty())
					{
						Label label = new Label("Keine Termine vorhanden");
						terminPlan.getChildren().add(label);
					}
				}

			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	@FXML
	private void handleTerminErstellenAction(ActionEvent event)
	{
		try
		{
			if (dateStart.getValue() != null && dateEnd.getValue() != null && !terminName.getText().equals("")
					&& !terminBeschreibung.getText().equals(""))
			{
				Termin.createTermin(terminName.getText(), terminBeschreibung.getText(), dateStart.getValue().toString(),
						dateEnd.getValue().toString());
				Alert alert = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
				dialogPane.getStyleClass().add("myDialog");
				alert.setTitle("Termin erfolgreich hinzugefügt");
				alert.setHeaderText(null);
				alert.setContentText("Der Termin " + terminName.getText() + " wurde erfolgreich hinzugefügt!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				Stage owner = (Stage) date.getScene().getWindow();
				Image image = new Image("application/data/images/logo.png");
				stage.getIcons().add(image);
				alert.initOwner(owner);
				alert.showAndWait();
				terminName.clear();
				terminBeschreibung.clear();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add("application/data/css/ProjectZero_theme.css");
				dialogPane.getStyleClass().add("myDialog");
				alert.setTitle("Ungültiger Termin");
				alert.setHeaderText(null);
				alert.setContentText("Bitte füllen Sie alle Felder aus!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				Stage owner = (Stage) date.getScene().getWindow();
				Image image = new Image("application/data/images/logo.png");
				stage.getIcons().add(image);
				alert.initOwner(owner);
				alert.showAndWait();
			}
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Erstellen des Termins",
					(Stage) date.getScene().getWindow(), e);
		}
	}
}
