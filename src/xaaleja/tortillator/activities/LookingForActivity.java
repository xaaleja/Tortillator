package xaaleja.tortillator.activities;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

import xaaleja.tortillator.R;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.utils.ToastWriter;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class LookingForActivity extends Activity implements LocationListener
{
	private ProgressBar showProgressBar;
	private int precisionOptima=20;
	private double latitude;
	private double longitude;
	//= new LatLng(43.326729, -3.032551)
	private LocationManager lm;
	private int precisionMinimaRequerida =50;
	private User user;
	private int choice =1;
	private ArrayList<Bar> bars;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.looking_for);
		
		this.showProgressBar = (ProgressBar)findViewById(R.id.lookingForProgressBar);
		this.lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		user = (User)getIntent().getParcelableExtra("user");
		//choice = getIntent().getIntExtra("choice");
		choice = getIntent().getIntExtra("choice", 1);
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

	}
	
	@Override
	protected void onPause() 
	{	
		super.onPause();
		
		lm.removeUpdates(this);
	}	
	@Override
	public void onLocationChanged(Location location) 
	{
		
		float precision = location.getAccuracy();
		latitude=location.getLatitude();
		longitude=location.getLongitude();
	
		//Comprobar precisión
		if(precision<precisionMinimaRequerida)
		{
			final LatLng latlng = new LatLng(latitude, longitude);

			ToastWriter.writeToast("We have found you!", getApplicationContext());

			showProgressBar.setVisibility(View.INVISIBLE);
						
			//Tenemos la precisión
			Thread tr = new Thread(){
				@Override
				public void run()
				{					
					switch (choice) {
					case 1:
						bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						break;
					case 2:
						//TortillatorAPITesting.getInstance().getRecommendations(user.getUsername());						
						bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						break;
					case 3:
						//TortillatorAPITesting.getInstance().getUsersTortillas(user.getUsername());
						bars = TortillatorAPITesting.getInstance().getBarsNearsLocation(latlng);
						break;
					default:
						break;
					}
				
					runOnUiThread(
							new Runnable() {
								public void run() 
								{
									Intent i = new Intent(LookingForActivity.this, GeolocationActivity.class);
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

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
