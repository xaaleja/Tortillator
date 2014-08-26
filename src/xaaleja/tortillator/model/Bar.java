package xaaleja.tortillator.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bar implements Parcelable
{
	private int id;
	private String name;
	private String address;
	private String city;
	private String province;
	private String schedule;
	private float latitude;
	private float longitude;
	
	public Bar()
	{
	}
	
	public Bar(int id, String name, String address, String city, String province, String schedule, float latitude, float longitude)
	{
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.province = province;
		this.schedule = schedule;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	public Bar(Parcel in)
	{
		readFromParcel(in);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	public float getLatitude()
	{
		return this.latitude;
	}
	public void setLatitude(float latitude)
	{
		this.latitude = latitude;
	}
	public float getLongitude()
	{
		return this.longitude;
	}
	public void setLongitude(float longitude)
	{
		this.longitude = longitude;
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.address);
		dest.writeString(this.city);
		dest.writeString(this.province);
		dest.writeString(this.schedule);
		dest.writeFloat(this.latitude);
		dest.writeFloat(this.longitude);
	}
	
	public void readFromParcel(Parcel in) 
	{
		this.id = in.readInt();
		this.name = in.readString();
		this.address = in.readString();
		this.city = in.readString();
		this.province = in.readString();
		this.schedule = in.readString();
		this.latitude = in.readFloat();
		this.longitude = in.readFloat();
	}
	public static final Parcelable.Creator<Bar> CREATOR
    = new Parcelable.Creator<Bar>() {
        public Bar createFromParcel(Parcel in) {
            return new Bar(in);
        }
 
        public Bar[] newArray(int size) {
            return new Bar[size];
        }
    };
	
}
