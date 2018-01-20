package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.model.Group;

public class GroupsTest {

	private static String name = "JUNIT_TEST",
			description = "JUNIT_TEST",
			message = "JUNIT_TEST_NACHRICHT";
	private static int userID = 14;
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void createGroupNull() {
		try {
			assertNull(Groups.createGroup(null, null));
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void createGroupEmpty() {
		try {
			assertNull(Groups.createGroup("", ""));
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void createGroup() {
		try {
			Group g = Groups.createGroup(name, description);
			assertNotNull(g);
			Groups.deleteGroup(g.getID());
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteGroup() {
		try {
			int id = Groups.createGroup(name, description).getID();
			assertTrue(Groups.deleteGroup(id));
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteGroupInvalid() {
		assertFalse(Groups.deleteGroup(-1));
	}
	
	@Test
	public void getAllGroups() {
		try {
			assertNotNull(Groups.getAllGroups());
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getGroupByIDInvalid() {
		try {
			assertNull(Groups.getGroupByID(-1));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getGroupByID() {
		try {
			int id = Groups.createGroup(name, description).getID();
			assertEquals(Groups.getGroupByID(id).getID(), id);
			Groups.deleteGroup(id);
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getUserGroups() {
		try {
			assertNotNull(Groups.getUserGroups());
		} catch(IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addUserToGroupInvalid() {
		try {
			assertFalse(Groups.addUserToGroup(-1, -1));
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addUserToGroup() {
		try {
		int id = Groups.createGroup(name, description).getID();
		assertTrue(Groups.addUserToGroup(userID, id));
		Groups.deleteUserFromGroup(userID, id);
		Groups.deleteGroup(id);
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteUserFromGroup() {
		try {
			int id = Groups.createGroup(name, description).getID();
			Groups.addUserToGroup(userID, id);
			assertTrue(Groups.deleteUserFromGroup(userID, id));
			Groups.deleteGroup(id);
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteUserFromGroupInvalid() {
		try {
			assertFalse(Groups.deleteUserFromGroup(-1, -1));
		} catch (IOException | JSONException e) {
			fail (e.getMessage());
		}
	}
	
	@Test
	public void addGroupNoteInvalid() {
		assertFalse(Groups.addGroupNote(-1, -1));
	}
	
	@Test
	public void addGroupNote() {
		try {
			int noteID = Notes.createNote("JUNIT_TEST_GROUPNOTE", "JUNIT_TEST_GROUPNOTE").getID();
			int groupID = Groups.createGroup(name, description).getID();
			assertTrue(Groups.addGroupNote(groupID, noteID));
			Notes.deleteNote(noteID);
			Groups.deleteGroup(groupID);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getGroupNotesInvalid() {
		try {
			assertNull(Groups.getGroupNotes(-1));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	/*
	@Test
	public void getGroupNotes() {
		try {
			int noteID = Notes.createNote("JUNIT_TEST_GROUPNOTE", "JUNIT_TEST_GROUPNOTE").getID();
			int groupID = Groups.createGroup(name, description).getID();
			System.out.println(noteID);
			System.out.println(groupID);
			Groups.addGroupNote(groupID, noteID);
			//assertEquals(Groups.getGroupNotes(groupID).get(0).getID(), noteID);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	*/
	
	@Test
	public void sendGroupMessage() {
		try {
			int groupID = Groups.createGroup(name, description).getID();
			assertTrue(Groups.sendGroupMessage(groupID, message));
			Groups.deleteGroup(groupID);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getGroupMessages() {
		try {
			int groupID = Groups.createGroup(name, description).getID();
			Groups.sendGroupMessage(groupID, message);
			assertEquals(message, Groups.getGroupMessages(groupID).get(0).getMessage());
			Groups.deleteGroup(groupID);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
}