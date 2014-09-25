package xaaleja.tortillator.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xaaleja.tortillator.model.Bar;

public class BarParser 
{
	public static Bar parse(JSONObject json) throws JSONException
	{
		Bar bar = new Bar(json.getInt("id"), 
				json.getString("name"), 
				json.getString("address"), 
				json.getString("city"), 
				json.getString("province"), 
				json.getString("schedule"), 
				(float)json.getDouble("latitude"), 
				(float)json.getDouble("longitude"));
     
		return bar;
	}
	
	public static ArrayList<Bar> parseBars(JSONArray json) throws JSONException
	{
		ArrayList<Bar> bars = new ArrayList<Bar>();
		
		for (int i=0; i<json.length();i++)
		{
			Bar b = new Bar(json.getJSONObject(i).getInt("id"), 
					json.getJSONObject(i).getString("name"), 
					json.getJSONObject(i).getString("address"), 
					json.getJSONObject(i).getString("city"), 
					json.getJSONObject(i).getString("province"), 
					json.getJSONObject(i).getString("schedule"), 
					(float)json.getJSONObject(i).getDouble("latitude"), 
					(float)json.getJSONObject(i).getDouble("longitude"));
			bars.add(b);
		}
		return bars;
		
	}
}
