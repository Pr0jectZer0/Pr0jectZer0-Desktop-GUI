package application.model;
/**
 * Das ist die Klasse, die die Daten von einer Gruppenanfrage beinhaltet
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class GroupRequest
{

	private int id;
	private Group group;

	public GroupRequest(int id, Group group)
	{
		this.id = id;
		this.group = group;
	}

	public int getID()
	{
		return id;
	}

	public Group getGroup()
	{
		return group;
	}
}