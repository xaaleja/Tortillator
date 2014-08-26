package xaaleja.tortillator.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable
{
	private String username;
	private String password;
	private String email;
	private String city;
	public User()
	{
		
	}
	
	public User(String username, String password, String email, String city)
	{
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setCity(city);
	}
	
	public User(Parcel in)
	{
		readFromParcel(in);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) 
	{
		if(username !=null && username!="")
			this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password !=null && password!="")
			this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if(city!=null && city!="")	
			this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email!=null && email!="")
			this.email = email;
	}
	@Override
	public String toString() 
	{
		return "Username: " + this.username + 
				" City: " + this.city + 
				" Email: " + this.email;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(this.username);
		dest.writeString(this.password);
		dest.writeString(this.email);
		dest.writeString(this.city);
	}
	
	public void readFromParcel(Parcel in) 
	{
		this.username = in.readString();
		this.password = in.readString();
		this.email = in.readString();
		this.city = in.readString();
	}
	public static final Parcelable.Creator<User> CREATOR
    = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
 
        public User[] newArray(int size) {
            return new User[size];
        }
    };
	
	
}
