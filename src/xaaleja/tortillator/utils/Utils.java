package xaaleja.tortillator.utils;

import android.util.Log;

public class Utils 
{
	public static String clean(String s)
	{
		String sub = s.substring(1, s.length()-1);
		return sub;
	}
	public static String cleanDate(String date)
	{
		String sub = date.substring(0, 19);
		sub = sub.substring(0, 10) + " - " + sub.substring(11, 16);
		Log.i("DATE COMMENT", sub);
		return sub;
	}
}
