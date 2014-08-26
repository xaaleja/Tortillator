package xaaleja.tortillator.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import xaaleja.tortillator.R;
import xaaleja.tortillator.activities.BarActivity;
import xaaleja.tortillator.adapters.ArrayAdapterRanking;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

public class RankingFragment extends Fragment implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener
{
	private ListView tortillasRanking;
	private User user;
	private View rootView;
	private ArrayAdapterRanking aar;
	private Activity activity;
	
	private HashMap<Integer, String> bars = new HashMap<Integer, String>();
	private ArrayList<Tortilla> arrayRanking = new ArrayList<Tortilla>();


	
	public RankingFragment(User user, Activity activity)
	{
		this.user = user;
		this.activity = activity;
	}
	public void onActivityCreated(Bundle savedInstanceState) 
	{
	    setHasOptionsMenu(true);
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		rootView = inflater.inflate(R.layout.ranking_fragment, container,
				false);
		
		tortillasRanking = (ListView)rootView.findViewById(R.id.ra_list);

		return rootView;
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		
		Thread tr = new Thread(){
			@Override
			public void run()
			{
				arrayRanking = TortillatorAPITesting.getInstance().getRanking();
				
				for(Tortilla t: arrayRanking)
				{
					String barName = TortillatorAPITesting.getInstance().getBarName(t.getId_bar());
					bars.put(t.getId_bar(), barName);
				}
				
				activity.runOnUiThread(
						new Runnable() {
							public void run() 
							{
								aar = new ArrayAdapterRanking(activity, arrayRanking, bars);			
								tortillasRanking.setAdapter(aar);
							}
						});
			}
		};
		tr.start();
		
		//arrayRanking = TortillatorAPI.getInstance(this.activity.getApplicationContext()).getRanking();
		//aar = new ArrayAdapterRanking(this.activity, arrayRanking);			
		//tortillasRanking.setAdapter(aar);
		
		tortillasRanking.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) 
			{
				Thread tr = new Thread(){
					@Override
					public void run()
					{
						int id_bar = arrayRanking.get(position).getId_bar();
						final Bar bar = TortillatorAPITesting.getInstance().getBar(id_bar);

						activity.runOnUiThread(
								new Runnable() {
									public void run() 
									{
										Intent intent = new Intent(activity, BarActivity.class);
										intent.putExtra("bar", bar);
										intent.putExtra("user", user);
										intent.putExtra("tortilla", arrayRanking.get(position));
										startActivity(intent);
									}
								});
					}
				};
				tr.start();
			}		
		});
	}
	
	@Override
	public void onStop() 
	{
		super.onStop();
		tortillasRanking.setAdapter(null);
		this.aar.clear();
	}
	@Override
	public boolean onClose() {
	    aar.filter("");
	    return(true);
	}
	@Override
	public boolean onQueryTextChange(String newText) {
		String text = newText.toString().toLowerCase(Locale.getDefault());
		this.aar.filter(text);
		return(true);
	}
	@Override
	public boolean onQueryTextSubmit(String query) 
	{
	    return(false);
	}
	

}
