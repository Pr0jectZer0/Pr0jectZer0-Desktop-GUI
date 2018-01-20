package application.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class TerminTest {
	
	private final String startDate = "2018-01-30",
							endDate = "2018-01-31",
							title = "JUNIT_TEST_TITLE",
							description = "JUNIT_TEST_TITLE";
	@Before
	public void setUp() {
		User.login("peter@peter.com", "Peter123");
	}
	
	@Test
	public void createTerminNull() {
		try {
			assertNull(Termin.createTermin(null, null, null, null));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void createTerminEmpty() {
		try {
			assertNull(Termin.createTermin("", "", "", ""));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void createTermin() {
		try {
			application.model.Termin termin = Termin.createTermin(title, description, startDate, endDate);
			assertEquals(termin.getTitle(), title);
			Termin.deleteTermin(termin.getID());
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteTerminInvalid() {
		try {
			assertFalse(Termin.deleteTermin(-1));
		} catch(JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteTermin() {
		try {
			int id = Termin.createTermin(title, description, startDate, endDate).getID();
			assertTrue(Termin.deleteTermin(id));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateTerminNull() {
		try {
			assertNull(Termin.updateTermin(5, null, null, null, null));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateTerminEmpty() {
		try {
			assertNull(Termin.updateTermin(5, "", "", "", ""));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateTerminInvalid() {
		try {
			assertNull(Termin.updateTermin(-1, title, description, startDate, endDate));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateTermin() {
		try {
			int id = Termin.createTermin(title, description, startDate, endDate).getID();
			assertEquals(Termin.updateTermin(id, title, description, startDate, endDate).getTitle(), title);
			Termin.deleteTermin(id);
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getTerminByIDInvalid() {
		try {
			assertNull(Termin.getTerminByID(-1));
		} catch (JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getTerminByID() {
		try {
			int id = Termin.createTermin(title, description, startDate, endDate).getID();
			assertEquals(title, Termin.getTerminByID(id).getTitle());
			Termin.deleteTermin(id);
		} catch(JSONException | IOException e) {
			fail(e.getMessage());
		}
	}
}