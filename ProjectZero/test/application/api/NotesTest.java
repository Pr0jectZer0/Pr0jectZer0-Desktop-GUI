package application.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.model.Note;

public class NotesTest
{

	private final String title = "JUNIT_TEST", text = "JUNIT_TEST";
	private final int userID = DummyData.user2ID;

	@Before
	public void setUp()
	{
		User.login(DummyData.userEmail, DummyData.userPW);
	}

	@Test
	public void getNotesFromUser()
	{
		try
		{
			assertNotNull(Notes.getNotesFromUser());
		}
		catch (JSONException | IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void getNotesFromID()
	{
		try
		{
			int id = Notes.createNote(title, text).getID();
			assertNotNull(Notes.getNoteByID(id));
			Notes.deleteNote(id);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void createNote()
	{
		try
		{
			Note n = Notes.createNote(title, text);
			Notes.deleteNote(n.getID());
			assertNotNull(n);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void changeNote()
	{
		try
		{
			int id = Notes.createNote(title, text).getID();
			Note n = Notes.changeNote("JUNIT_TEST_CHANGED", "JUNIT_TEST_CHANGED", id);
			Notes.deleteNote(id);
			assertEquals(n.getText(), "JUNIT_TEST_CHANGED");
		}
		catch (Exception e)
		{
			fail(e.getMessage());

		}
	}

	@Test
	public void deleteNoteTrue()
	{
		try
		{
			int id = Notes.createNote(title, text).getID();
			assertTrue(Notes.deleteNote(id));
		}
		catch (JSONException | IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void deleteNoteDeleted()
	{
		try
		{
			int id = Notes.createNote(title, text).getID();
			Notes.deleteNote(id);
			try
			{
				Notes.getNoteByID(id);
			}
			catch (FileNotFoundException e)
			{
				assertTrue(true);
			}
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void addUserToNoteInvalid()
	{
		try
		{
			assertFalse(Notes.addUserToNote(-1, userID));
		}
		catch (JSONException | IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void addUserToNote()
	{
		try
		{
			int noteID = Notes.createNote(title, text).getID();
			assertTrue(Notes.addUserToNote(noteID, userID));
			Notes.deleteNote(noteID);
		}
		catch (JSONException | IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void deleteUserFromNote()
	{
		try
		{
			int noteID = Notes.createNote(title, text).getID();
			Notes.addUserToNote(noteID, userID);
			assertTrue(Notes.deleteUserFromNote(noteID, userID));
			Notes.deleteNote(noteID);
		}
		catch (JSONException | IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void delteteUserFromNoteInvalid()
	{
		try
		{
			assertFalse(Notes.deleteUserFromNote(-1, userID));
		}
		catch (JSONException | IOException e)
		{
			fail(e.getMessage());
		}
	}
}