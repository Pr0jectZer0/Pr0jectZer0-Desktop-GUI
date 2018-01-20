package application.api;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;

public class Termin {
	
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
	
	public static application.model.Termin updateTermin(int id, String title, String description, String startDate, String endDate) throws JSONException, IOException {
		if (id < 0 || title == null || title.isEmpty() || description == null || description.isEmpty() || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			return null;
		}
		String[][] parameter = {{ "titel", title }, { "beschreibung", description}, {"start_datum", startDate}, {"end_datum", endDate} };
		JSONObject response = new JSONObject(HttpWebRequest.sendPutRequest("date/" + id + "?token=" + User.getLoginToken(), parameter));
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
}