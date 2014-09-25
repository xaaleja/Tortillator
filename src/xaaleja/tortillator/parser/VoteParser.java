package xaaleja.tortillator.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import xaaleja.tortillator.model.Vote;
import xaaleja.tortillator.utils.Utils;

public class VoteParser 
{
	public static ArrayList<Vote> parseVotes(JSONArray json) throws JSONException
	{
		ArrayList<Vote> votes = new ArrayList<Vote>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd - HH:mm");

		
		for (int i=0; i<json.length();i++)
		{
			Vote v = null;
			try {
				v = new Vote(json.getJSONObject(i).getString("user"), 
						json.getJSONObject(i).getInt("id_tortilla"), 
						json.getJSONObject(i).getInt("rating"), 
						format.parse(Utils.cleanDate(json.getJSONObject(i).getString("datetime"))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			votes.add(v);
		}
		return votes;
		
	}
}
