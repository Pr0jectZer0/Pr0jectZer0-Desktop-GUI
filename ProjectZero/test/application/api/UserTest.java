package application.api;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
	
	@Test
	public void getLoginTokenTestBeforeLogin() {
		assertNull(User.getLoginToken());
	}
	
	@Test
	public void loginTestEmpty() {
		assertEquals(User.login("", ""), User.LoginState.ServerError);
	}
	
	@Test
	public void loginTestNull() {
		assertEquals(User.login(null, null), User.LoginState.ServerError);
	}
	
	@Test
	public void loginTestInvalid() {
		assertEquals(User.login("randommüll", "irgendwas"), User.LoginState.WrongData);
	}
	
	@Test
	public void loginTestInvalidPassword() {
		assertEquals(User.login("peter@peter.com", "FALSCHESPW"), User.LoginState.WrongData);
	}
}
