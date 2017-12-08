package application.view;

import java.io.IOException;

import org.json.JSONObject;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import application.model.Game;
import application.model.HttpWebRequest;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SpielebibliothekController {
	@FXML
	private JFXListView<Object> gamelist;
	@FXML
	private Label selectedGameName;
	@FXML
	private JFXTextArea selectedGameDescription;
	private static ObservableList<Game> games = FXCollections.observableArrayList();
	private static ObservableList<Integer> subgames = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		initGames();
		initSelectedGame();
	}

	private void initGames() {
		try {
			JSONObject response = new JSONObject(
					HttpWebRequest.sendGetRequest("user/game/list?token=" + LoginController.getToken()));

			int anzgames = response.getJSONArray("games").length();

			for (int i = 0; i < anzgames; i++) {
				games.add(new Game(response.getJSONArray("games").getJSONObject(i).getString("name"),
						response.getJSONArray("games").getJSONObject(i).getString("beschreibung"),
						response.getJSONArray("games").getJSONObject(i).getInt("id")));
				subgames.add(response.getJSONArray("games").getJSONObject(i).getInt("id"));
				gamelist.getItems().add(response.getJSONArray("games").getJSONObject(i).getString("name"));
			}
		} catch (IOException e) {
			// TODO
		}
	}
	
	private void initSelectedGame() {
		gamelist.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
			System.out.println("test");
			int selectedGame = gamelist.getSelectionModel().getSelectedIndex();
			int selectedGameId = subgames.get(selectedGame);
			System.out.println("test4");
			for (int i = 0; i<games.size(); i++) {
				System.out.println("test5");
				if (selectedGameId == games.get(i).getId()) {
					System.out.println("test6");
					selectedGameName.setText(games.get(i).getName());
					selectedGameDescription.setText(games.get(i).getDescrption());
				}
			}
			
		});
	}

	@FXML
	private void addGame() {
//		try {
//			String[][] parameter = { { "", "" }, { "id", newGame.getText() } };
//			String response = HttpWebRequest.sendPostRequest("user/game/add?token=" + LoginController.getToken(),
//					parameter);
//		} catch (Exception e) {
//			// TODO
//		}
	}

	@FXML
	private void deleteGame() {
		try {
			if (!gamelist.getSelectionModel().isEmpty()) {
				int selectedGame = gamelist.getSelectionModel().getSelectedIndex();
				gamelist.getItems().remove(selectedGame);
				subgames.remove(selectedGame);
				int selectedGameId = games.get(selectedGame).getId();
				games.remove(selectedGame);
				gamelist.refresh();
				System.out.println(selectedGameId);
				String response = HttpWebRequest.sendDeleteRequest(
						"user/game/remove/" + selectedGameId + "?token=" + LoginController.getToken());
				System.out.println(response);
			}
		} catch (IOException e) {
			// TODO
		}
	}

}
