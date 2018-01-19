package application.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.model.Note;

public class NotesTest {
	
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void getNotesFromUser() {
		try {
			System.out.println(Notes.getNotesFromUser().get(0).getID());
			assertNotNull(Notes.getNotesFromUser());
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getNotesFromID() {
		try {
			int id = Notes.getNotesFromUser().get(0).getID();
			assertNotNull(Notes.getNoteByID(id));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void createNote() {
		try {
			Note n = Notes.createNote("JUNIT_TEST", "JUNIT_TEST");
			Notes.deleteNote(n.getID());
			assertNotNull(n);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void changeNote() {
		try {
			
			int id = Notes.createNote("JUNIT_TEST_CHANGE", "JUNIT_TEST_CHANGE").getID();
			Note n = Notes.changeNote("JUNIT_TEST_CHANGED", "JUNIT_TEST_CHANGED", id);
			Notes.deleteNote(id);
			assertEquals(n.getText(), "JUNIT_TEST_CHANGED");
		} catch (Exception e) {
			fail(e.getMessage());
			
		}
	}
	
	@Test
	public void deleteNoteTrue() {
		try {
			int id = Notes.createNote("JUNIT_TEST", "JUNIT_TEST").getID();
			assertTrue(Notes.deleteNote(id));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteNoteDeleted() {
		try {
			int id = Notes.createNote("JUNIT_TEST", "JUNIT_TEST").getID();
			Notes.deleteNote(id);
			try {
				Notes.getNoteByID(id);
			} catch (FileNotFoundException e) {
				assertTrue(true);
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}