package application.view;

import application.api.Termin;
import application.model.ErrorWindow;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TeilnehmerlisteController
{
	@FXML
	private TableView<User> userlist;
	@FXML
	private TableColumn<User, String> nameCol;
	@FXML
	private TableColumn<User, Integer> idCol;
	@FXML
	private TableView<User> teilnehmerlist;
	@FXML
	private TableColumn<User, String> nameCol2;
	@FXML
	private TableColumn<User, Integer> idCol2;

	@FXML
	private void initialize()
	{
		userlist.setPlaceholder(new Label("Keine User enthalten"));
		idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		nameCol.prefWidthProperty().bind(userlist.widthProperty().multiply(1.00));

		for (int i = 0; i < FreundeslisteController.getFreundeslistecontroller().friendlist.getItems().size(); i++)
		{
			userlist.getItems()
					.add(new User(
							FreundeslisteController.getFreundeslistecontroller().friendlist.getItems().get(i).getName(),
							FreundeslisteController.getFreundeslistecontroller().friendlist.getItems().get(i).getId()));
		}

		teilnehmerlist.setPlaceholder(new Label("Keine Teilnehmer enthalten"));
		nameCol2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		idCol2.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		nameCol2.prefWidthProperty().bind(userlist.widthProperty().multiply(1.00));

	}

	@FXML
	private void add(ActionEvent event)
	{
		try
		{
			if (!userlist.getSelectionModel().isEmpty())
			{
				teilnehmerlist.getItems().add(userlist.getSelectionModel().getSelectedItem());
				Stage stage = (Stage) userlist.getScene().getWindow();
				String[] help = stage.getTitle().split(" ");
				String id = help[3].replace(("("), "");
				id = id.replace(")", "");
				Termin.addUserToTermin(userlist.getSelectionModel().getSelectedItem().getId(), Integer.parseInt(id));
				userlist.getItems().remove(userlist.getSelectionModel().getSelectedItem());
				teilnehmerlist.refresh();
				userlist.refresh();
			}
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim hinzufügen eines Users zu einem Termin",
					(Stage) userlist.getScene().getWindow(), e);
		}
	}

	@FXML
	private void remove(ActionEvent event)
	{
		try
		{
			if (!teilnehmerlist.getSelectionModel().isEmpty())
			{
				userlist.getItems().add(teilnehmerlist.getSelectionModel().getSelectedItem());
				Stage stage = (Stage) userlist.getScene().getWindow();
				String[] help = stage.getTitle().split(" ");
				String id = help[3].replace(("("), "");
				id = id.replace(")", "");
				Termin.deleteUserFromTermin(teilnehmerlist.getSelectionModel().getSelectedItem().getId(),
						Integer.parseInt(id));
				teilnehmerlist.getItems().remove(teilnehmerlist.getSelectionModel().getSelectedItem());
				teilnehmerlist.refresh();
				userlist.refresh();
			}
		}
		catch (Exception e)
		{
			ErrorWindow.newErrorWindow("Es gab ein Fehler beim Entfernen eines Users eines Termins",
					(Stage) userlist.getScene().getWindow(), e);
		}
	}
}
