package application.api;

import java.io.IOException;
import java.util.List;

import application.model.Group;
import application.model.HttpWebRequest;

public class Groups
{
	public static GroupCreateState createGroup(String name, String description)
	{
		String[][] parameter = {{"name",name},{"beschreibung",description}};
		try
		{
			HttpWebRequest.sendPostRequest("group?token=" + application.api.User.getLoginToken(), parameter);
			return GroupCreateState.Success;
		}
		catch(IOException e)
		{
			return GroupCreateState.Failure;
		}

	}
	public static Group getGroupByID(int id)
	{
		try
		{
			System.out.println(HttpWebRequest.sendGetRequest("group/"+id/*+"?token=" + application.api.User.getLoginToken()*/));
			//TO DOOOO
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			//TO DOOOO
			return null;
		}
	}
	public static List<Group> getAllGroups()
	{
		try
		{
			System.out.println(HttpWebRequest.sendGetRequest("groups"));
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public enum GroupCreateState {
		Success,
		Failure
	}

}
