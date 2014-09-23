package xaaleja.tortillator.activities;

import java.util.ArrayList;
import java.util.Date;

import xaaleja.tortillator.R;
import xaaleja.tortillator.adapters.ArrayAdapterComment;
import xaaleja.tortillator.adapters.ArrayAdapterTortilla;
import xaaleja.tortillator.db.TortillatorAPI;
import xaaleja.tortillator.db.TortillatorAPITesting;
import xaaleja.tortillator.model.Bar;
import xaaleja.tortillator.model.Comment;
import xaaleja.tortillator.model.Tortilla;
import xaaleja.tortillator.model.User;
import xaaleja.tortillator.model.Vote;
import xaaleja.tortillator.utils.UtilsImages;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class BarActivity extends Activity {

	private Bar bar;
	private User user;
	private Tortilla tortilla;
	private ListView comments;
	private ArrayList<Comment> arrayComments;
	private ArrayAdapterComment aac;
	private Integer yourRating;
	private RatingBar ratingBar;
	private TextView numVotes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bar);
		
		bar = (Bar)getIntent().getParcelableExtra("bar");
		user = (User)getIntent().getParcelableExtra("user");
		tortilla = (Tortilla)getIntent().getParcelableExtra("tortilla");
		
		ImageView tortillaImage = (ImageView)findViewById(R.id.bar_image);
		tortillaImage.setImageDrawable(UtilsImages.loadImageAssets(BarActivity.this, tortilla.getImage()));
		TextView name = (TextView)findViewById(R.id.bar_name);
		name.setText(bar.getName());
		final TextView average = (TextView)findViewById(R.id.bar_averageN);
		average.setText(" "+String.format("%.1f", tortilla.getAverage()));

		TextView address = (TextView)findViewById(R.id.bar_addressT);
		address.setText(" "+bar.getAddress());
		TextView city = (TextView)findViewById(R.id.bar_cityT);
		city.setText(" "+bar.getCity()+", ");
		TextView province = (TextView)findViewById(R.id.bar_provinceT);
		province.setText(" "+bar.getProvince());
		TextView schedule = (TextView)findViewById(R.id.bar_scheduleT);
		schedule.setText(" "+bar.getSchedule());
		
		this.comments = (ListView)findViewById(R.id.bar_comments_list);
		ratingBar = (RatingBar)findViewById(R.id.bar_rating_bar);
		numVotes = (TextView)findViewById(R.id.bar_num_votes);
		
		/*******/
		Thread tr = new Thread(){
			@Override
			public void run()
			{
				yourRating = TortillatorAPITesting.getInstance().getUserRating(user.getUsername(), tortilla.getId());
				final int num = TortillatorAPITesting.getInstance().getNumVotes(tortilla.getId());
				arrayComments = TortillatorAPITesting.getInstance().getComments(tortilla.getId());
				BarActivity.this.runOnUiThread(
						new Runnable() {
							public void run() 
							{
								if(yourRating!=-1)
								{
									Float ratingFloat = Float.parseFloat(""+yourRating);
									ratingBar.setRating(ratingFloat/2);
								}
								numVotes.setText(" (" + num+ " votes)");
								aac = new ArrayAdapterComment(BarActivity.this, arrayComments);
								comments.setAdapter(aac);
							}
						});
			}
		};
		tr.start();
		/*****/
		
		
		//yourRating = TortillatorAPI.getInstance(BarActivity.this).getUserRating(user.getUsername(), tortilla.getId());
		/*RatingBar ratingBar = (RatingBar)findViewById(R.id.bar_rating_bar);
		if(yourRating!=-1)
		{
			Float ratingFloat = Float.parseFloat(""+yourRating);
			ratingBar.setRating(ratingFloat/2);
		}*/
		//final TextView numVotes = (TextView)findViewById(R.id.bar_num_votes);
		//numVotes.setText(" (" + TortillatorAPI.getInstance(this).getNumVotes(tortilla.getId()) + " votes)");
		//arrayComments = TortillatorAPI.getInstance(this).getComments(tortilla.getId());
		//aac = new ArrayAdapterComment(this, arrayComments);
		//comments.setAdapter(aac);
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() 
		{
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) 
			{
				Vote vote = new Vote(user.getUsername(), tortilla.getId(), (int)(rating*2), new Date());
				if(yourRating !=-1)
				{
					TortillatorAPI.getInstance(BarActivity.this).updateVote(vote);
				}
				else
				{
					TortillatorAPI.getInstance(BarActivity.this).newVote(vote);
				}
				tortilla = TortillatorAPI.getInstance(BarActivity.this).getTortilla(tortilla.getId());
				average.setText(" "+String.format("%.1f", tortilla.getAverage()));
				numVotes.setText(" (" + TortillatorAPI.getInstance(BarActivity.this).getNumVotes(tortilla.getId()) + " votes)");

			}
		});
		
	
	}
	
	public void onClickSave(View v)
	{
		if(v.getId() == R.id.bar_save_button)
		{
			EditText commentText = (EditText)findViewById(R.id.bar_commentT);	
			if(!commentText.getText().toString().isEmpty())
			{
				Comment comment = new Comment(5, user.getUsername(), tortilla.getId(), new Date(), commentText.getText().toString());
				arrayComments.add(0, comment);
				aac.notifyDataSetChanged();
				TortillatorAPI.getInstance(BarActivity.this).newComment(comment);
				commentText.setText("");
			}
			else
			{
				Toast.makeText(this.getApplicationContext(), "You have to write something to add a new comment", Toast.LENGTH_SHORT).show();
			}
		}
	}
	public void onClickImage(View v)
	{
		if(v.getId() == R.id.bar_image)
		{
			final Dialog settingsDialog = new Dialog(this);
			settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image, null));
			
			
			ImageView tortillaImage = (ImageView)settingsDialog.findViewById(R.id.ima_image);
			tortillaImage.setImageDrawable(UtilsImages.loadImageAssets(BarActivity.this, tortilla.getImage()));
			
			tortillaImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					settingsDialog.dismiss();
				}
			});
			settingsDialog.show();		
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		comments.setAdapter(null);
		aac.clear();
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
