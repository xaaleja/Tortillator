package xaaleja.tortillator.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable
{
	private int id_tortilla;
	private String username;
	private Date datetime;
	private String text;
	
	public Comment(String username, int id_tortilla, Date datetime, String text)
	{
		this.id_tortilla = id_tortilla;
		this.username = username;
		this.datetime = datetime;
		this.text = text;
	}
	
	public Comment(Parcel in)
	{
		readFromParcel(in);
	}
	

	public int getId_tortilla() {
		return id_tortilla;
	}

	public void setId_tortilla(int id_tortilla) {
		this.id_tortilla = id_tortilla;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
		dest.writeInt(this.id_tortilla);
		dest.writeSerializable(this.datetime);
		dest.writeString(this.text);
	}
	
	public void readFromParcel(Parcel in) 
	{
		this.username = in.readString();
		this.id_tortilla = in.readInt();
		this.datetime = (Date)in.readSerializable();
		this.text = in.readString();
	}
	public static final Parcelable.Creator<Comment> CREATOR
    = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }
 
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
