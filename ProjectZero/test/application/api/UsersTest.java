package application.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class UsersTest
{

	@Before
	public void setUp()
	{
		User.login(DummyData.userEmail, DummyData.userPW);
	}

	@Test
	public void getAllUsers()
	{
		try
		{
			assertNotNull(Users.getUsers());
		}
		catch (JSONException | IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void getNoFriends()
	{
		try
		{
			assertNotNull(Users.getNoFriends());
		}
		catch (JSONException | IOException e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void getUserByID()
	{
		assertEquals(Users.getUserByID(21).getName(), "PeterPeter");
	}
}