package xaaleja.tortillator.parser;

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
}
