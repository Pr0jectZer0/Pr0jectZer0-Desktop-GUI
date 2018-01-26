package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.TerminRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Termin {
	
	private static ObservableList<TerminRequest> terminRequests;
	
	private Termin() { }
	
	public static application.model.Termin createTermin(String title, String description, String startDate, String endDate) throws JSONException, IOException {
		if (title == null || title.isEmpty() || description == null || description.isEmpty() || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		String[][] parameter = {{ "titel", title }, { "beschreibung", description}, {"start_datum", startDate}, {"end_datum", endDate} };
		JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("date?token=" + User.getLoginToken(), parameter));
		String titleGot = response.getString("titel");
		String descriptionGot = response.getString("beschreibung");
		String startDateGot = response.getString("start_datum");
		String endDateGot = response.getString("end_datum");
		int id = response.getInt("id");
		return new application.model.Termin(startDateGot, endDateGot, titleGot, descriptionGot, id);
	}
	
	public static boolean deleteTermin(int id) throws JSONException, IOException {
		if (id < 0)
			return false;
		JSONObject response = new JSONObject(HttpWebRequest.sendDeleteRequest("date/" + id + "?token=" + User.getLoginToken()));
		return response.getString("message").equals("Termin wurde gelöscht.");
	}
	
	/**
	 * Not working because backend route is not working
	 * @param id
	 * @param title
	 * @param description
	 * @param startDate
	 * @param endDate
	 * @return the updated termin
	 * @throws JSONException
	 * @throws IOException
	 */
	public static application.model.Termin updateTermin(int id, String title, String description, String startDate, String endDate) throws JSONException, IOException {
		if (id < 0 || title == null || title.isEmpty() || description == null || description.isEmpty() || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		String[][] parameter = {{ "titel", title }, { "beschreibung", description}, {"start_datum", startDate}, {"end_datum", endDate} };
		JSONObject response = new JSONObject(HttpWebRequest.sendPutRequest("date/" + id + "?token=" + User.getLoginToken(), parameter)).getJSONObject("date");
		String titleGot = response.getString("titel");
		String descriptionGot = response.getString("beschreibung");
		String startDateGot = response.getString("start_datum");
		String endDateGot = response.getString("end_datum");
		int idGot = response.getInt("id");
		return new application.model.Termin(startDateGot, endDateGot, titleGot, descriptionGot, idGot);
	}
	
	public static application.model.Termin getTerminByID(int id) throws JSONException, IOException {
		if (id < 0) {
			return null;
		}
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("date/" + id + "?token=" + User.getLoginToken())).getJSONObject("date");
		String titleGot = response.getString("titel");
		String descriptionGot = response.getString("beschreibung");
		String startDateGot = response.getString("start_datum");
		String endDateGot = response.getString("end_datum");
		int idGot = response.getInt("id");
		return new application.model.Termin(startDateGot, endDateGot, titleGot, descriptionGot, idGot);
	}
	
	public static boolean addUserToTermin(int userID, int terminID) throws JSONException, IOException {
		if (userID < 0 || terminID < 0) {
			return false;
		}
		String[][] parameter = {{ "id", Integer.toString(userID) }};
		JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("date/" + terminID + "/add_user?token=" + User.getLoginToken(), parameter));
		return response.getString("message").equals("Termin anfrage wurde an User gesendet.");
	}
	
	public static boolean deleteUserFromTermin(int userID, int terminID) throws JSONException, IOException {
		if (userID < 0 || terminID < 0) {
			return false;
		}
		JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("date/" + terminID + "/remove_user?token=" + User.getLoginToken() + "&id=" + userID));
		return response.getString("message").equals("User wurde aus Termin entfernt.");
	}
	
	public static List<application.model.Termin> getUserTermine() throws JSONException, IOException {
		JSONArray dates = new JSONObject(HttpWebRequest.sendGetRequest("dates?token=" + User.getLoginToken())).getJSONArray("dates");
		List<application.model.Termin> terminList = new ArrayList<application.model.Termin>();
		for (int i = 0; i < dates.length(); i++) {
			JSONObject curTermin = dates.getJSONObject(i);
			int id = curTermin.getInt("id");
			String title = curTermin.getString("titel");
			String description = curTermin.getString("beschreibung");
			String startDate = curTermin.getString("start_datum");
			String endDate = curTermin.getString("end_datum");
			terminList.add(new application.model.Termin(startDate, endDate, title, description, id));
		}
		return terminList;
	}
	
	public static List<application.model.Termin> getSharedTermine() throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("dates/shared?token=" + User.getLoginToken()));
		if (response.has("message") && response.getString("message").equals("Keine Termin.")) {
			return null;
		}
		JSONArray dates = response.getJSONArray("dates");
		List<application.model.Termin> terminList = new ArrayList<application.model.Termin>();
		for (int i = 0; i < dates.length(); i++) {
			JSONObject curTermin = dates.getJSONObject(i);
			int id = curTermin.getInt("id");
			String title = curTermin.getString("titel");
			String description = curTermin.getString("beschreibung");
			String startDate = curTermin.getString("start_datum");
			String endDate = curTermin.getString("end_datum");
			terminList.add(new application.model.Termin(startDate, endDate, title, description, id));
		}
		return terminList;
	}
	
	public static ObservableList<TerminRequest> getTerminRequests() throws JSONException, IOException {
		if (terminRequests == null) {
			terminRequests = FXCollections.observableArrayList();
			JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("date/requests?token=" + User.getLoginToken()));
			if (response.has("message") && response.getString("message").equals("Keine Termin anfragen."))
				return terminRequests;
			JSONArray requests = response.getJSONArray("requests");
			for (int i = 0; i < requests.length(); i++) {
				JSONObject curRequest = requests.getJSONObject(i);
				int requestID = curRequest.getInt("id");
				String title = curRequest.getString("titel");
				String description = curRequest.getString("beschreibung");
				String startDate = curRequest.getString("start_datum");
				String endDate = curRequest.getString("end_datum");
				application.model.Termin termin = new application.model.Termin(startDate, endDate, title, description, -1); // WE HAVE NO ID :(
				TerminRequest request = new TerminRequest(termin, requestID);
				terminRequests.add(request);
			}
		}
		return terminRequests;
	}
	
	public static boolean acceptRequest(int requestID) throws JSONException, IOException {
		JSONObject reponse = new JSONObject(HttpWebRequest.sendGetRequest("date/" + requestID + "/accept?token=" + User.getLoginToken()));
		return reponse.getString("message").equals("User ist jetzt Teilnehmer des Termins.");
	}
	
	public static boolean declineRequest(int requestID) throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("date/" + requestID + "/decline?token=" + User.getLoginToken()));
		return response.getString("message").equals("User ist jetzt kein Teilnehmer des Termins mehr.");
	}
}