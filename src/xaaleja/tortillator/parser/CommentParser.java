package xaaleja.tortillator.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import xaaleja.tortillator.model.Comment;
import xaaleja.tortillator.utils.Utils;

public class CommentParser 
{
	public static ArrayList<Comment> parseComments(JSONArray json) throws JSONException
	{
		ArrayList<Comment> comments = new ArrayList<Comment>();
		
		for (int i=0; i<json.length();i++)
		{
			/*Tortilla t = new Tortilla(json.getJSONObject(i).getInt("id"), 
					(float)json.getJSONObject(i).getDouble("price"), 
					(float)json.getJSONObject(i).getDouble("average"), 
					json.getJSONObject(i).getString("image"), 
					json.getJSONObject(i).getInt("id_bar"));
			tortillas.add(t);*/
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/ HH:mm:ss");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd - HH:mm");

			//Date d = new Date();
			Comment c = null;
			try {
				c = new Comment(json.getJSONObject(i).getInt("id"), 
						json.getJSONObject(i).getString("user"), 
						json.getJSONObject(i).getInt("id_tortilla"), 
						format.parse(Utils.cleanDate(json.getJSONObject(i).getString("datetime"))), 
						json.getJSONObject(i).getString("text"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			comments.add(c);
		}
		return comments;
		
	}
}
