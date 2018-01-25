package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
	
	public static String getTerminRequests() throws JSONException, IOException {
		//TODO: finish
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("date/requests?token=" + User.getLoginToken()));
		if (response.has("message") && response.getString("message").equals("Keine Termin anfragen."))
			return null;
		return null;
	}
	
	public static void acceptRequest(int requestID) throws JSONException, IOException {
		//TODO: finish
		JSONObject reponse = new JSONObject(HttpWebRequest.sendGetRequest("date/" + requestID + "/accept?token=" + User.getLoginToken()));
	}
	
	public static void declineRequest(int requestID) throws JSONException, IOException {
		//TODO: finish
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("date/" + requestID + "/decline?token=" + User.getLoginToken()));
	}
}