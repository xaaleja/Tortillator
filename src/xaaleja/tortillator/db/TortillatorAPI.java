package xaaleja.tortillator.db;

import java.util.ArrayList;
import java.util.Date;

import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.Comment;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.model.Vote;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

public class TortillatorAPI extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "dbTortillator";
	private static final int DATABASE_VERSION = 1;
	
	private static TortillatorAPI dbInstance = null;
	
	private SQLiteDatabase db; 

	private String sqlUsers = "CREATE TABLE USERS (" +
			"username TEXT PRIMARY KEY NOT NULL, " +
			"password TEXT NOT NULL, " +
			"email TEXT NOT NULL UNIQUE, " +
			"city TEXT)";
	
	private String sqlBar = "CREATE TABLE BAR (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
			"name TEXT NOT NULL, " +
			"address TEXT NOT NULL, " +
			"city TEXT NOT NULL, " +
			"province TEXT NOT NULL, " +
			"schedule TEXT, " +
			"latitude REAL, " +
			"longitude REAL)";
	
	private String sqlTortilla = "CREATE TABLE TORTILLA (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
			"price REAL NOT NULL, " +
			"average REAL, " +
			"image TEXT, " +
			"id_bar INTEGER, " +
			"FOREIGN KEY (id_bar) REFERENCES BAR (id))";
	
	private String sqlVotes = "CREATE TABLE VOTES (" +
			"id_tortilla INTEGER NOT NULL, " +
			"user TEXT NOT NULL, " +
			"date DATETIME, " +
			"rating INTEGER, " +
			"FOREIGN KEY (id_tortilla) REFERENCES TORTILLA (id), " +
			"FOREIGN KEY (user) REFERENCES USERS (username), " +
			"PRIMARY KEY(id_tortilla, user))";
	
	private String sqlComments = "CREATE TABLE COMMENTS (" +
			"id_tortilla INTEGER NOT NULL, " +
			"user TEXT NOT NULL, " +
			"datetime DATETIME, " +
			"text TEXT, " +
			"FOREIGN KEY (id_tortilla) REFERENCES TORTILLA (id), " +
			"FOREIGN KEY (user) REFERENCES USERS (username), " +
			"PRIMARY KEY(id_tortilla, user, datetime))";
			
			

	private TortillatorAPI(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static TortillatorAPI getInstance(Context ctx) 
	{
		if(dbInstance == null) 
		{
			dbInstance = new TortillatorAPI(ctx.getApplicationContext());
		}
		return dbInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(sqlUsers);
		db.execSQL(sqlBar);
		db.execSQL(sqlTortilla);
		db.execSQL(sqlVotes);
		db.execSQL(sqlComments);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS USERS");
		db.execSQL(sqlUsers);
		db.execSQL("DROP TABLE IF EXISTS BAR");
		db.execSQL(sqlBar);
		db.execSQL("DROP TABLE IF EXISTS TORTILLA");
		db.execSQL(sqlTortilla);
		db.execSQL("DROP TABLE IF EXISTS VOTES");
		db.execSQL(sqlVotes);
		db.execSQL("DROP TABLE IF EXISTS COMMENTS");
		db.execSQL(sqlComments);
	}
	
	/*public User loginUser(String username, String password)
	{
		db = this.getReadableDatabase();
		User user = new User();
		Cursor c = db.rawQuery("SELECT * FROM USERS WHERE username='"+
				username + "' AND password='" + password + "'", null);
		if(c.getCount() > 0) 
		{
			if (c.moveToFirst())
	        {
				String uname= c.getString(0);
				user.setUsername(uname);
				String pass = c.getString(1);
				user.setPassword(pass);	            
				String email = c.getString(2);
	            user.setEmail(email);
	            String city = c.getString(3);
	            user.setCity(city);
	        }
		}
		else
		{
			user = null;
		}
		db.close();
		return user;
	}*/
	
	public boolean newUser(User user)
	{
		boolean b = false;
		db = this.getWritableDatabase();
		if(db != null)
        {
			db.execSQL("INSERT INTO USERS VALUES ('"+user.getUsername()+"', '"+user.getPassword()+"', '"+user.getEmail()+"', '"+user.getCity()+"')");
			b = true;
        }
		db.close();
		return b;
	}
	
	public boolean existUsername(String username)
	{
		boolean b = false;
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT username FROM USERS WHERE username='" + username + "'", null);
			if(c.getCount() ==1)
			{
				b = true;
			}
		}
		db.close();
		return b;
	}
	public boolean existEmail(String email)
	{
		boolean b = false;
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT email FROM USERS WHERE email='" + email + "'", null);
			if(c.getCount() ==1)
			{
				b = true;
			}
		}
		db.close();
		return b;
	}
	public boolean newBar(Bar bar)
	{
		boolean b = false;
		db = this.getWritableDatabase();
		if(db != null)
        {
			db.execSQL("INSERT INTO BAR VALUES ("+ null +", '"+bar.getName()+"', '"+bar.getAddress()+"', '"+bar.getCity()+"', '"+bar.getProvince()+"', '"+bar.getSchedule()+"', "+bar.getLatitude()+", "+bar.getLongitude()+" )");
			b = true;
        }
		db.close();
		return b;
	}
	public boolean newTortilla(Tortilla tortilla)
	{
		boolean b = false;
		db = this.getWritableDatabase();
		if(db != null)
        {
			db.execSQL("INSERT INTO TORTILLA VALUES ("+ null +", '"+tortilla.getPrice()+"', '"+tortilla.getAverage()+"', '"+tortilla.getImage()+"', "+tortilla.getId_bar()+")");
			b = true;
        }
		db.close();
		return b;
	}
	public boolean newComment(Comment comment)
	{
		boolean b = false;
		db = this.getWritableDatabase();
		if(db != null)
        {
			db.execSQL("INSERT INTO COMMENTS VALUES ("+ comment.getId_tortilla() +", '"+comment.getUsername()+"', '"+comment.getDatetime()+"', '"+comment.getText()+"')");
			b = true;
        }
		db.close();
		return b;
	}
	public boolean newVote(Vote vote)
	{
		boolean b = false;
		db = this.getWritableDatabase();
		if(db != null)
        {
			db.execSQL("INSERT INTO VOTES VALUES ("+ vote.getId_tortilla() +", '"+vote.getUsername()+"', '"+vote.getDate()+"', '"+vote.getVote()+"')");
			b = true;
			this.updateTortillaAverage(vote.getId_tortilla());
        }
		db.close();
		return b;
	}
	public String getBarName(int id)
	{
		String name = "";
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT name FROM BAR WHERE id=" + id, null);
			if(c.moveToFirst())
			{
				name = c.getString(0);
			}
		}
		db.close();
		return name;
	}
	public ArrayList<Tortilla> getUsersTortillas(String username)
	{
		ArrayList<Tortilla> tortillas = new ArrayList<Tortilla>();
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT t.id, t.price, t.average, t.image, t.id_bar" +
					" FROM TORTILLA t, VOTES v " +
					"WHERE t.id=v.id_tortilla AND v.user='" + username + "'", null);
			if(c.getCount() >0)
			{
				while(c.moveToNext())
				{
					Tortilla tortilla = new Tortilla(c.getInt(0), c.getFloat(1), 
							c.getFloat(2), c.getString(3), c.getInt(4));
					tortillas.add(tortilla);
				}
			}
		}
		db.close();
		return tortillas;
	}
	public Integer getUserRating(String username, int id_tortilla)
	{
		int rating =-1;
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT rating FROM VOTES WHERE user='" + username + "' AND id_tortilla =" + id_tortilla, null);
			if(c.moveToFirst())
			{
				rating = c.getInt(0);
			}
		}
		db.close();		
		return rating;
	}
	public Bar getBar(int id)
	{
		Bar bar = new Bar();
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT * FROM BAR WHERE id=" + id, null);
			if(c.moveToFirst())
			{
				bar.setId(c.getInt(0));
				bar.setName(c.getString(1));
				bar.setAddress(c.getString(2));
				bar.setCity(c.getString(3));
				bar.setProvince(c.getString(4));
				bar.setSchedule(c.getString(5));
				bar.setLatitude(c.getFloat(6));
				bar.setLongitude(c.getFloat(7));
			}
		}
		db.close();
		return bar;
	}
	public Tortilla getTortilla(int id)
	{
		Tortilla tortilla = new Tortilla();
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT * FROM TORTILLA WHERE id=" + id, null);
			if(c.moveToFirst())
			{
				tortilla.setId(c.getInt(0));
				tortilla.setPrice(c.getFloat(1));
				tortilla.setAverage(c.getFloat(2));
				tortilla.setImage(c.getString(3));
				tortilla.setId_bar(c.getInt(4));
			}
		}
		db.close();
		return tortilla;
	}
	public ArrayList<Comment> getComments(int id_tortilla)
	{
		ArrayList<Comment> arrayComments = new ArrayList<Comment>();
		db = this.getReadableDatabase();
		if(db!=null)
		{
			Cursor c = db.rawQuery("SELECT * FROM COMMENTS WHERE id_tortilla=" + id_tortilla + " ORDER BY datetime DESC", null);
			while(c.moveToNext())
			{
				String date = c.getString(2);
				Date d = new Date(date);
				Comment comment = new Comment(c.getString(1), c.getInt(0),d , c.getString(3));
				arrayComments.add(comment);
			}
		}
		db.close();
		return arrayComments;
	}
	public void updateTortillaAverage(int id_tortilla)
	{
		db = this.getWritableDatabase();
		if(db!=null)
		{
			Cursor c = db.rawQuery("SELECT avg(rating) FROM VOTES WHERE id_tortilla= " + id_tortilla, null);
			if(c.moveToNext())
			{
				float average = c.getFloat(0);
				db.execSQL("UPDATE TORTILLA SET average=" + average + " WHERE id=" + id_tortilla);
				
			}
		}
		db.close();
	}

	public void updateVote(Vote vote)
	{
		db = this.getWritableDatabase();
		if(db!=null)
		{
			db.execSQL("UPDATE VOTES SET rating=" + vote.getVote() +
					", date='" + vote.getDate() + 
					"' WHERE id_tortilla=" + vote.getId_tortilla() +
					" AND user ='" + vote.getUsername() + "'");
		}
		this.updateTortillaAverage(vote.getId_tortilla());
		db.close();
	}
	
	public int getNumVotes(int id_tortilla)
	{
		int numVotes = 0;
		db = this.getReadableDatabase();
		if(db!=null)
		{
			Cursor c = db.rawQuery("SELECT count(id_tortilla) FROM VOTES WHERE id_tortilla="+id_tortilla, null);
			if(c.moveToNext())
			{
				numVotes = c.getInt(0);
			}
		}
		db.close();
		return numVotes;
	}
	public ArrayList<Tortilla> getRanking()
	{
		ArrayList<Tortilla> arrayRanking = new ArrayList<Tortilla>();
		db = this.getReadableDatabase();
		if(db!=null)
		{
			Cursor c = db.rawQuery("SELECT * FROM TORTILLA ORDER BY average DESC LIMIT 5", null);
			while(c.moveToNext())
			{
				Tortilla tortilla = new Tortilla();
				tortilla.setId(c.getInt(0));
				tortilla.setPrice(c.getFloat(1));
				tortilla.setAverage(c.getFloat(2));
				tortilla.setImage(c.getString(3));
				tortilla.setId_bar(c.getInt(4));
				arrayRanking.add(tortilla);
			}
		}
		db.close();
		return arrayRanking;
	}
	public ArrayList<Tortilla> getRecommendations(String username)
	{
		ArrayList<Tortilla> arrayRecommendations = new ArrayList<Tortilla>();
		db = this.getReadableDatabase();
		if(db!=null)
		{
			Cursor c = db.rawQuery("SELECT t.id, t.price, t.average, t.image, t.id_bar " +
					"from VOTES v, TORTILLA t WHERE t.id=v.id_tortilla " +
					"AND id_tortilla NOT IN (SELECT id_tortilla FROM votes WHERE user='"+username+"') " +
							"GROUP BY id_tortilla ORDER BY t.average DESC LIMIT 5 ", null);
			
			while(c.moveToNext())
			{
				Tortilla tortilla = new Tortilla();
				tortilla.setId(c.getInt(0));
				tortilla.setPrice(c.getFloat(1));
				tortilla.setAverage(c.getFloat(2));
				tortilla.setImage(c.getString(3));
				tortilla.setId_bar(c.getInt(4));
				arrayRecommendations.add(tortilla);
			}
		}
		db.close();
		return arrayRecommendations;
	}
	public ArrayList<Bar> getBarsNearsLocation(LatLng location)	
	{
		ArrayList<Bar> arrayBar = new ArrayList<Bar>();
		db = this.getReadableDatabase();
		if(db!=null)
		{
			Cursor c = db.rawQuery("SELECT * FROM BAR WHERE latitude BETWEEN "+(location.latitude-0.002) +" AND " +
					(location.latitude + 0.002) + " AND longitude BETWEEN "+(location.longitude-0.002)+ " AND " +
					(location.longitude + 0.002) + " LIMIT 5", null);
			while(c.moveToNext())
			{
				Bar bar = new Bar();
				bar.setId(c.getInt(0));
				bar.setName(c.getString(1));
				bar.setAddress(c.getString(2));
				bar.setCity(c.getString(3));
				bar.setProvince(c.getString(4));
				bar.setSchedule(c.getString(5));
				bar.setLatitude(c.getFloat(6));
				bar.setLongitude(c.getFloat(7));

				arrayBar.add(bar);
			} 	
		}
		db.close();
		return arrayBar;
	}
	public Bar getBarByName(String barName)
	{
		Bar bar = new Bar();
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT * FROM BAR WHERE name='" + barName+"'", null);
			if(c.moveToFirst())
			{
				bar.setId(c.getInt(0));
				bar.setName(c.getString(1));
				bar.setAddress(c.getString(2));
				bar.setCity(c.getString(3));
				bar.setProvince(c.getString(4));
				bar.setSchedule(c.getString(5));
				bar.setLatitude(c.getFloat(6));
				bar.setLongitude(c.getFloat(7));
			}
		}
		db.close();
		return bar;
	}
	public Tortilla getTortillaByBarId(int id)
	{
		Tortilla tortilla = new Tortilla();
		db = this.getReadableDatabase();
		if(db != null)
		{
			Cursor c = db.rawQuery("SELECT * FROM TORTILLA WHERE id_bar=" + id, null);
			if(c.moveToFirst())
			{
				tortilla.setId(c.getInt(0));
				tortilla.setPrice(c.getFloat(1));
				tortilla.setAverage(c.getFloat(2));
				tortilla.setImage(c.getString(3));
				tortilla.setId_bar(c.getInt(4));
			}
		}
		db.close();
		return tortilla;
	}
}
