package application.view;

import java.io.IOException;

import org.json.JSONException;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import application.api.UserGames;
import application.model.Game;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * Controller-Klasse für die Spielebibliothek
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class SpielebibliothekController
{
	@FXML
	private JFXListView<Game> gamelist;
	@FXML
	private Label selectedGameName;
	@FXML
	private JFXTextArea selectedGameDescription;

	/**
	 * Initialisierungen
	 */
	@FXML
	private void initialize()
	{
		initGames();
		initSelectedGame();
	}

	/**
	 * Diese Methode initialisert die Spieleliste in die Spielebibliothek (TableView)
	 */
	private void initGames()
	{
		try
		{
			gamelist.setItems(UserGames.getGames());
		}
		catch (IOException | JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode aktualisiert die Anzeige nach dem selektiertem Spiel
	 */
	private void initSelectedGame()
	{
		gamelist.getSelectionModel().selectedItemProperty()
				.addListener((ChangeListener<Game>) (observable, oldValue, newValue) -> {
					Game selectedGame = gamelist.getSelectionModel().getSelectedItem();
					selectedGameDescription.setText(selectedGame.getDescrption());
					selectedGameName.setText(selectedGame.getName());
				});
	}

	/**
	 * Diese Methode fügt ein Spiel der Spielebibliothek hinzu
	 */
	@FXML
	private void addGame()
	{
		// try {
		// String[][] parameter = { { "", "" }, { "id", newGame.getText() } };
		// String response =
		// HttpWebRequest.sendPostRequest("user/game/add?token=" +
		// LoginController.getToken(),
		// parameter);
		// } catch (Exception e) {
		// // TODO
		// }
	}

	/**
	 * Diese Methode löscht ein Spiel aus der Spielebibliothek
	 */
	@FXML
	private void deleteGame()
	{
		try
		{
			if (!gamelist.getSelectionModel().isEmpty())
			{
				int selectedGame = gamelist.getSelectionModel().getSelectedIndex();
				UserGames.delete(gamelist.getItems().get(selectedGame).getId());
				gamelist.getItems().remove(selectedGame);
				gamelist.refresh();
			}
		}
		catch (IOException e)
		{
			System.out.println("Backend Route not working(Server send error 400 - Game not in list)!");
			// e.printStackTrace();
		}
	}
}