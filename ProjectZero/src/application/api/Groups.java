package application.api;

import java.io.IOException;

import application.model.Group;
import application.model.HttpWebRequest;

public class Groups
{
	public static GroupCreateState createGroup(String name, String description)
	{
		String[][] parameter = {{"name",name},{"beschreibung",description}};
		try
		{
			HttpWebRequest.sendPostRequest("/group?token=" + application.api.User.getLoginToken(), parameter);
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
			HttpWebRequest.sendGetRequest("/group/"+id+"?token=" + application.api.User.getLoginToken());
			//TO DOOOO
			return null;
		}
		catch(IOException e)
		{
			//TO DOOOOO
			return null;
		}
	}
	
	
	public enum GroupCreateState {
		Success,
		Failure
	}

}
