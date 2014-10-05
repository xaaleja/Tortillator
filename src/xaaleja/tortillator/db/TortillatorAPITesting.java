package xaaleja.tortillator.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.Comment;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.model.Vote;
import xaaleja.tortillator.parser.BarParser;
import xaaleja.tortillator.parser.CommentParser;
import xaaleja.tortillator.parser.TortillaParser;
import xaaleja.tortillator.parser.UserParser;
import xaaleja.tortillator.parser.VoteParser;
import xaaleja.tortillator.utils.APIRoutes;
import xaaleja.tortillator.utils.Utils;
import android.util.Log;

public class TortillatorAPITesting 
{
	private static TortillatorAPITesting apiInstance = null;
	
	HttpClient client =new DefaultHttpClient();
	HttpContext context = new BasicHttpContext();
	
	private static InputStream is = null;

	
	private TortillatorAPITesting() 
	{
	}
	
	public static TortillatorAPITesting getInstance() 
	{
		if(apiInstance == null) 
		{
			apiInstance = new TortillatorAPITesting();
		}
		return apiInstance;
	}
	
	public User loginUser(String username, String password)
	{
		User user = new User();
		String route = APIRoutes.GET_OR_PUT_USER+username+"/usernames/"+password+"/password.json";
			
		HttpGet httpget = new HttpGet(route);
		String result=null;
		try 
		{
			HttpResponse response = client.execute(httpget,context);			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
			JSONObject json = new JSONObject(result);
			user = UserParser.parse(json);
		} 
		catch (Exception e) 
		{
			Log.e("EXCEPTION", e.toString());
			user = null;
		}		
		
		return user;
	}
	
	public boolean existUsername(String username)
	{
		boolean b = false;
		String route = APIRoutes.USER_EXISTS+username+"/exist.json";
		HttpGet httpget = new HttpGet(route);

		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			HttpEntity entity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==200)
				b=true;
			String result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) 
		{
			e.printStackTrace();
		} 

		return b;
	}
	public boolean existEmail(String email)
	{
		boolean b = false;
		String route = APIRoutes.EMAIL_EXISTS+email+"/email/exist.json";
		HttpGet httpget = new HttpGet(route);

		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			HttpEntity entity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==200)
				b=true;
			String result = EntityUtils.toString(entity, "UTF-8");

		} catch (Exception e) 
		{
			e.printStackTrace();
		} 

		
		return b;
	}
	
	public boolean newUser(User user)
	{	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", user.getUsername()));
		params.add(new BasicNameValuePair("password", user.getPassword()));
		params.add(new BasicNameValuePair("email", user.getEmail()));
		params.add(new BasicNameValuePair("city", user.getCity()));
	
		String route = APIRoutes.GET_OR_POST_USERS;

		String result = this.post(params, route);
		Log.d("NEW USER", result);
		return true;
	}
	
	public boolean newBar(Bar bar)
	{	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", bar.getName()));
		params.add(new BasicNameValuePair("city", bar.getCity()));
		params.add(new BasicNameValuePair("province", bar.getProvince()));
		params.add(new BasicNameValuePair("address", bar.getAddress()));
		params.add(new BasicNameValuePair("schedule", bar.getSchedule()));
		params.add(new BasicNameValuePair("latitude", Float.toString(bar.getLatitude())));
		params.add(new BasicNameValuePair("longitude", Float.toString(bar.getLongitude())));

		String route = APIRoutes.GET_OR_POST_BARS;
		String result = this.post(params, route);
		Log.i("NEW BAR", result);
		return true;
	}
	
	public String newTortilla(Tortilla tortilla)
	{	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("price", Float.toString(tortilla.getPrice())));
		params.add(new BasicNameValuePair("average", Float.toString(tortilla.getAverage())));
		params.add(new BasicNameValuePair("image", tortilla.getImage()));
		params.add(new BasicNameValuePair("idBar", Integer.toString(tortilla.getId_bar())));
	
		String route = APIRoutes.GET_OR_POST_TORTILLAS;

		return this.post(params, route);
	}
	public String newVote(Vote vote)
	{	
		//The tortilla's average has to be updated
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("idTortilla", Integer.toString(vote.getId_tortilla())));
		params.add(new BasicNameValuePair("user", vote.getUsername()));
		params.add(new BasicNameValuePair("rating", Integer.toString(vote.getVote())));
	
		String route = APIRoutes.GET_OR_POST_VOTES;

		return this.post(params, route);
	}
	public String newComment(Comment comment)
	{	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("idTortilla", Integer.toString(comment.getId_tortilla())));
		params.add(new BasicNameValuePair("user", comment.getUsername()));
		params.add(new BasicNameValuePair("text", comment.getText()));
	
		String route = APIRoutes.GET_OR_POST_COMMENTS;

		return this.post(params, route);
	}
	
	public String getBarName(int id)
	{
		String barName="";
		String route = APIRoutes.GET_OR_PUT_BAR+id+"/name.json";
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");			
			barName = Utils.clean(result);
			} catch (Exception e) 
		{
			Log.e("ERROR", e.toString());
			e.printStackTrace();
		}
		return barName;
	}
	
	public ArrayList<Tortilla> getUsersTortillas(String username)
	{
		ArrayList<Tortilla> tortillas = new ArrayList<Tortilla>();
		String route = APIRoutes.GET_TORTILLAS_USER+username+"/user.json";
		
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONArray json= new JSONArray(result);
				tortillas = TortillaParser.parseTortillas(json);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		return tortillas;
	}
	public int getUserRating(String username, int id_tortilla)
	{
		int rating = -1;
		String route = APIRoutes.GET_OR_PUT_VOTE+id_tortilla+"-"+username+"/user/rating.json";
		
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				rating = Integer.parseInt(result);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		return rating;
	}
	public Bar getBar(int id)
	{
		Bar bar = new Bar();
		String route = APIRoutes.GET_OR_PUT_BAR+id+".json";
		
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			Log.i("JSON", ""+response.getStatusLine().getStatusCode());
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			JSONObject json= new JSONObject(result);
			bar = BarParser.parse(json);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		return bar;
	}
	public Tortilla getTortilla(int id)
	{
		Tortilla tortilla = new Tortilla();
		String route = APIRoutes.GET_OR_PUT_TORTILLA+id+".json";
		
		HttpGet httpget = new HttpGet(route);
		String result = null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			Log.i("JSON", ""+response.getStatusLine().getStatusCode());
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			JSONObject json= new JSONObject(result);
			tortilla = TortillaParser.parse(json);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return tortilla;
	}
	public ArrayList<Comment> getComments(int id_tortilla)
	{		
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String route = APIRoutes.GET_OR_PUT_COMMENT + id_tortilla + "/tortilla.json";
		
		HttpGet httpget = new HttpGet(route);
		String result = null;
		
		HttpResponse response;
		try
		{
			response = client.execute(httpget, context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONArray json = new JSONArray(result);
				comments = CommentParser.parseComments(json);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return comments;
	}
	public void updateTortillaAverage(int id_tortilla)
	{
		ArrayList<Vote> votes = this.getTortillasVotes(id_tortilla);
		int numVotes = this.getNumVotes(id_tortilla);
		float avg = 0;
		for(Vote v: votes)
		{
			avg = avg + v.getVote();
		}
		
		Tortilla tortilla = this.getTortilla(id_tortilla);
		tortilla.setAverage(avg/numVotes);
		Log.i("UPDATE AVERAGE", ""+avg/numVotes);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("price", Float.toString(tortilla.getPrice())));
		params.add(new BasicNameValuePair("average", Float.toString(tortilla.getAverage())));
		params.add(new BasicNameValuePair("image", tortilla.getImage()));
		params.add(new BasicNameValuePair("idBar", Integer.toString(tortilla.getId_bar())));

			
	
		String route = APIRoutes.GET_OR_PUT_TORTILLA+id_tortilla+".json";
		this.put(params, route);

		//return this.put(params, route);
	}
	public void updateVote(Vote vote)
	{
		//The tortilla's average has to be updated
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("idTortilla", Integer.toString(vote.getId_tortilla())));
		params.add(new BasicNameValuePair("user", vote.getUsername()));
		params.add(new BasicNameValuePair("rating", Integer.toString(vote.getVote())));
	

		String route = APIRoutes.GET_OR_PUT_VOTE+vote.getId_tortilla() +"-"+vote.getUsername()+".json";
		this.put(params, route);
		//return this.put(params, route);
	}
	public int getNumVotes(int id_tortilla)
	{
		int numVotes = 0;
		String route = APIRoutes.GET_OR_PUT_VOTE+id_tortilla+"/number.json";
		
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			String num = Utils.clean(result);
			numVotes = Integer.parseInt(num);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		return numVotes;
	}
	public ArrayList<Tortilla> getRanking()
	{
		ArrayList<Tortilla> tortillas = new ArrayList<Tortilla>();
		String route = APIRoutes.GET_TORTILLAS_RANKING;
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONArray json= new JSONArray(result);
				tortillas = TortillaParser.parseTortillas(json);
			}
		} catch (Exception e) 
		{
			Log.e("ERROR RANKING", e.toString());
			e.printStackTrace();
		}	
		
		return tortillas;
	}
	public ArrayList<Tortilla> getRecommendations(String username)
	{
		ArrayList<Tortilla> tortillas = new ArrayList<Tortilla>();
		String route = APIRoutes.GET_OR_PUT_TORTILLA+username+"/recommendations.json";
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONArray json= new JSONArray(result);
				tortillas = TortillaParser.parseTortillas(json);
			}
		} catch (Exception e) 
		{
			Log.e("ERROR RECOMMENDS", e.toString());
			e.printStackTrace();
		}	
		
		return tortillas;
	}
	public ArrayList<Bar> getBarsNearsLocation(LatLng location)	
	{		
		ArrayList<Bar> bars = new ArrayList<Bar>();
		String route = APIRoutes.GET_OR_PUT_BAR+location.latitude+"/latitudes/"+location.longitude+"/longitude.json";
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONArray json= new JSONArray(result);
				bars = BarParser.parseBars(json);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		return bars;
	}
	public Bar getBarByName(String barName)
	{
		barName = barName.replace(" ", "");
		Bar bar = new Bar();
		String route = APIRoutes.GET_OR_PUT_BAR+barName+"/by/slug.json";
		
		HttpGet httpget = new HttpGet(route);
		String result = null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONObject json= new JSONObject(result);
				bar = BarParser.parse(json);
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return bar;
	}
	public Tortilla getTortillaByBarId(int id)
	{
		Tortilla tortilla = new Tortilla();
		String route = APIRoutes.GET_OR_PUT_TORTILLA+id+"/by/bar.json";
		
		HttpGet httpget = new HttpGet(route);
		String result = null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONObject json= new JSONObject(result);
				tortilla = TortillaParser.parse(json);
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return tortilla;
	}
	public ArrayList<Vote> getTortillasVotes(int id)
	{
		ArrayList<Vote> votes = new ArrayList<Vote>();
		String route = APIRoutes.GET_OR_PUT_VOTE+id+"/tortillas.json";
		HttpGet httpget = new HttpGet(route);
		String result=null;
		
		HttpResponse response;
		try {
			response = client.execute(httpget,context);
			
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");			
			if(response.getStatusLine().getStatusCode()!=404)
			{
				JSONArray json= new JSONArray(result);
				votes = VoteParser.parseVotes(json);
			}
		} catch (Exception e) 
		{
			Log.e("ERROR VOTES", e.toString());
			e.printStackTrace();
		}	
		
		return votes;		
	}
	private String post(List<NameValuePair> params, String route)
	{
		HttpPost httpPost = new HttpPost(route);
		HttpResponse response = null;
		
		try
		{	
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return this.getResponse();
	}
	private void put(List<NameValuePair> params, String route)
	{
		HttpPut httpPut = new HttpPut(route);

		try {
			httpPut.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPut);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try
		{	
			httpPut.setEntity(new UrlEncodedFormEntity(params));
			response = client.execute(httpPut);
			//HttpEntity entity = response.getEntity();
			//is = entity.getContent();
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return this.getResponse();*/ catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String getResponse()
	{
		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
		} catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}		
		return sb.toString();
	}
}
