package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.api.Friends.FriendAddState;
import application.api.Friends.FriendDeleteState;

public class FriendsTest {
	
	private static int friendID = 14;
	
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void testAddFriendValid() {
		Friends.delete(friendID);
		Friends.FriendAddState a = Friends.add(friendID);
		assertEquals(a, Friends.FriendAddState.Success);
	}
	
	@Test
	public void testAddAlreadyFriends() {
		Friends.add(friendID);
		assertEquals(FriendAddState.AlreadyFriends, Friends.add(14));
	}
	
	@Test
	public void testDeleteValid() {
		Friends.add(friendID);
		assertEquals(Friends.delete(friendID), FriendDeleteState.Success);
	}
	
	@Test
	public void testDeleteNotFriends() {
		Friends.delete(friendID);
		assertEquals(Friends.delete(friendID), FriendDeleteState.NotFriends);
	}
	
	@Test
	public void testGetFriends() {
		Friends.add(friendID);
		try {
			assertNotNull(Friends.getFriends());
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
}