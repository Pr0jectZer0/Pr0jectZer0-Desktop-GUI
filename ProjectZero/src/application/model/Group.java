package application.model;

import java.util.List;

public class Group
{
	private String name, description;
	private int id;
	private List<User> members;
	private User admin;

	public Group(String name, String description, int id, List<User> members, User admin)
	{
		this.name = name;
		this.description = description;
		this.id = id;
		this.members = members;
		this.admin = admin;
	}

	public Group(String name, String description, int id)
	{
		this.name = name;
		this.description = description;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public int getID()
	{
		return id;
	}

	public List<User> getMembers()
	{
		return members;
	}

	public User getAdmin()
	{
		return admin;
	}
}
