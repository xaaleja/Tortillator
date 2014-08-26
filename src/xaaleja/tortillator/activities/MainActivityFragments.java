package xaaleja.tortillator.activities;

import xaaleja.tortillator.R;
import xaaleja.tortillator.fragments.TabsPagerAdapter;
import xaaleja.tortillator.model.User;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivityFragments extends FragmentActivity implements ActionBar.TabListener
{
	private User user;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;

	private ActionBar actionBar;
	private String[] tabs = { "Your votes", "Geolocation", "Recommends","Ranking" };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragments);

		user =(User)getIntent().getParcelableExtra("user");
		
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), user, MainActivityFragments.this, this);

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (String tab_name : tabs) 
		{
		    actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}
	
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
		    @Override
		    public void onPageSelected(int position) 
		    {
		        actionBar.setSelectedNavigationItem(position);
		    }
			
		    @Override
		    public void onPageScrolled(int arg0, float arg1, int arg2) {
		    }
		
		    @Override
		    public void onPageScrollStateChanged(int arg0) {
		    }
		});	
	}
	
	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		
	}
	
	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		actionBar.setTitle(tabs[tab.getPosition()]);
	}
	
	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == R.id.logout)
		{
			Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
