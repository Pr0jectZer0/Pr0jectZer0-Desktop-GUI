package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ChatTest {

	private static int friendID = 14,
			chatID = 33;
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void getChatIDValid() {
		assertEquals(Chat.getChatroomID(friendID), chatID);
	}
	
	@Test
	public void getChatIDInvalid() {
		assertEquals(Chat.getChatroomID(-1), -1);
	}
	
	@Test
	public void getChatIDNotExisting() {
		assertEquals(Chat.getChatroomID(131231237), -1);
	}
	
	@Test
	public void sendMessageValid() {
		assertTrue(Chat.sendMessage(chatID, "JUNIT_TEST"));
	}
	
	@Test
	public void sendMessageNull() {
		assertFalse(Chat.sendMessage(chatID, null));
	}
	
	@Test
	public void sendMessageEmpty() {
		assertFalse(Chat.sendMessage(chatID, ""));
	}
	
	@Test
	public void getMessages() {
		
	}
}
