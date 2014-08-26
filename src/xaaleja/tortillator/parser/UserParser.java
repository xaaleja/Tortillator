package xaaleja.tortillator.parser;

import org.json.JSONException;
import org.json.JSONObject;

import xaaleja.tortillator.model.User;

public class UserParser 
{
	public static User parse(JSONObject json) throws JSONException
	{
		User user = new User();

		String uname = json.getString("username");
		user.setUsername(uname);
		String pass = json.getString("password");
		user.setPassword(pass);
		String email = json.getString("email");
        user.setEmail(email);
		String city = json.getString("city");
        user.setCity(city);
        
        return user;
	}
}
