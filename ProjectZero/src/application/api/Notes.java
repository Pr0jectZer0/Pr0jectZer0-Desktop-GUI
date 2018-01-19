package application.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Notes {

	private static ObservableList<Note> userNotes = null;
	public static ObservableList<Note> getNotesFromUser() throws JSONException, IOException {
		if (userNotes == null) {
			JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("notes?token=" + application.api.User.getLoginToken()));
			JSONArray noteArr = response.getJSONArray("notes");
			userNotes = FXCollections.observableArrayList();
			for (int i = 0; i < noteArr.length(); i++) {
				JSONObject curNote = noteArr.getJSONObject(i);
				String title = curNote.getString("titel");
				String text = curNote.getString("text");
				int id = curNote.getInt("id");
				int userID = 	curNote.getInt("id_user");
				userNotes.add(new Note(title, text, userID, id));
			}
		}
		return userNotes;
	}
	
	public static Note createNote(String title, String text) throws JSONException, IOException {
		String[][] parameter = {{"titel", title}, {"text", text}};
		JSONObject response = new JSONObject(HttpWebRequest.sendPostRequest("note?token=" + application.api.User.getLoginToken(), parameter));
		int userID = response.getInt("id_user");
		int id = response.getInt("id");
		String titleGot = response.getString("titel");
		String textGot = response.getString("text");
		return new Note(titleGot, textGot, userID, id);
	}
	
	public static Note getNoteByID(int id) throws JSONException, IOException {
		JSONObject response = new JSONObject(HttpWebRequest.sendGetRequest("note/" + id + "?token=" + application.api.User.getLoginToken()));
		if (response.has("message"))
			throw new JSONException("Notiz-ID falsch!");
		JSONObject note = response.getJSONObject("note");
		int idgot = note.getInt("id");
		int userID = note.getInt("id_user");
		String title = note.getString("titel");
		String text = note.getString("text");
		return new Note(title, text, userID, idgot);
	}
	
	public static Note changeNote(String title, String text, int id) throws JSONException, IOException {
		String[][] parameter = {{"titel", title}, {"text", text}};
		JSONObject response = new JSONObject(HttpWebRequest.sendPutRequest("note/" + id + "?token=" + application.api.User.getLoginToken(), parameter));
		if (response.has("message"))
			throw new JSONException("Notiz-ID falsch!");
		JSONObject note = response.getJSONObject("note");
		int userID = note.getInt("id_user");
		int idgot = note.getInt("id");
		String titleGot = note.getString("titel");
		String textGot = note.getString("text");
		return new Note(titleGot, textGot, userID, idgot);
	}
	
	public static boolean deleteNote(int id) {
		try {
			JSONObject response = new JSONObject(HttpWebRequest.sendDeleteRequest("note/" + id + "?token=" + application.api.User.getLoginToken()));
			String message = response.getString("message");
			return message.equals("Notiz wurde gelöscht.");
		} catch (JSONException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}