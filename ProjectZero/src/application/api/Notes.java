package application.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.model.HttpWebRequest;
import application.model.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Das ist die Api-Klasse für die Notizen
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class Notes
{

	private Notes()
	{
	}

	private static ObservableList<Note> userNotes = null;
  /**
   * Gibt alle Notizen des Benutzers zurück
   * @return Liste mit Notizen des Benutzers
   * @throws JSONException
   * @throws IOException
   */
	public static ObservableList<Note> getNotesFromUser() throws JSONException, IOException
	{
		if (userNotes == null)
		{
			JSONObject response = new JSONObject(
					HttpWebRequest.sendGetRequest("notes?token=" + application.api.User.getLoginToken()));
			JSONArray noteArr = response.getJSONArray("notes");
			userNotes = FXCollections.observableArrayList();
			for (int i = 0; i < noteArr.length(); i++)
			{
				JSONObject curNote = noteArr.getJSONObject(i);
				String title = curNote.getString("titel");
				String text = curNote.getString("text");
				int id = curNote.getInt("id");
				int userID = curNote.getInt("id_user");
				userNotes.add(new Note(title, text, userID, id));
			}
		}
		return userNotes;
	}
/**
 * Methode zum erstellen einer Notiz
 * @param title Titel der Notiz
 * @param text Inhalt der Notiz
 * @return Die erstellte Notiz
 */
	public static Note createNote(String title, String text) throws JSONException, IOException
	{
		if (title == null || text == null || title.isEmpty() || text.isEmpty())
		{
			return null;
		}
		String[][] parameter = { { "titel", title }, { "text", text } };
		JSONObject response = new JSONObject(
				HttpWebRequest.sendPostRequest("note?token=" + application.api.User.getLoginToken(), parameter));
		int userID = response.getInt("id_user");
		int id = response.getInt("id");
		String titleGot = response.getString("titel");
		String textGot = response.getString("text");
		return new Note(titleGot, textGot, userID, id);
	}
/**
 * Gibt die Notiz einer ID zurück
 * @param id ID der Notiz
 * @return die Notiz
 */
	public static Note getNoteByID(int id) throws JSONException, IOException
	{
		if (id < 0)
		{
			return null;
		}
		JSONObject response = new JSONObject(
				HttpWebRequest.sendGetRequest("note/" + id + "?token=" + application.api.User.getLoginToken()));
		if (response.has("message"))
			throw new JSONException("Notiz-ID falsch!");
		JSONObject note = response.getJSONObject("note");
		int idgot = note.getInt("id");
		int userID = note.getInt("id_user");
		String title = note.getString("titel");
		String text = note.getString("text");
		return new Note(title, text, userID, idgot);
	}
/**
 * Methode zum ändern einer Notiz
 * @param title neuer Titel
 * @param text neuer Inhalt
 * @param id ID der Notiz
 * @return die neue Notiz
 */
	public static Note changeNote(String title, String text, int id) throws JSONException, IOException
	{
		if (title == null || text == null || id < 0 || title.isEmpty() || text.isEmpty())
		{
			return null;
		}
		String[][] parameter = { { "titel", title }, { "text", text } };
		JSONObject response = new JSONObject(HttpWebRequest
				.sendPutRequest("note/" + id + "?token=" + application.api.User.getLoginToken(), parameter));
		if (response.has("message"))
			throw new JSONException("Notiz-ID falsch!");
		JSONObject note = response.getJSONObject("note");
		int userID = note.getInt("id_user");
		int idgot = note.getInt("id");
		String titleGot = note.getString("titel");
		String textGot = note.getString("text");
		return new Note(titleGot, textGot, userID, idgot);
	}
/**
 * Methode zum löschen einer Notiz
 * @param id ID der Notiz
 * @return Rückmeldung, ob es geklappt hat(true = geklappt)
 */
	public static boolean deleteNote(int id)
	{
		if (id < 0)
		{
			return false;
		}
		try
		{
			JSONObject response = new JSONObject(
					HttpWebRequest.sendDeleteRequest("note/" + id + "?token=" + application.api.User.getLoginToken()));
			String message = response.getString("message");
			return message.equals("Notiz wurde gel�scht.");
		}
		catch (JSONException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}
	}
/**
 * Methode zum hinzufügen eines Benutzers zu einer Notiz
 * @param noteID ID der Notiz
 * @param userID ID des Benutzers
 * @return Rückmeldung, ob es geklappt hat(true = geklappt)
 */
	public static boolean addUserToNote(int noteID, int userID) throws JSONException, IOException
	{
		if (userID < 0 || noteID < 0)
		{
			return false;
		}
		String[][] parameter = { { "id", Integer.toString(userID) } };
		JSONObject response = new JSONObject(HttpWebRequest
				.sendPostRequest("note/" + noteID + "/add_user?token=" + User.getLoginToken(), parameter));
		return response.getString("message").equals("Notiz anfrage wurde an User gesendet.");
	}
/**
 * Methode um einen Benutzer von einer Notiz zu entfernen
 * @param noteID ID der Notiz 
 * @param userID ID von dem Benutzer der entfernt werden soll
 * @return Rückmeldung, ob es geklappt hat(true = geklappt)
 */
	public static boolean deleteUserFromNote(int noteID, int userID) throws JSONException, IOException
	{
		if (userID < 0 || noteID < 0)
		{
			return false;
		}
		JSONObject response = new JSONObject(HttpWebRequest
				.sendPostRequest("note/" + noteID + "/remove_user?token=" + User.getLoginToken() + "&id=" + userID));
		return response.getString("message").equals("User wurde aus Notiz entfernt.");
	}
/**
 * Alle Notizanfragen von dem eingeloggten Benutzer
 * @return Liste mit Notizanfragen
 */
	public static List<Note> getNoteRequests() throws JSONException, IOException
	{
		JSONObject response = new JSONObject(
				HttpWebRequest.sendGetRequest("note/requests?token=" + User.getLoginToken()));
		if (response.has("message") && response.getString("message").equals("Keine Notiz anfragen."))
			return null;
		List<Note> noteList = new ArrayList<Note>();
		return null;
	}
}