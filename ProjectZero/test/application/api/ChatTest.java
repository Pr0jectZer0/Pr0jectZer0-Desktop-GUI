package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import application.model.Message;

public class ChatTest {

	private static int friendID = 14,
			chatID = 33;
	private static String message = "JUNIT_TEST";
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
		assertTrue(Chat.sendMessage(chatID, message));
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
		Chat.sendMessage(chatID, message);
		List<Message> messages = Chat.getMessages(chatID);
		if (messages == null)
			fail();
		assertEquals(messages.get(messages.size() - 1).getMessage(), message);
	}
}