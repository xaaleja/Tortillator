package xaaleja.tortillator.fragments;


import java.util.ArrayList;

import xaaleja.tortillator.R;
import xaaleja.tortillator.activities.GeolocationActivity;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.utils.ToastWriter;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLng;


public class GeolocationFragment extends Fragment implements LocationListener
{
	private User user;
	private Activity activity;
	private RadioGroup radioGroup;
	private View rootView;
	private Button showMapButton;
	private ProgressBar showProgressBar;
	
	private int precisionOptima=20;
	private double latitude;
	private double longitude;
	//= new LatLng(43.326729, -3.032551)
	private LocationManager lm;
	private int precisionMinimaRequerida =50;
	
	private ArrayList<Bar> bars;

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
		rootView = inflater.inflate(R.layout.geo_fragment, container,
				false);
		this.radioGroup = (RadioGroup)rootView.findViewById(R.id.showBarsRadioGroup);
		this.showMapButton = (Button)rootView.findViewById(R.id.ShowMapButton);
		this.showProgressBar = (ProgressBar)rootView.findViewById(R.id.geolocationProgressBar);
		this.lm = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
		
		this.showMapButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				if(v.getId() == R.id.ShowMapButton)
				{
					final LatLng latlng = new LatLng(latitude, longitude);
					
					Thread tr = new Thread(){
						@Override
						public void run()
						{
							
							switch (radioGroup.getCheckedRadioButtonId()) {
							case R.id.showAllBars:
								bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
								break;
							case R.id.showRecommendedBarsRadio:
								//TortillatorAPITesting.getInstance().getRecommendations(user.getUsername());						
								bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
								break;
							case R.id.showVotedBarsRadio:
								//TortillatorAPITesting.getInstance().getUsersTortillas(user.getUsername());
								bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
								break;
							default:
								break;
							}
						
							activity.runOnUiThread(
									new Runnable() {
										public void run() 
										{
											Intent i = new Intent(activity, GeolocationActivity.class);
									        i.putExtra("user", user);
											i.putExtra("bars", bars);
									        i.putExtra("latlng", latlng);
											startActivity(i);
										}
									});
						}
					};
					tr.start();
					
				}
				
			}
		});
		
		
		return rootView;
	}
	

	
	@Override
	public void onLocationChanged(Location location) 
	{
		//Es llamado cuando cambia la ubicación
		float precision = location.getAccuracy();
		latitude=location.getLatitude();
		longitude=location.getLongitude();
	
		//Comprobar precisión
		if(precision<precisionMinimaRequerida)
		{
			//Tenemos la precisión
			this.radioGroup.setVisibility(View.VISIBLE);
			this.showMapButton.setVisibility(View.VISIBLE);
			this.showProgressBar.setVisibility(View.INVISIBLE);
			
			ToastWriter.writeToast("We have found you!", activity.getApplicationContext());
		}
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		//Proveedor de ubicación, refresco mínimo, metros mínimos, listener
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
	
	}
	@Override
	public void onPause() 
	{
		super.onPause();
		//El GPS consume muchos recursos, mejor pararlo cuando no lo vayamos a utilizar
		lm.removeUpdates(this);
	}
	
	
	
	/*public void onClickShow(View v)
	{
		if(v.getId() == R.id.ShowMapButton)
		{
			final LatLng latlng = new LatLng(latitude, longitude);
			
			Thread tr = new Thread(){
				@Override
				public void run()
				{
					
					switch (radioGroup.getCheckedRadioButtonId()) {
					case R.id.showAllBars:
						bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						break;
					case R.id.showRecommendedBarsRadio:
						//TortillatorAPITesting.getInstance().getRecommendations(user.getUsername());						
						bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						break;
					case R.id.showVotedBarsRadio:
						//TortillatorAPITesting.getInstance().getUsersTortillas(user.getUsername());
						bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						break;
					default:
						break;
					}
				
					activity.runOnUiThread(
							new Runnable() {
								public void run() 
								{
									Intent i = new Intent(activity, GeolocationActivity.class);
							        i.putExtra("user", user);
									i.putExtra("bars", bars);
							        i.putExtra("latlng", latlng);
									startActivity(i);
								}
							});
				}
			};
			tr.start();
			
		}
	}*/
	@Override
	public void onStop() 
	{
		super.onStop();
	}
	@Override
	public void onProviderDisabled(String provider) 
	{
		//Cuando se deshabilita el GPS
		
		//Le reenviamos a los ajustes de GPS de Android
		Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
		ToastWriter.writeToast("You have to enable the GPS", activity.getApplicationContext());
	}
	@Override
	public void onProviderEnabled(String provider) 
	{	
		//Cuando se habilita el GPS
		ToastWriter.writeToast("The GPS is enabled", activity.getApplicationContext());
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		//Es llamado cuando cambia el estado del GPS
		switch (status) 
		{
			case LocationProvider.OUT_OF_SERVICE:
				ToastWriter.writeToast("The GPS is not available", activity.getApplicationContext());
				break;
			default:
				break;
		}
	}

}
