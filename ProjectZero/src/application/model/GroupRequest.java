package application.model;

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