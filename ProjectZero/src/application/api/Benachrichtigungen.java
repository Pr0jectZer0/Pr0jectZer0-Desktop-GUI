package application.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Benachrichtigungen {

	private static ObservableList<application.model.Benachrichtigungen> benachrichtigungen = null;

	private Benachrichtigungen() {
	}

	public static ObservableList<application.model.Benachrichtigungen> getBenachrichtigungen() throws JSONException, IOException {
		if (benachrichtigungen == null) {
			JSONObject response = new JSONObject(
					HttpWebRequest.sendGetRequest("friend/requests?token=" + application.api.User.getLoginToken()));
			JSONArray requests = response.getJSONArray("requests");
			benachrichtigungen = FXCollections.observableArrayList();
			for (int i = 0; i < requests.length(); i++) {
				JSONObject curRequest = requests.getJSONObject(i);
				benachrichtigungen.add(new application.model.Benachrichtigungen(curRequest.getInt("id"), curRequest.getJSONObject("user").getString("name"), curRequest.getJSONObject("user").getInt("id")));
			}
		}
		return benachrichtigungen;
	}
}