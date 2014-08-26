package xaaleja.tortillator.activities;

import xaaleja.tortillator.R;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.utils.EmailValidator;
import xaaleja.tortillator.utils.ToastWriter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends Activity
{
	private User user = new User();
	private String message="";
	
	private boolean rb = false;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
	}
		
	public void onClickRegister(View v)
	{
		if(R.id.re_signUp == v.getId())
		{		
			Thread tr = new Thread(){
				@Override
				public void run()
				{
					if(checkRegisterFormUsername() && checkRegisterFormPassword() && checkRegisterFormEmail() && checkRegisterFormCity())
					{
						user.setUsername(((EditText)findViewById(R.id.re_usernameT)).getText().toString());
						user.setPassword(((EditText)findViewById(R.id.re_passwordT)).getText().toString());
						user.setEmail(((EditText)findViewById(R.id.re_emailT)).getText().toString());
						user.setCity(((EditText)findViewById(R.id.re_cityT)).getText().toString());

						if(TortillatorAPITesting.getInstance().newUser(user))
						{
							message = "Successfully registered";
							rb = true;
							Intent i = new Intent(SignUp.this, MainActivity.class);
					        startActivity(i);
						}
						else
						{
							message = "There has been a problem registering a new User. Try again please.";
						}

					}
					
					runOnUiThread(
							new Runnable() {
								public void run() 
								{
									if(!message.isEmpty())
										ToastWriter.writeToast(message, getApplicationContext());
									if(rb)
									{
										Intent i = new Intent(SignUp.this, MainActivity.class);
								        startActivity(i);
									}
								}
							});
				}
			};
			tr.start();						
		}
	}
	
	private boolean checkRegisterFormUsername()
	{
		boolean b = false;
		String username = ((EditText)findViewById(R.id.re_usernameT)).getText().toString();
		
		if(username.isEmpty())
		{
			message = "You have not inserted your username. Please insert your username.";
		}
		else 
		{
			if(TortillatorAPITesting.getInstance().existUsername(username))
			{
				message = "This username is already taken.";
			}
			else
			{
				b = true;
				message="";
			}
		}
		
		return b;
	}
	private boolean checkRegisterFormPassword()
	{
		boolean b = false;
		String password = ((EditText)findViewById(R.id.re_passwordT)).getText().toString();
		String repeatPass = ((EditText)findViewById(R.id.re_repeatPassT)).getText().toString();

		if(password.isEmpty() || repeatPass.isEmpty())
		{
			if(password.isEmpty())
			{
				message = "You have not inserted your password. Please insert your password.";
			}
			else
			{
				message = "You have to repeat your password";
			}
		}
		else
		{
			if(!password.equals(repeatPass))
			{
				message = "The passwords do not match";
			}
			else
			{
				b = true;
				message = "";
			}
		}
		return b;
	}
	private boolean checkRegisterFormEmail()
	{
		boolean b = false;
		String email = ((EditText)findViewById(R.id.re_emailT)).getText().toString();
	
		if(email.isEmpty())
		{
			message = "You have not inserted your email. Please insert your email.";
		}
		else
		{
			if(!EmailValidator.validate(email))
			{
				message = "Email format is not correct.";
			}
			else if(TortillatorAPITesting.getInstance().existEmail(email))
			{
				message = "This email is already taken.";
			}
			else
			{
				b = true;
				message = "";
			}
		}
		
		return b;
	}
	private boolean checkRegisterFormCity()
	{
		boolean b = false;
		String city = ((EditText)findViewById(R.id.re_cityT)).getText().toString();
		
		if(city.isEmpty())
		{
			message = "You have not inserted your city. Please insert your city.";
		}
		else
		{
			b=true;
			message = "";
		}

		return b;
	}
}
