package application.api;

import java.util.Calendar;

public class TimeCompare
{
	public static boolean before(Calendar c1, Calendar c2)
	{
		int c1Year = c1.get(Calendar.YEAR);
		int c1Month = c1.get(Calendar.MONTH);
		int c1Day = c1.get(Calendar.DAY_OF_MONTH);
		int c1Hour = c1.get(Calendar.HOUR_OF_DAY);
		int c1Minute = c1.get(Calendar.MINUTE);
		int c1Second = c1.get(Calendar.SECOND);

		int c2Year = c2.get(Calendar.YEAR);
		int c2Month = c2.get(Calendar.MONTH);
		int c2Day = c2.get(Calendar.DAY_OF_MONTH);
		int c2Hour = c2.get(Calendar.HOUR_OF_DAY);
		int c2Minute = c2.get(Calendar.MINUTE);
		int c2Second = c2.get(Calendar.SECOND);

		if (c1Year < c2Year)
		{
			return true;
		}
		else if (c1Year > c2Year)
		{
			return false;
		}
		else
		{
			if (c1Month > c2Month)
			{
				return false;
			}
			else if (c1Month < c2Month)
			{
				return true;
			}
			else
			{
				if (c1Day < c2Day)
				{
					return true;
				}
				else if (c1Day > c2Day)
				{
					return false;
				}
				else
				{
					if(c1Hour < c2Hour)
					{
						return true;
					}
					else if(c1Hour > c2Hour)
					{
						return false;
					}
					else
					{
						if(c1Minute < c2Minute)
						{
							return true;
						}
						else if(c1Minute > c2Minute)
						{
							return false;
						}
						else
						{
							if(c1Second < c2Second)
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
				}
			}

		}
	}
}
