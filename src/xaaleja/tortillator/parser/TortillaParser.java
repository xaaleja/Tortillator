package xaaleja.tortillator.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import xaaleja.tortillator.model.Tortilla;

public class TortillaParser 
{
	public static ArrayList<Tortilla> parseTortillas(JSONArray json) throws JSONException
	{
		ArrayList<Tortilla> tortillas = new ArrayList<Tortilla>();
		
		for (int i=0; i<json.length();i++)
		{
			Tortilla t = new Tortilla(json.getJSONObject(i).getInt("id"), 
					(float)json.getJSONObject(i).getDouble("price"), 
					(float)json.getJSONObject(i).getDouble("average"), 
					json.getJSONObject(i).getString("image"), 
					json.getJSONObject(i).getInt("id_bar"));
			tortillas.add(t);
		}
		return tortillas;
		
	}
	
	public static Tortilla parse(JSONObject json) throws JSONException
	{
			Tortilla t = new Tortilla(json.getInt("id"), 
					(float)json.getDouble("price"), 
					(float)json.getDouble("average"), 
					json.getString("image"), 
					json.getInt("id_bar"));
     
		return t;
	}
}
