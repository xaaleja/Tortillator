package xaaleja.tortillator.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tortilla implements Parcelable
{
	private int id;
	private float price;
	private float average;
	private String image;
	private int id_bar;
	
	public Tortilla()
	{
	}
	public Tortilla(int id, float price, float average, String image, int id_bar)
	{
		this.id = id;
		this.price = price;
		this.average = average;
		this.image = image;
		this.id_bar = id_bar;		
	}
	public Tortilla(Parcel in)
	{
		readFromParcel(in);
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getId_bar() {
		return id_bar;
	}

	public void setId_bar(int id_bar) {
		this.id_bar = id_bar;
	}
	
	@Override
	public String toString()
	{
		return this.id + " - " + 
				this.price + " - " + 
				this.average + " - " +
				this.image + " - " +
				this.id_bar;
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
		dest.writeFloat(this.price);
		dest.writeFloat(this.average);
		dest.writeString(this.image);
		dest.writeInt(this.id_bar);
	}
	
	public void readFromParcel(Parcel in) 
	{
		this.id = in.readInt();
		this.price = in.readFloat();
		this.average = in.readFloat();
		this.image = in.readString();
		this.id_bar = in.readInt();
	}
	public static final Parcelable.Creator<Tortilla> CREATOR
    = new Parcelable.Creator<Tortilla>() {
        public Tortilla createFromParcel(Parcel in) {
            return new Tortilla(in);
        }
 
        public Tortilla[] newArray(int size) {
            return new Tortilla[size];
        }
    };
	
}
