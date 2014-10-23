package xaaleja.tortillator.activities;

import java.util.ArrayList;

import xaaleja.tortillator.R;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.fragments.GeolocationFragment;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeolocationActivity extends Activity
{
	private User user;
	private static LatLng MY_LOCATION;	   
	private static final String myLocationTitle = "My location";
	private GoogleMap googleMap = null;
	private ArrayList<Bar>bars;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geolocation);
		user = (User)getIntent().getParcelableExtra("user");
		bars = getIntent().getParcelableArrayListExtra("bars");
		MY_LOCATION = (LatLng)getIntent().getParcelableExtra("latlng");

		if(bars==null)
		{
			Log.e("BARS", "No recoge los bares");
		}
		if(MY_LOCATION == null)
		{
			Log.e("MY LOCATION", "No recoge la localización");
		}
	}
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.geolocation_fragment, container,false);
		if(googleMap == null)
		{
			googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); 
		}
		return rootView;
	}*/
	
	@Override
	public void onResume() 
	{
		super.onResume();
		
		if(googleMap == null)
		{
			   googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView))
				        .getMap();	
		}	
		
		final BitmapDescriptor myLocationColour = BitmapDescriptorFactory.defaultMarker(
			          BitmapDescriptorFactory.HUE_AZURE);

	        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        
	        Thread tr = new Thread(){
					@Override
					public void run()
					{
						GeolocationActivity.this.runOnUiThread(
								new Runnable() {
									public void run() 
									{
								        for(Bar b : bars) 
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
								
								GeolocationActivity.this.runOnUiThread(
										new Runnable() {
											public void run() 
											{
								        		Intent intent = new Intent(GeolocationActivity.this, BarActivity.class);
												intent.putExtra("user", user);
												intent.putExtra("bar", bar);
												intent.putExtra("tortilla", tortilla);
												startActivity(intent);
											}
										});
							}
						};
						tr.start();
		        		
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
