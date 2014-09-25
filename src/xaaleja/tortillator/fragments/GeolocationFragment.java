package xaaleja.tortillator.fragments;

import java.util.ArrayList;

import xaaleja.tortillator.R;
import xaaleja.tortillator.activities.BarActivity;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeolocationFragment extends Fragment
{
	private User user;
	private Activity activity;
	private static final LatLng MY_LOCATION = new LatLng(43.326729, -3.032551);	   
	private static final String myLocationTitle = "My location";
	private GoogleMap googleMap;
	
	public GeolocationFragment()
	{
		
	}
	public GeolocationFragment(User user, Activity activity)
	{
		this.user = user;
		this.activity = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.geolocation_fragment, container,false);
		if(googleMap == null)
		{
			googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); 
		}
		return rootView;
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		

			final BitmapDescriptor myLocationColour = BitmapDescriptorFactory.defaultMarker(
			          BitmapDescriptorFactory.HUE_AZURE);

	        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        
	        Thread tr = new Thread(){
					@Override
					public void run()
					{
	        			final ArrayList<Bar> arrayBar = TortillatorAPITesting.getInstance().getBarsNearsLocation(MY_LOCATION);

						activity.runOnUiThread(
								new Runnable() {
									public void run() 
									{
								        for(Bar b : arrayBar) 
								        {
								        	 LatLng location = new LatLng(b.getLatitude(), b.getLongitude());
											 Marker m = googleMap.addMarker(new MarkerOptions().position(location).
													title(b.getName()));
										}
								        Marker myLocationMarker = googleMap.addMarker(new MarkerOptions().
								    	        position(MY_LOCATION).title(myLocationTitle).icon(myLocationColour));

									}
								});
					}
				};
				tr.start();
	        
	        //ArrayList<Bar> arrayBar = TortillatorAPI.getInstance(activity.getApplicationContext()).getBarsNearsLocation(MY_LOCATION);
	        /*for(Bar b : arrayBar) 
	        {
	        	 LatLng location = new LatLng(b.getLatitude(), b.getLongitude());
				 Marker m = googleMap.addMarker(new MarkerOptions().position(location).
						title(b.getName()));
			}*/
	                
	      
	        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_LOCATION, 17));
	        googleMap.setOnMarkerClickListener(new OnMarkerClickListener() 
	        {	
	        	@Override
				public boolean onMarkerClick(Marker marker) 
				{
		        	if(!marker.getTitle().equals(myLocationTitle))
		        	{
		        		final String title = marker.getTitle();
		        		Thread tr = new Thread(){
							@Override
							public void run()
							{
				        		final Bar bar = TortillatorAPITesting.getInstance().getBarByName(title);
				        		final Tortilla tortilla = TortillatorAPITesting.getInstance().getTortillaByBarId(bar.getId());
								
								activity.runOnUiThread(
										new Runnable() {
											public void run() 
											{
								        		Intent intent = new Intent(activity, BarActivity.class);
												intent.putExtra("user", user);
												intent.putExtra("bar", bar);
												intent.putExtra("tortilla", tortilla);
												startActivity(intent);
											}
										});
							}
						};
						tr.start();
		        		
		        		
		        		
		        		
		        		/*Bar bar = TortillatorAPI.getInstance(activity.getApplicationContext()).getBarByName(marker.getTitle());
		        		Tortilla tortilla = TortillatorAPI.getInstance(activity.getApplicationContext()).getTortillaByBarId(bar.getId());
		        		Intent intent = new Intent(activity.getApplicationContext(), BarActivity.class);
						intent.putExtra("user", user);
						intent.putExtra("bar", bar);
						intent.putExtra("tortilla", tortilla);
						startActivity(intent);*/
		        	}
					return true;
				}
			});
	}
	@Override
	public void onStop() 
	{
		super.onStop();
		googleMap = null;
	}

}
