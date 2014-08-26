package xaaleja.tortillator.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Vote implements Parcelable
{
	private String username;
	private int id_tortilla;
	private int vote;
	private Date date;
	
	public Vote(String username, int id_tortilla, int vote, Date date)
	{
		this.username = username;
		this.id_tortilla = id_tortilla;
		this.vote = vote;
		this.date = date;
	}
	public Vote(Parcel in)
	{
		readFromParcel(in);
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId_tortilla() {
		return id_tortilla;
	}

	public void setId_tortilla(int id_tortilla) {
		this.id_tortilla = id_tortilla;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
		dest.writeInt(this.vote);
		dest.writeSerializable(this.date);
	}
	
	public void readFromParcel(Parcel in) 
	{
		this.username = in.readString();
		this.id_tortilla = in.readInt();
		this.vote = in.readInt();
		this.date = (Date)in.readSerializable();
	}
	public static final Parcelable.Creator<Vote> CREATOR
    = new Parcelable.Creator<Vote>() {
        public Vote createFromParcel(Parcel in) {
            return new Vote(in);
        }
 
        public Vote[] newArray(int size) {
            return new Vote[size];
        }
    };
}
