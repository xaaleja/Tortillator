package xaaleja.tortillator.fragments;
 

import xaaleja.tortillator.model.User;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
	private User user;
	private Activity activity;
	
    public TabsPagerAdapter(FragmentManager fm, User user, Context context, Activity activity) 
    {
        super(fm);
        this.user = user;
        this.activity = activity;
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Votes
            return new VotesFragment(user, activity);
        case 1:
            // Geolocation
            return new GeolocationFragment(user, activity);
        case 2:
            // Recommendation
            return new RecommendationFragment(user, activity);
        case 3:
            // Ranking
            return new RankingFragment(user, activity);    
        }
 
        return null;
    }
 
    @Override
    public int getCount() 
    {
        return 4;
    }
 
}