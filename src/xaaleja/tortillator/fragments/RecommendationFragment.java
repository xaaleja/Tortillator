package xaaleja.tortillator.fragments;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import xaaleja.tortillator.R;
import xaaleja.tortillator.activities.BarActivity;
import xaaleja.tortillator.adapters.ArrayAdapterRecommendations;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class RecommendationFragment extends Fragment implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener
{
	private ListView tortillasRecommendation;
	private User user;
	private Activity activity;
	
	private View rootView;
	private ArrayAdapterRecommendations aare;
	private CharSequence initialQuery=null;
	private SearchView sv=null;
	
	private ArrayList<Tortilla> arrayRecommendation = new ArrayList<Tortilla>();
	private HashMap<Integer, String> bars = new HashMap<Integer, String>();

	private TextView noRecommends;

	public RecommendationFragment()
	{
		
	}
	public RecommendationFragment(User user, Activity activity)
	{
		this.user = user;
		this.activity = activity;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
	    setHasOptionsMenu(true);
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
	    inflater.inflate(R.menu.search, menu);
	    
	    MenuItem search=menu.findItem(R.id.search);

	    sv=(SearchView)search.getActionView();
	    sv.setOnQueryTextListener(this);
	    sv.setOnCloseListener(this);
	    sv.setSubmitButtonEnabled(false);

	    if (initialQuery != null) {
	      sv.setIconified(false);
	      search.expandActionView();
	      sv.setQuery(initialQuery, true);
	    }

		super.onCreateOptionsMenu(menu, inflater);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		rootView = inflater.inflate(R.layout.recommendation_fragment, container,
				false);
		
		tortillasRecommendation = (ListView)rootView.findViewById(R.id.re_list);
		noRecommends = (TextView)rootView.findViewById(R.id.re_noRecommends);

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
				arrayRecommendation = TortillatorAPITesting.getInstance().getRecommendations(user.getUsername());
				if(!arrayRecommendation.isEmpty())
				{
					Log.i("Array not null", "ENTRA");
					for(Tortilla t: arrayRecommendation)
					{
						String barName = TortillatorAPITesting.getInstance().getBarName(t.getId_bar());
						bars.put(t.getId_bar(), barName);
					}
				}

				activity.runOnUiThread(
						new Runnable() {
							public void run() 
							{
								if(!arrayRecommendation.isEmpty())
								{
									Log.i("Array not null", "ENTRA");
									aare = new ArrayAdapterRecommendations(RecommendationFragment.this.activity, arrayRecommendation, bars);			
									tortillasRecommendation.setAdapter(aare);
								}
								else
								{
									tortillasRecommendation.setVisibility(View.INVISIBLE);
									noRecommends.setVisibility(View.VISIBLE);
								}
							}
						});
			}
		};
		tr.start();
		
		
		/*arrayRecommendation = TortillatorAPI.getInstance(this.activity.getApplicationContext()).getRecommendations(user.getUsername());
		aare = new ArrayAdapterRecommendations(this.activity, arrayRecommendation);			
		tortillasRecommendation.setAdapter(aare);
		*/
		tortillasRecommendation.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) 
			{
				final int id_bar = arrayRecommendation.get(position).getId_bar();
				Thread tr = new Thread(){
					@Override
					public void run()
					{
						final Bar bar = TortillatorAPITesting.getInstance().getBar(id_bar);
						
						activity.runOnUiThread(
								new Runnable() {
									public void run() 
									{
										Intent intent = new Intent(RecommendationFragment.this.activity, BarActivity.class);
										intent.putExtra("bar", bar);
										intent.putExtra("user", user);
										intent.putExtra("tortilla", arrayRecommendation.get(position));
										startActivity(intent);
									}
								});

					}
				};
				tr.start();

				
				
				/*int id_bar = arrayRecommendation.get(position).getId_bar();
				Bar bar = TortillatorAPI.getInstance(parent.getContext()).getBar(id_bar);
				Intent intent = new Intent(parent.getContext(), BarActivity.class);
				intent.putExtra("bar", bar);
				intent.putExtra("user", user);
				intent.putExtra("tortilla", arrayRecommendation.get(position));
				startActivity(intent);*/
			}		
		});
	}
	
	
	@Override
	public void onStop() 
	{
		super.onStop();
		tortillasRecommendation.setAdapter(null);
		if(this.aare !=null)	
			aare.clear();		
	}

	@Override
	public boolean onClose() {
		if(this.aare !=null)	
			aare.filter("");

	    return(true);
	}

	@Override
	public boolean onQueryTextChange(String newText) 
	{
		String text = newText.toString().toLowerCase(Locale.getDefault());
		if(this.aare !=null)	
			this.aare.filter(text);
		return(true);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
	    return(false);
	}


}
