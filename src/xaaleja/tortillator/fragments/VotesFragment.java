package xaaleja.tortillator.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import xaaleja.tortillator.R;
import xaaleja.tortillator.activities.BarActivity;
import xaaleja.tortillator.adapters.ArrayAdapterTortilla;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class VotesFragment extends Fragment implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener
{
	private ListView tortillas;
	private User user;
	private View rootView;
	private ArrayAdapterTortilla aat;
	private Activity activity;
	private CharSequence initialQuery=null;
	private SearchView sv=null;
	
	private ArrayList<Tortilla> arrayTortillas = new ArrayList<Tortilla>();
	
	private HashMap<Integer, String> bars = new HashMap<Integer, String>();
	private HashMap<Integer, Integer> ratings = new HashMap<Integer, Integer>();
	
	public VotesFragment()
	{
		
	}

	public VotesFragment(User user, Activity activity)
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
		rootView = inflater.inflate(R.layout.votes_fragment, container,
				false);
		
		tortillas = (ListView)rootView.findViewById(R.id.vo_list);

		
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
				arrayTortillas = TortillatorAPITesting.getInstance().getUsersTortillas(user.getUsername());
				
				for(Tortilla t: arrayTortillas)
				{
					String barName = TortillatorAPITesting.getInstance().getBarName(t.getId_bar());
					bars.put(t.getId_bar(), barName);
					int ratingValue = TortillatorAPITesting.getInstance().getUserRating(user.getUsername(), t.getId());
					ratings.put(t.getId(), ratingValue);
				}
				
				activity.runOnUiThread(
						new Runnable() {
							public void run() 
							{
								aat = new ArrayAdapterTortilla(activity, arrayTortillas, user.getUsername(), bars, ratings);
								tortillas.setAdapter(aat);
							}
						});
			}
		};
		tr.start();
				
		tortillas.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) 
			{
				final int id_bar = arrayTortillas.get(position).getId_bar();
				Thread tr = new Thread(){
					@Override
					public void run()
					{
						final Bar bar = TortillatorAPITesting.getInstance().getBar(id_bar);
						
						activity.runOnUiThread(
								new Runnable() {
									public void run() 
									{
										Intent intent = new Intent(VotesFragment.this.getActivity(), BarActivity.class);
										intent.putExtra("bar", bar);
										intent.putExtra("user", user);
										intent.putExtra("tortilla", arrayTortillas.get(position));
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
		tortillas.setAdapter(null);
		aat.clear();
	}

	@Override
	public boolean onClose() {
	    aat.filter("");

	    return(true);
	}

	@Override
	public boolean onQueryTextChange(String newText) 
	{
		String text = newText.toString().toLowerCase(Locale.getDefault());
		this.aat.filter(text);
		return(true);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
	    return(false);
	}

}
