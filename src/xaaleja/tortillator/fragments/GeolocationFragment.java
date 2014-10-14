package xaaleja.tortillator.fragments;


import java.util.ArrayList;

import xaaleja.tortillator.R;
import xaaleja.tortillator.activities.BarActivity;
import xaaleja.tortillator.activities.GeolocationActivity;
import xaaleja.tortillator.activities.LookingForActivity;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.utils.ToastWriter;
import xaaleja.tortillator.utils.UtilsImages;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;


public class GeolocationFragment extends Fragment implements LocationListener
{
	private User user;
	private Activity activity;
	private RadioGroup radioGroup;
	private View rootView;
	private Button showMapButton;
	private Button enableGPSButton;

	private TextView enableGPSText;
	

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
		this.enableGPSButton = (Button)rootView.findViewById(R.id.gpsNotEnabledButton);
		this.enableGPSText = (TextView)rootView.findViewById(R.id.gpsNotEnabled);

		
		this.showMapButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				int choice = 1;
				if(v.getId() == R.id.ShowMapButton)
				{
					switch (radioGroup.getCheckedRadioButtonId()) {
					case R.id.showAllBars:
						//bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						choice = 1;
						break;
					case R.id.showRecommendedBarsRadio:
						//TortillatorAPITesting.getInstance().getRecommendations(user.getUsername());						
						//bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						choice = 2;
						break;
					case R.id.showVotedBarsRadio:
						//TortillatorAPITesting.getInstance().getUsersTortillas(user.getUsername());
						//bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						choice = 3;
						break;
					default:
						break;
					}
					
					Intent intent = new Intent(activity, LookingForActivity.class);
					intent.putExtra("user", user);
					intent.putExtra("choice", choice);
					startActivity(intent);
					
				}
				
			}
		});
		
		
		return rootView;
	}
	

	
	@Override
	public void onLocationChanged(Location location) 
	{
		//Es llamado cuando cambia la ubicación
		
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		//Proveedor de ubicación, refresco mínimo, metros mínimos, listener
	
	}
	@Override
	public void onPause() 
	{
		super.onPause();
		//El GPS consume muchos recursos, mejor pararlo cuando no lo vayamos a utilizar
	}
	
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
		//this.showProgressBar.setVisibility(View.INVISIBLE);
		
		/*final DialogFragment settingsDialog = new DialogFragment();
		settingsDialog.
		
		settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image, null));
			
			
		//ImageView tortillaImage = (ImageView)settingsDialog.findViewById(R.id.ima_image);
		//tortillaImage.setImageDrawable(UtilsImages.loadImageAssets(BarActivity.this, tortilla.getImage()));
			
		tortillaImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				settingsDialog.dismiss();
			}
		});
		settingsDialog.show();*/		
		
		Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
		//ToastWriter.writeToast("You have to enable the GPS", activity.getApplicationContext());
	}
	@Override
	public void onProviderEnabled(String provider) 
	{	
		//Cuando se habilita el GPS
		//this.showProgressBar.setVisibility(View.VISIBLE);
		ToastWriter.writeToast("The GPS is enabled", activity.getApplicationContext());
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		//Es llamado cuando cambia el estado del GPS
		switch (status) 
		{
			case LocationProvider.AVAILABLE:
			{
				//this.showProgressBar.setVisibility(View.VISIBLE);
				break;
			}
			case LocationProvider.OUT_OF_SERVICE:
				ToastWriter.writeToast("The GPS is not available", activity.getApplicationContext());
				break;
			default:
				break;
		}
	}

}
