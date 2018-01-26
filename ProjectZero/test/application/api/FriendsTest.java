package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import application.api.Friends.FriendAddState;

public class FriendsTest {
	
	private static int friendID = DummyData.user2ID;
	
	@Before
	public void setUp() {
		User.login(DummyData.userEmail, DummyData.userPW);
	}
	
	@Test
	public void testAddFriendValid() {
		Friends.delete(friendID);
		assertEquals(Friends.add(friendID), Friends.FriendAddState.Success);
	}
	
	@Test
	public void testAddAlreadyFriends() {
		Friends.add(friendID);
		assertEquals(Friends.add(friendID), FriendAddState.AlreadyFriends);
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