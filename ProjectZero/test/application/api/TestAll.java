package application.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserTest.class, UsersTest.class, UserGamesTest.class, NotesTest.class, GroupsTest.class,
		GamesTest.class, FriendsTest.class, ChatTest.class })
public class TestAll
{

}
