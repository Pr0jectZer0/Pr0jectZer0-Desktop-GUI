package application.model;
/**
 * Das ist die Klasse, die die Daten von einem Publisher beinhaltet
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class Publisher
{

	private String name;
	private int id;

	public Publisher(String name, int id)
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
