package application.api;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
	
	@Test
	public void getLoginTokenTestBeforeLogin() {
		User.login(null, null);
		assertNull(User.getLoginToken());
	}
	
	@Test
	public void getLoginTokenLoggedIn() {
		User.login("peter@peter.com", "Peter123");
		assertNotNull(User.getLoginToken());
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
	
	@Test
	public void loginValid() {
		assertEquals(User.login("peter@peter.com", "Peter123"), User.LoginState.Success);
	}
	@Test
	public void registerEmpty() {
		assertEquals(User.register("", "", ""), User.RegisterState.WrongData);
	}
	
	@Test
	public void registerNull() {
		assertEquals(User.register(null, null, null), User.RegisterState.WrongData);
	}
	
	@Test
	public void registerInvalid() {
		assertEquals(User.register("a", "aaaa", "ansdanbsda"), User.RegisterState.WrongData);
	}
	
	//Cannot registerValid because we can't delete and it will fail after done once
	//@Test
	//public void registerValid() {
	//	assertEquals(User.register("AAAAAAAAAAAAAAAAAAAAA", "aaaa@aaaaaaaaaa.com", "AAAAAAAAAAAAAAAAAAAAA"), User.RegisterState.Success);
	//}
	
	@Test
	public void registerValidAgain() {
		assertEquals(User.register("AAAAAAAAAAAAAAAAAAAAA", "aaaa@aaaaaaaaaa.com", "AAAAAAAAAAAAAAAAAAAAA"), User.RegisterState.WrongData);
	}
	
	@Test
	public void deleteUser() {
		assertFalse(User.delete(-1));
	}
	

}