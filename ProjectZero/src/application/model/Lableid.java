package application.model;

import javafx.scene.control.Label;

public class Lableid extends Label
{
	private int groupid;
	private boolean istest;
	
	public Lableid(int id)
	{
		groupid = id;
		istest = false;
	}
	public Lableid()
	{
		istest = true;
		setText("Neu hinzufügen");
		setStyle("-fx-text-fill: #0db518");
	}

	public int getGroupid()
	{
		return groupid;
	}

	public void setGroupid(int groupid)
	{
		this.groupid = groupid;
	}
	public boolean istest()
	{
		return istest;
	}
	
}
