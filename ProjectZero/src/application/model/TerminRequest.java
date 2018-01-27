package application.model;
/**
 * Das ist die Klasse, die die Daten von einer Terminanfrage beinhaltet
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class TerminRequest
{

	private Termin termin;
	private int requestID;

	public TerminRequest(Termin termin, int requestID)
	{
		this.termin = termin;
		this.requestID = requestID;
	}

	public Termin getTermin()
	{
		return termin;
	}

	public int requestID()
	{
		return requestID;
	}
}