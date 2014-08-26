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

public class ArrayAdapterRanking extends ArrayAdapter<Tortilla>
{
	private Activity activity;
	private ArrayList<Tortilla> arrayRanking;
    private static LayoutInflater inflater=null;
	private ArrayList<Tortilla> arrayList;
	
	private HashMap<Integer, String> bars = new HashMap<Integer, String>();

	
	public ArrayAdapterRanking(Activity activity, ArrayList<Tortilla> arrayRanking, HashMap<Integer, String> bars)
	{
		super(activity.getApplicationContext(),R.layout.item_ranking,arrayRanking);
		this.activity = activity;
		this.arrayRanking = arrayRanking;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = new ArrayList<Tortilla>();
        this.arrayList.addAll(arrayRanking);
        
        this.bars = bars;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		 View item=convertView;
	        if(convertView==null)
	            item = inflater.inflate(R.layout.item_ranking, null);
		
		ImageView tortillaImage = (ImageView)item.findViewById(R.id.ra_image);
		tortillaImage.setImageDrawable(UtilsImages.loadImageAssets(this.activity.getApplicationContext(), arrayRanking.get(position).getImage()));		
		
		TextView bar = (TextView)item.findViewById(R.id.ra_bar);
		String barName = bars.get((arrayRanking.get(position).getId_bar()));

		bar.setText(barName);
		
		TextView average = (TextView)item.findViewById(R.id.ra_average);
		float ave = arrayRanking.get(position).getAverage();
		average.setText(" "+String.format("%.1f", ave));
		
		TextView pos = (TextView)item.findViewById(R.id.ra_pos);
		pos.setText(""+(position+1));
		
		return item;
	}
	
	public void filter(String charText)
	{
		charText = charText.toLowerCase(Locale.getDefault());
		this.arrayRanking.clear();
		if(charText.length() ==0)
		{
			this.arrayRanking.addAll(arrayList);
		}
		else
		{
			for(Tortilla t : arrayList)
			{
				String bar = bars.get(t.getId_bar());
				if(bar.toLowerCase(Locale.getDefault()).contains(charText))
				{
					this.arrayRanking.add(t);	
				}
			}
		}
		notifyDataSetChanged();
	}
}
