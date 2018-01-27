package application.model;

public class Genre
{

	private String name;
	private int id;

	public Genre(String name, int id)
	{
		this.setName(name);
		this.setId(id);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
