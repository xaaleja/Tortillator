package xaaleja.tortillator.fragments;

import java.util.ArrayList;

import xaaleja.tortillator.R;
import xaaleja.tortillator.activities.BarActivity;
import xaaleja.tortillator.db.TortillatorAPI;
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
		
		try 
		{
			BitmapDescriptor myLocationColour = BitmapDescriptorFactory.defaultMarker(
			          BitmapDescriptorFactory.HUE_AZURE);

	        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        ArrayList<Bar> arrayBar = TortillatorAPI.getInstance(activity.getApplicationContext()).getBarsNearsLocation(MY_LOCATION);
	        for(Bar b : arrayBar) 
	        {
	        	 LatLng location = new LatLng(b.getLatitude(), b.getLongitude());
				 Marker m = googleMap.addMarker(new MarkerOptions().position(location).
						title(b.getName()));
			}
	        
	        Marker myLocationMarker = googleMap.addMarker(new MarkerOptions().
	        position(MY_LOCATION).title(myLocationTitle).icon(myLocationColour));
	        
	      
	        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_LOCATION, 17));
	        googleMap.setOnMarkerClickListener(new OnMarkerClickListener() 
	        {	
	        	@Override
				public boolean onMarkerClick(Marker marker) 
				{
		        	if(!marker.getTitle().equals(myLocationTitle))
		        	{
	        			Bar bar = TortillatorAPI.getInstance(activity.getApplicationContext()).getBarByName(marker.getTitle());
		        		Tortilla tortilla = TortillatorAPI.getInstance(activity.getApplicationContext()).getTortillaByBarId(bar.getId());
		        		Intent intent = new Intent(activity.getApplicationContext(), BarActivity.class);
						intent.putExtra("user", user);
						intent.putExtra("bar", bar);
						intent.putExtra("tortilla", tortilla);
						startActivity(intent);
		        	}
					return true;
				}
			});
        } 
        catch (Exception e) {
           e.printStackTrace();
        }	
	}
	@Override
	public void onStop() 
	{
		super.onStop();
		googleMap = null;
	}

}