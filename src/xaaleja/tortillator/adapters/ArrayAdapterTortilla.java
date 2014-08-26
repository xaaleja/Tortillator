package xaaleja.tortillator.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import xaaleja.tortillator.R;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.utils.UtilsImages;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ArrayAdapterTortilla extends ArrayAdapter<Tortilla>
{
    private Activity activity;
    private static LayoutInflater inflater=null;
	private ArrayList<Tortilla> arrayTortillas;
	private String username;
	
	private ArrayList<Tortilla> arrayList;
	private HashMap<Integer, String> bars = new HashMap<Integer, String>();
	private HashMap<Integer, Integer> ratings = new HashMap<Integer, Integer>();
	
	/**
	 * 
	 * Constructor of the Adapter
	 * @param context context of the activity who uses the adapter
	 * @param arrayTortilla datas which we want to show on the listview
	 */
	public ArrayAdapterTortilla(Activity activity, ArrayList<Tortilla> arrayTortillas, String username, 
			HashMap<Integer, String> bars, HashMap<Integer, Integer> ratings)
	{
		super(activity.getApplicationContext(),R.layout.item_vote,arrayTortillas);
		this.activity = activity;
		this.arrayTortillas = arrayTortillas;
		this.username = username;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        this.arrayList = new ArrayList<Tortilla>();
        this.arrayList.addAll(arrayTortillas);
        
        this.bars = bars;
        this.ratings = ratings;
	}
	
	@Override
	public int getCount() 
	{
		return this.arrayTortillas.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View item=convertView;
	        if(convertView==null)
	            item = inflater.inflate(R.layout.item_vote, null);
		
		//From this list, I take the controls
		ImageView tortillaImage = (ImageView)item.findViewById(R.id.vo_image);
		tortillaImage.setImageDrawable(UtilsImages.loadImageAssets(this.activity.getApplicationContext(), arrayTortillas.get(position).getImage()));
		
		//Bar
		TextView bar = (TextView)item.findViewById(R.id.vo_bar);
		//barName = TortillatorAPI.getInstance(this.activity.getApplicationContext()).getBarName(arrayTortillas.get(position).getId_bar());
		String barName = bars.get(arrayTortillas.get(position).getId_bar());
		bar.setText(barName);
		//Price
		TextView price = (TextView)item.findViewById(R.id.vo_price);
		price.setText(" "+Float.toString(arrayTortillas.get(position).getPrice()) + " €");
		//Average
		TextView average = (TextView)item.findViewById(R.id.vo_average);
		float ave = arrayTortillas.get(position).getAverage();
		average.setText(" "+String.format("%.1f", ave));
		//Your rating
		TextView rating = (TextView) item.findViewById(R.id.vo_your_rating);
		Integer ratingValue = ratings.get(arrayTortillas.get(position).getId());
		rating.setText(Integer.toString(ratingValue));
		return item;
	}
	
	public void filter(String charText)
	{
		charText = charText.toLowerCase(Locale.getDefault());
		this.arrayTortillas.clear();
		if(charText.length() ==0)
		{
			this.arrayTortillas.addAll(arrayList);
		}
		else
		{
			for(Tortilla t : arrayList)
			{
				String bar = bars.get(t.getId());
				if(bar.toLowerCase(Locale.getDefault()).contains(charText))
				{
					this.arrayTortillas.add(t);	
				}
			}
		}
		notifyDataSetChanged();
	}
}
