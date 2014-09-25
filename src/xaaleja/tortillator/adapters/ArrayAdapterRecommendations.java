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

public class ArrayAdapterRecommendations extends ArrayAdapter<Tortilla>
{
	private ArrayList<Tortilla> arrayRecommendation;
	private Activity activity;
    private static LayoutInflater inflater=null;
	private ArrayList<Tortilla> arrayList;
	
	private HashMap<Integer, String> bars = new HashMap<Integer, String>();

	public ArrayAdapterRecommendations(Activity activity, ArrayList<Tortilla> arrayRecommendation, HashMap<Integer, String> bars)
	{
		super(activity.getApplicationContext(),R.layout.item_recommend,arrayRecommendation);
		this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.arrayRecommendation = arrayRecommendation;
        this.arrayList = new ArrayList<Tortilla>();
        this.arrayList.addAll(arrayRecommendation);
        this.bars = bars;

	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View item=convertView;
        if(convertView==null)
            item = inflater.inflate(R.layout.item_recommend, null);
	
		
		
		ImageView tortillaImage = (ImageView)item.findViewById(R.id.rec_image);
		tortillaImage.setImageDrawable(UtilsImages.loadImageAssets(this.activity.getApplicationContext(), arrayRecommendation.get(position).getImage()));
		
		TextView bar = (TextView)item.findViewById(R.id.rec_bar);
		//String barName = TortillatorAPI.getInstance(this.activity.getApplicationContext()).getBarName(arrayRecommendation.get(position).getId_bar());
		String barName = bars.get(arrayRecommendation.get(position).getId_bar());

		bar.setText(barName);
		
		TextView average = (TextView)item.findViewById(R.id.rec_average);
		float ave = arrayRecommendation.get(position).getAverage();
		average.setText(" "+String.format("%.1f", ave));
		
		return item;
	}
	
	public void filter(String charText)
	{
		charText = charText.toLowerCase(Locale.getDefault());
		this.arrayRecommendation.clear();
		if(charText.length() ==0)
		{
			this.arrayRecommendation.addAll(arrayList);
		}
		else
		{
			for(Tortilla t : arrayList)
			{
				//String bar = TortillatorAPI.getInstance(activity.getApplicationContext()).getBarName(t.getId());
				String bar = bars.get(t.getId());

				if(bar.toLowerCase(Locale.getDefault()).contains(charText))
				{
					this.arrayRecommendation.add(t);	
				}
			}
		}
		notifyDataSetChanged();
	}

}
