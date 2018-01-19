package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.api.Friends.FriendAddState;
import application.api.Friends.FriendDeleteState;

public class FriendsTest {
	
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void testAddFriendValid() {
		Friends.delete(14);
		Friends.FriendAddState a = Friends.add(14);
		assertEquals(a, Friends.FriendAddState.Success);
	}
	
	@Test
	public void testAddAlreadyFriends() {
		Friends.add(14);
		assertEquals(FriendAddState.AlreadyFriends, Friends.add(14));
	}
	
	@Test
	public void testDeleteValid() {
		Friends.add(14);
		assertEquals(Friends.delete(14), FriendDeleteState.Success);
	}
	
	@Test
	public void testDeleteNotFriends() {
		Friends.delete(14);
		assertEquals(Friends.delete(14), FriendDeleteState.NotFriends);
	}
	
	@Test
	public void testGetFriends() {
		try {
			assertNotNull(Friends.getFriends().get(0));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetFriendsAdded() {
		Friends.add(14);
		try {
			boolean found = false;
			for (application.model.User f : Friends.getFriends()) {
				if (f.getId() == 14) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
}