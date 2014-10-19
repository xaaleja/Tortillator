package xaaleja.tortillator.activities;

import com.google.android.gms.internal.ho;

import xaaleja.tortillator.R;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.utils.ToastWriter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity 
{	
	Button enter;
	
	private User user;
	private String message="";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		enter = (Button)findViewById(R.id.lo_Enter);
	}
	
	public void onClickEnter(View v) {
		if(R.id.lo_Enter == v.getId())
		{
			Thread tr = new Thread(){
				@Override
				public void run()
				{

					final String username = ((EditText)findViewById(R.id.lo_usernameT)).getText().toString();
					final String password = ((EditText)findViewById(R.id.lo_passwordT)).getText().toString();

					if(username.isEmpty())
					{
						message = message + "You have not inserted your username. Please insert your username.";
						user = null;
					}
					else
					{
						if(password.isEmpty())
						{
							message = message + "You have not inserted your password. Please insert your password.";
							user = null;
						}
						else
						{
							user = TortillatorAPITesting.getInstance().loginUser(username, password);
							if(user == null)
								message="The username or password that you entered is incorrect";
						}
					}
					
					runOnUiThread(
						new Runnable() {
							public void run() 
							{
								if(user != null)
								{
									ToastWriter.writeToast("Login accepted: "+ user.toString(), MainActivity.this);
									Intent i = new Intent(MainActivity.this, MainActivityFragments.class);
							        i.putExtra("user", user);
									startActivity(i);
								}
								else
								{
									ToastWriter.writeToast(message, MainActivity.this);
							        message="";
								}
							}
						});
				}
			};
			tr.start();
		}
	}

	public void onClickSignUp(View v)
	{
		if(R.id.lo_signUpB == v.getId())
		{
			Intent i = new Intent(MainActivity.this, SignUp.class);
	        startActivity(i);
		}
		else
		{
			Toast.makeText(this, "ERROR al cambiar de actividad", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();	
    }
	
	/*private void writeToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
	
	public boolean checkLoginUsername(String username)
	{
		boolean b = false;
		//The function isEmpty doesn't work in Android version 2.2 and less.
		if(android.os.Build.VERSION.SDK_INT>=10)
		{
			if(username.isEmpty())
			{
				Toast.makeText(this, "You have not inserted your username. Please insert your username.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				b = true;
			}
		}
		else 
		{
			if(username =="")
			{
				Toast.makeText(this, "You have not inserted your username. Please insert your username.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				b = true;
			}
		}
	
		return b;
	}
	
	public boolean checkLoginPassword(String password)
	{
		boolean b = false;
		if(android.os.Build.VERSION.SDK_INT>=10)
		{
			if(password.isEmpty())
			{
				Toast.makeText(this, "You have not inserted your password. Please insert your password.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				b = true;
			}
		}
		else 
		{
			if(password =="")
			{
				Toast.makeText(this, "You have not inserted your password. Please insert your password.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				b = true;
			}
		}
		return b;
	}
	
	private class Login extends AsyncTask<String, Void, String>
    {
         protected String doInBackground(String... params) {
			return null;
        	 
         }
    }*/
	
	
	
	/*
	private void insertTestData()
	{
		//---------- Test data ---------------------
		
				User user1 = new User("user1", "11", "user1@gmail.com", "Santurtzi");
				User user2 = new User("user2", "22", "user2@gmail.com", "Galdakao");
				
				TortillatorAPI.getInstance(this).newUser(user1);
				TortillatorAPI.getInstance(this).newUser(user2);
				
				Bar bar1 = new Bar(1,"Bar Moe´s", "Gabriel Aresti 3", "Santurtzi", "Bizkaia", "TH-SU: 10:00 - 22:00",(float)43.326598,(float)-3.032681);
				Bar bar2 = new Bar(2, "Bar Tiberius", "Genaro Oraa 18", "Santurtzi", "Bizkaia", "MO-SU: 10:00 - 22:00", (float)43.327429, (float)-3.03201);
				Bar bar3 = new Bar(3, "Bar Reinolds", "Santa Eulalia 5", "Santurtzi", "Bizkaia", "TH-SU: 10:00 - 23:00", (float)43.327157,(float) -3.031502);

				TortillatorAPI.getInstance(this).newBar(bar1);
				TortillatorAPI.getInstance(this).newBar(bar2);
				TortillatorAPI.getInstance(this).newBar(bar3);

				Tortilla tortilla1 = new Tortilla(1, (float)1.35,(float)6.75, "imagesTortilla/tortilla1.jpg", 1);
				Tortilla tortilla2 = new Tortilla(2, (float)1.40,(float)8.75, "imagesTortilla/tortilla2.jpg", 2);
				Tortilla tortilla3 = new Tortilla(3, (float)1.20,(float)7.25, "imagesTortilla/tortilla3.jpg", 3);
				TortillatorAPI.getInstance(this).newTortilla(tortilla1);
				TortillatorAPI.getInstance(this).newTortilla(tortilla2);
				TortillatorAPI.getInstance(this).newTortilla(tortilla3);
				
				Vote vote1 = new Vote("user1", 1, 7, new Date());
				Vote vote2 = new Vote("user1", 2, 5, new Date());
				Vote vote3 = new Vote("user1", 3, 7, new Date());
				Vote vote4 = new Vote("user2", 1, 7, new Date());
				Vote vote5 = new Vote("user2", 2, 5, new Date());
				Vote vote6 = new Vote("user2", 3, 4, new Date());

				TortillatorAPI.getInstance(this).newVote(vote1);
				TortillatorAPI.getInstance(this).newVote(vote2);
				TortillatorAPI.getInstance(this).newVote(vote3);
				TortillatorAPI.getInstance(this).newVote(vote4);
				TortillatorAPI.getInstance(this).newVote(vote5);
				TortillatorAPI.getInstance(this).newVote(vote6);
				Comment comment1 = new Comment("user1", 1, new Date(), "Está bastante buena");
				Comment comment2 = new Comment("user1", 2, new Date(), "Tiene demasiada cebolla");
				Comment comment3 = new Comment("user1", 3, new Date(), "Al volver de fiesta es como mejor entra");
				Comment comment4 = new Comment("user2", 1, new Date(), "Para el precio que tiene no está nada mal");
				Comment comment5 = new Comment("user2", 2, new Date(), "Suele estar demasiado hecha");
				Comment comment6 = new Comment("user2", 3, new Date(), "Sabe un poco a cartón");

				TortillatorAPI.getInstance(this).newComment(comment1);
				TortillatorAPI.getInstance(this).newComment(comment2);
				TortillatorAPI.getInstance(this).newComment(comment3);
				TortillatorAPI.getInstance(this).newComment(comment4);
				TortillatorAPI.getInstance(this).newComment(comment5);
				TortillatorAPI.getInstance(this).newComment(comment6);
				
				//------------------------------------------
	}*/
}
